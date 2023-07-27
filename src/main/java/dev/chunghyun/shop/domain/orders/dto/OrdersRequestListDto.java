package dev.chunghyun.order.domain.orders.dto;

import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

@Getter
public class OrdersRequestListDto {
    private final List<OrdersRequestDto> ordersList = new ArrayList<>();

    public void addOrderRequestDto(OrdersRequestDto ordersRequestDto) {
        ordersList.add(ordersRequestDto);
    }
}
