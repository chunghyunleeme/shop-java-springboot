package dev.chunghyun.shop.order;

import dev.chunghyun.shop.delivery.Delivery;
import dev.chunghyun.shop.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import dev.chunghyun.shop.BaseTimeEntity;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "orders")
@Entity
public class Order extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private final List<OrderItem> orderItemList = new ArrayList<>();

    private int shippingFee;

    @OneToOne(cascade = CascadeType.ALL, fetch = LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; // 주문 시간

    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItemList) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        order.setStatus(OrderStatus.ORDER);
        for (OrderItem orderItem : orderItemList){
            order.addOrderItems(orderItem);
        }
        order.setShippingFee();
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    public void cancel() {
        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItemList) {
            orderItem.cancel();
        }
    }

    public int getOrderAmount() {
        return orderItemList.stream().mapToInt(OrderItem::getTotalPrice).sum();
    }

    public int getPaymentAmount() {
        return this.getOrderAmount() + this.shippingFee;
    }

    private void setMember(Member member) {
        this.member = member;
    }

    private void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    private void setOrderDate(LocalDateTime localDateTime) {
        this.orderDate = localDateTime;
    }

    private void addOrderItems(OrderItem orderItem) {
        orderItemList.add(orderItem);
        orderItem.setOrder(this);
    }

    private void setShippingFee() {
        this.shippingFee = SHIPPING_FEE;
        boolean isOverFreeShippingAmount = FREE_SHIPPING_AMOUNT < getOrderAmount();
        if(isOverFreeShippingAmount) {
            shippingFee = 0;
        }
    }

    private void setStatus(OrderStatus status) {
        this.status = status;
    }

    public enum OrderStatus {
        ORDER,
        CANCEL
    }

    public static final int SHIPPING_FEE = 2500;
    public static final int FREE_SHIPPING_AMOUNT = 50000;
}
