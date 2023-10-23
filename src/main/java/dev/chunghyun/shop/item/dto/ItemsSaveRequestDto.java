package dev.chunghyun.shop.item.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
public abstract class ItemsSaveRequestDto {
    private int itemNumber;
    private String name;
    private int price;

    public ItemsSaveRequestDto(int itemNumber, String name, int price) {
        this.itemNumber = itemNumber;
        this.name = name;
        this.price = price;
    }
}
