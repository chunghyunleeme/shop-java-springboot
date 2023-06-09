package dev.chunghyun.order.domain.items.dto;

import dev.chunghyun.order.domain.items.Items;
import lombok.Getter;

@Getter
public class ItemsResponseDto {
    private Long id;
    private int itemNumber;
    private String name;
    private int price;
    private int stockQuantity;

    public ItemsResponseDto(Items entity) {
        this.id = entity.getId();
        this.itemNumber = entity.getItemNumber();
        this.price = entity.getItemPrice();
        this.name = entity.getName();
        this.stockQuantity = entity.getStockQuantity();
    }
}
