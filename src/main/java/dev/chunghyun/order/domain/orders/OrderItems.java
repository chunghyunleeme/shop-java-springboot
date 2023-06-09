package dev.chunghyun.order.domain.orders;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import dev.chunghyun.order.domain.BaseTimeEntity;
import dev.chunghyun.order.domain.items.Items;

import java.util.Objects;

@Getter
@RequiredArgsConstructor
@Entity
public class OrderItems extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDERS_ID")
    private Orders orders;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ITEMS_ID")
    private Items items;
    private int orderItemPrice;// 상품 가격과 독립된 데이터로 존재 왜냐면, 상품가격이 변했다고 전에 주문한 상품 금액이 바뀌는건 아님
    private int orderQuantity;

    @Builder
    public OrderItems(Items items, int orderItemPrice, int orderQuantity) {
        this.items = items;
        this.orderItemPrice = orderItemPrice;
        this.orderQuantity = orderQuantity;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public static OrderItems createOrderItem(Items items, int orderQuantity) {
        return OrderItems.builder()
                .items(items)
                .orderItemPrice(Objects.requireNonNull(items.getItemPricesList().stream().findFirst().orElse(null)).getPrice())
                .orderQuantity(orderQuantity)
                .build();
    }

    public void cancel() {
        this.items.addStockQuantity(orderQuantity);
    }

    public int getTotalPrice() {
        return this.getOrderItemPrice() * this.getOrderQuantity();
    }
}
