package dev.chunghyun.shop.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrdersRequestDto {
    private int itemNumber;
    private int orderQuantity;

    @Builder
    public OrdersRequestDto(int itemNumber, int orderQuantity) {
        this.itemNumber = itemNumber;
        this.orderQuantity = orderQuantity;
    }
}
