package dev.chunghyun.shop.order.dto;

import dev.chunghyun.shop.order.OrderItem;
import dev.chunghyun.shop.order.Order;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class OrdersResponseDto {
    private final Long id;
    private final Order.OrderStatus status;
    private final List<OrderItem> orderItemsList = new ArrayList<>();
    private final int shippingFee;
    private final int orderAmount;
    private final int paymentAmount;

    @Builder
    public OrdersResponseDto(Order entity) {
        this.id = entity.getId();
        this.status = entity.getStatus();
        this.shippingFee = entity.getShippingFee();
        this.orderAmount = entity.getOrderAmount();
        this.paymentAmount = entity.getPaymentAmount();
        this.orderItemsList.addAll(entity.getOrderItemList());
    }
}