package dev.chunghyun.shop.domain.orders.dto;

import dev.chunghyun.shop.domain.orders.OrderItem;
import dev.chunghyun.shop.domain.orders.Orders;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class OrdersResponseDto {
    private final Long id;
    private final Orders.OrderStatus status;
    private final List<OrderItem> orderItemsList = new ArrayList<>();
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