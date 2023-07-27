package dev.chunghyun.order.domain.orders;

import jakarta.persistence.*;
import lombok.Getter;
import java.util.ArrayList;
import java.util.List;
import dev.chunghyun.order.domain.BaseTimeEntity;

@Getter
@Entity
public class Orders extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private final List<OrderItems> orderItemsList = new ArrayList<>();
    private int shippingFee;

    public int getOrderAmount() {
        int orderAmount = 0;
        for (OrderItems orderItem : this.orderItemsList) {
            orderAmount += orderItem.getOrderItemPrice() * orderItem.getOrderQuantity();
        }
        return orderAmount;
    }

    public int getPaymentAmount() {
        return this.getOrderAmount() + this.shippingFee;
    }

    public void addOrderItems(OrderItems orderItems) {
        orderItemsList.add(orderItems);
        orderItems.setOrders(this);
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

    public static Orders createOrder(List<OrderItems> orderItemList) {
        Orders order = new Orders();
        order.setStatus(OrderStatus.ORDER);
        for (OrderItems orderItem : orderItemList){
            order.addOrderItems(orderItem);
        }
        order.setShippingFee();
        return order;
    }

    public void cancel() {
        this.setStatus(OrderStatus.CANCEL);
        for (OrderItems orderItems : orderItemsList) {
            orderItems.cancel();
        }
    }

    public enum OrderStatus {
        ORDER,
        CANCEL
    }

    public static final int SHIPPING_FEE = 2500;
    public static final int FREE_SHIPPING_AMOUNT = 50000;
}
