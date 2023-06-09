package dev.chunghyun.order.domain.orders.dto;

import dev.chunghyun.order.domain.orders.OrderItems;
import dev.chunghyun.order.domain.orders.Orders;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class OrdersResponseDto {
    private final Long id;
    private final Orders.OrderStatus status;
    private final List<OrderItems> orderItemsList = new ArrayList<>();
    private final int shippingFee;
    private final int orderAmount;
    private final int paymentAmount;

    @Builder
    public OrdersResponseDto(Orders entity) {
        this.id = entity.getId();
        this.status = entity.getStatus();
        this.shippingFee = entity.getShippingFee();
        this.orderAmount = entity.getOrderAmount();
        this.paymentAmount = entity.getPaymentAmount();
        this.orderItemsList.addAll(entity.getOrderItemsList());
    }
}