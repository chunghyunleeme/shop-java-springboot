package dev.chunghyun.shop.domain.order;

import dev.chunghyun.shop.domain.delivery.Delivery;
import dev.chunghyun.shop.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import java.util.ArrayList;
import java.util.List;
import dev.chunghyun.shop.domain.BaseTimeEntity;
import static jakarta.persistence.FetchType.*;

@Getter
@Entity
public class Orders extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private final List<OrderItem> orderItemsList = new ArrayList<>();
    private int shippingFee;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    @JoinColumn(name = "DELIVERY_ID")
    private Delivery delivery;

    public int getOrderAmount() {
        int orderAmount = 0;
        for (OrderItem orderItem : this.orderItemsList) {
            orderAmount += orderItem.getOrderItemPrice() * orderItem.getOrderQuantity();
        }
        return orderAmount;
    }

    public int getPaymentAmount() {
        return this.getOrderAmount() + this.shippingFee;
    }

    public void addOrderItems(OrderItem orderItem) {
        orderItemsList.add(orderItem);
        orderItem.setOrders(this);
    }

    public void setShippingFee() {
        this.shippingFee = SHIPPING_FEE;
        boolean isOverFreeShippingAmount = FREE_SHIPPING_AMOUNT < getOrderAmount();
        if(isOverFreeShippingAmount) {
            shippingFee = 0;
        }
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public static Orders createOrder(List<OrderItem> orderItemList) {
        Orders order = new Orders();
        order.setStatus(OrderStatus.ORDER);
        for (OrderItem orderItem : orderItemList){
            order.addOrderItems(orderItem);
        }
        order.setShippingFee();
        return order;
    }

//    public void cancel() {
//        this.setStatus(OrderStatus.CANCEL);
//        for (OrderItems orderItems : orderItemsList) {
//            orderItems.cancel();
//        }
//    }

    public enum OrderStatus {
        ORDER,
        CANCEL
    }

    public static final int SHIPPING_FEE = 2500;
    public static final int FREE_SHIPPING_AMOUNT = 50000;
}
