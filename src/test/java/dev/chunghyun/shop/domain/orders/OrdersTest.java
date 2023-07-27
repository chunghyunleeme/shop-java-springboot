package dev.chunghyun.order.domain.orders;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.List;
import java.util.ArrayList;
import dev.chunghyun.order.domain.items.ItemStocks;
import dev.chunghyun.order.domain.items.Items;
import static org.assertj.core.api.Assertions.assertThat;

public class OrdersTest {
    private Items items1;
    private Items items2;
    private OrderItems orderItems1;
    private OrderItems orderItems2;

    @BeforeEach
    public void beforeEach() {
        int itemNumber1 = 1;
        int itemNumber2 = 2;
        String itemName1 = "테스트 상품1";
        String itemName2 = "테스트 상품2";

        int orderQuantity1 = 1;
        int orderQuantity2 = 3;
        int orderItemPrice1 = 1000;
        int orderItemPrice2 = 3000;

        items1 = Items.builder()
                .itemNumber(itemNumber1)
                .name(itemName1)
                .build();

        items2 = Items.builder()
                .itemNumber(itemNumber2)
                .name(itemName2)
                .build();

        orderItems1 = OrderItems.builder()
                .items(items1)
                .orderQuantity(orderQuantity1)
                .orderItemPrice(orderItemPrice1)
                .build();

        orderItems2 = OrderItems.builder()
                .items(items2)
                .orderQuantity(orderQuantity2)
                .orderItemPrice(orderItemPrice2)
                .build();
    }

    @Test
    public void 주문_상품_리스트_추가_테스트() {
        //Given
        OrderItems orderItems1 = OrderItems.builder()
                .items(items1)
                .orderQuantity(1)
                .orderItemPrice(1000)
                .build();

        OrderItems orderItems2 = OrderItems.builder()
                .items(items2)
                .orderQuantity(1)
                .orderItemPrice(1000)
                .build();
        Orders orders = new Orders();

        //When
        orders.addOrderItems(orderItems1);
        orders.addOrderItems(orderItems2);

        //Then
        assertThat(orders.getOrderItemsList().size()).isEqualTo(2);
    }

    @Test
    public void 주문_상품_가격_조회_테스트() {
        //Given
        int orderQuantity1 = 1;
        int orderQuantity2 = 3;
        int orderItemPrice1 = 1000;
        int orderItemPrice2 = 3000;

        OrderItems orderItems1 = OrderItems.builder()
                .items(items1)
                .orderQuantity(orderQuantity1)
                .orderItemPrice(orderItemPrice1)
                .build();

        OrderItems orderItems2 = OrderItems.builder()
                .items(items2)
                .orderQuantity(orderQuantity2)
                .orderItemPrice(orderItemPrice2)
                .build();
        Orders orders = new Orders();
        orders.addOrderItems(orderItems1);
        orders.addOrderItems(orderItems2);

        //When
        int orderAmount = orders.getOrderAmount();

        //Then
        int expectedOrderAmount = orderQuantity1 * orderItemPrice1 + orderQuantity2 * orderItemPrice2;
        assertThat(orderAmount).isEqualTo(expectedOrderAmount);
    }

    @Test
    public void 주문상품_무료배송비_이하일때_배송비_세팅_테스트() {
        //Given
        orderItems1 = OrderItems.builder()
                .items(items1)
                .orderQuantity(1)
                .orderItemPrice(Orders.FREE_SHIPPING_AMOUNT / 2)
                .build();
        Orders orders = new Orders();
        orders.addOrderItems(orderItems1);

        //When
        orders.setShippingFee();

        //Then
        assertThat(orders.getShippingFee()).isEqualTo(Orders.SHIPPING_FEE);
    }


    @Test
    public void 주문상품_무료배송비_초과일때_배송비_세팅_테스트() {
        //Given
        orderItems1 = OrderItems.builder()
                .items(items1)
                .orderQuantity(2)
                .orderItemPrice(Orders.FREE_SHIPPING_AMOUNT)
                .build();
        Orders orders = new Orders();
        orders.addOrderItems(orderItems1);

        //When
        orders.setShippingFee();

        //Then
        assertThat(orders.getShippingFee()).isEqualTo(0);
    }

    @Test
    public void 주문_총액_계산_테스트() {
        //Given
        int orderItemPrice = Orders.FREE_SHIPPING_AMOUNT / 2;
        orderItems1 = OrderItems.builder()
                .items(items1)
                .orderQuantity(1)
                .orderItemPrice(orderItemPrice)
                .build();
        Orders orders = new Orders();
        orders.addOrderItems(orderItems1);
        orders.setShippingFee();

        //When
        int paymentAmount = orders.getPaymentAmount();

        //Then
        int expectedPaymentAmount = orderItemPrice + Orders.SHIPPING_FEE;
        assertThat(paymentAmount).isEqualTo(expectedPaymentAmount);
    }

    @Test
    public void 주문_생성_테스트() {
        //Given
        List<OrderItems> orderItemsList = new ArrayList<>();
        orderItemsList.add(orderItems1);
        orderItemsList.add(orderItems2);

        //When
        Orders orders = Orders.createOrder(orderItemsList);

        //Then
        assertThat(orders.getStatus()).isEqualTo(Orders.OrderStatus.ORDER);
        assertThat(orders.getOrderItemsList().size()).isEqualTo(2);
        assertThat(orders.getShippingFee()).isEqualTo(Orders.SHIPPING_FEE);
    }

    @Test
    public void 주문_취소_테스트() {
        //Given
        int stockQuantity1 = 10;
        int stockQuantity2 = 10;
        ItemStocks itemStocks1 = ItemStocks.builder()
                        .stockQuantity(stockQuantity1)
                        .build();
        items1.setItemStocks(itemStocks1);

        ItemStocks itemStocks2 = ItemStocks.builder()
                .stockQuantity(stockQuantity2)
                .build();
        items2.setItemStocks(itemStocks2);

        List<OrderItems> orderItemsList = new ArrayList<>();
        orderItemsList.add(orderItems1);
        orderItemsList.add(orderItems2);

        Orders orders = Orders.createOrder(orderItemsList);

        //When
        orders.cancel();

        //Then
        assertThat(items1.getStockQuantity()).isEqualTo(stockQuantity1 + orderItems1.getOrderQuantity());
        assertThat(items2.getStockQuantity()).isEqualTo(stockQuantity2 + orderItems2.getOrderQuantity());
        assertThat(orders.getStatus()).isEqualTo(Orders.OrderStatus.CANCEL);
    }
}
