package dev.chunghyun.shop.item.dto;

import lombok.Builder;
import lombok.Getter;

@Getter

public class ReadyMadeSaveRequestDto extends ItemsSaveRequestDto{
    int stockQuantity;

    @Builder
    public ReadyMadeSaveRequestDto(int itemNumber, String name, int price, int stockQuantity){
        super(itemNumber, name, price);
        this.stockQuantity = stockQuantity;
    }
}
