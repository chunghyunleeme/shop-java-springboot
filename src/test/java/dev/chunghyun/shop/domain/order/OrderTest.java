package dev.chunghyun.shop.domain.order;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.List;
import java.util.ArrayList;
import dev.chunghyun.shop.domain.item.ItemStock;
import dev.chunghyun.shop.domain.item.Item;
import static org.assertj.core.api.Assertions.assertThat;

public class OrderTest {
    private Item item1;
    private Item item2;
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

        item1 = Item.builder()
                .itemNumber(itemNumber1)
                .name(itemName1)
                .build();

        item2 = Item.builder()
                .itemNumber(itemNumber2)
                .name(itemName2)
                .build();

        orderItems1 = OrderItems.builder()
                .items(item1)
                .orderQuantity(orderQuantity1)
                .orderItemPrice(orderItemPrice1)
                .build();

        orderItems2 = OrderItems.builder()
                .items(item2)
                .orderQuantity(orderQuantity2)
                .orderItemPrice(orderItemPrice2)
                .build();
    }

    @Test
    public void 주문_상품_리스트_추가_테스트() {
        //Given
        OrderItems orderItems1 = OrderItems.builder()
                .items(item1)
                .orderQuantity(1)
                .orderItemPrice(1000)
                .build();

        OrderItems orderItems2 = OrderItems.builder()
                .items(item2)
                .orderQuantity(1)
                .orderItemPrice(1000)
                .build();
        Order order = new Order();

        //When
        order.addOrderItems(orderItems1);
        order.addOrderItems(orderItems2);

        //Then
        assertThat(order.getOrderItemsList().size()).isEqualTo(2);
    }

    @Test
    public void 주문_상품_가격_조회_테스트() {
        //Given
        int orderQuantity1 = 1;
        int orderQuantity2 = 3;
        int orderItemPrice1 = 1000;
        int orderItemPrice2 = 3000;

        OrderItems orderItems1 = OrderItems.builder()
                .items(item1)
                .orderQuantity(orderQuantity1)
                .orderItemPrice(orderItemPrice1)
                .build();

        OrderItems orderItems2 = OrderItems.builder()
                .items(item2)
                .orderQuantity(orderQuantity2)
                .orderItemPrice(orderItemPrice2)
                .build();
        Order order = new Order();
        order.addOrderItems(orderItems1);
        order.addOrderItems(orderItems2);

        //When
        int orderAmount = order.getOrderAmount();

        //Then
        int expectedOrderAmount = orderQuantity1 * orderItemPrice1 + orderQuantity2 * orderItemPrice2;
        assertThat(orderAmount).isEqualTo(expectedOrderAmount);
    }

    @Test
    public void 주문상품_무료배송비_이하일때_배송비_세팅_테스트() {
        //Given
        orderItems1 = OrderItems.builder()
                .items(item1)
                .orderQuantity(1)
                .orderItemPrice(Order.FREE_SHIPPING_AMOUNT / 2)
                .build();
        Order order = new Order();
        order.addOrderItems(orderItems1);

        //When
        order.setShippingFee();

        //Then
        assertThat(order.getShippingFee()).isEqualTo(Order.SHIPPING_FEE);
    }


    @Test
    public void 주문상품_무료배송비_초과일때_배송비_세팅_테스트() {
        //Given
        orderItems1 = OrderItems.builder()
                .items(item1)
                .orderQuantity(2)
                .orderItemPrice(Order.FREE_SHIPPING_AMOUNT)
                .build();
        Order order = new Order();
        order.addOrderItems(orderItems1);

        //When
        order.setShippingFee();

        //Then
        assertThat(order.getShippingFee()).isEqualTo(0);
    }

    @Test
    public void 주문_총액_계산_테스트() {
        //Given
        int orderItemPrice = Order.FREE_SHIPPING_AMOUNT / 2;
        orderItems1 = OrderItems.builder()
                .items(item1)
                .orderQuantity(1)
                .orderItemPrice(orderItemPrice)
                .build();
        Order order = new Order();
        order.addOrderItems(orderItems1);
        order.setShippingFee();

        //When
        int paymentAmount = order.getPaymentAmount();

        //Then
        int expectedPaymentAmount = orderItemPrice + Order.SHIPPING_FEE;
        assertThat(paymentAmount).isEqualTo(expectedPaymentAmount);
    }

    @Test
    public void 주문_생성_테스트() {
        //Given
        List<OrderItems> orderItemsList = new ArrayList<>();
        orderItemsList.add(orderItems1);
        orderItemsList.add(orderItems2);

        //When
        Order order = Order.createOrder(orderItemsList);

        //Then
        assertThat(order.getStatus()).isEqualTo(Order.OrderStatus.ORDER);
        assertThat(order.getOrderItemsList().size()).isEqualTo(2);
        assertThat(order.getShippingFee()).isEqualTo(Order.SHIPPING_FEE);
    }

    @Test
    public void 주문_취소_테스트() {
        //Given
        int stockQuantity1 = 10;
        int stockQuantity2 = 10;
        ItemStock itemStock1 = ItemStock.builder()
                        .stockQuantity(stockQuantity1)
                        .build();
        item1.setItemStocks(itemStock1);

        ItemStock itemStock2 = ItemStock.builder()
                .stockQuantity(stockQuantity2)
                .build();
        item2.setItemStocks(itemStock2);

        List<OrderItems> orderItemsList = new ArrayList<>();
        orderItemsList.add(orderItems1);
        orderItemsList.add(orderItems2);

        Order order = Order.createOrder(orderItemsList);

        //When
        order.cancel();

        //Then
        assertThat(item1.getStockQuantity()).isEqualTo(stockQuantity1 + orderItems1.getOrderQuantity());
        assertThat(item2.getStockQuantity()).isEqualTo(stockQuantity2 + orderItems2.getOrderQuantity());
        assertThat(order.getStatus()).isEqualTo(Order.OrderStatus.CANCEL);
    }
}
