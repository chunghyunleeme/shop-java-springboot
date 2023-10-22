package dev.chunghyun.shop.domain.order;

import dev.chunghyun.shop.domain.item.ReadyMade;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import dev.chunghyun.shop.domain.BaseTimeEntity;
import dev.chunghyun.shop.domain.item.Item;
import java.util.Objects;
import static jakarta.persistence.FetchType.*;

@Getter
@RequiredArgsConstructor
@Entity
public class OrderItem extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ORDERS_ID")
    private Order order;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ITEMS_ID")
    private Item item;

    private int orderItemPrice;// 상품 가격과 독립된 데이터로 존재 왜냐면, 상품가격이 변했다고 전에 주문한 상품 금액이 바뀌는건 아님

    private int orderQuantity;

    @Builder
    public OrderItem(Item item, int orderItemPrice, int orderQuantity) {
        this.item = item;
        this.orderItemPrice = orderItemPrice;
        this.orderQuantity = orderQuantity;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public static OrderItem createOrderItem(Item item, int orderQuantity) {
        return OrderItem.builder()
                .item(item)
                .orderItemPrice(Objects.requireNonNull(item.getItemPriceList().stream().findFirst().orElse(null)).getPrice())
                .orderQuantity(orderQuantity)
                .build();
    }

    public void cancel() {
        if(this.item instanceof ReadyMade) {
            ((ReadyMade) this.item).addStock(orderQuantity);
        }
    }

    public int getTotalPrice() {
        return this.getOrderItemPrice() * this.getOrderQuantity();
    }
}
