package dev.chunghyun.shop.item.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CustomMadeSaveRequestDto extends ItemsSaveRequestDto{
    private boolean isOrderable;
    private int madePeriod;

    @Builder
    public CustomMadeSaveRequestDto(int itemNumber, String name, int price, boolean isOrderable, int madePeriod) {
        super(itemNumber, name, price);
        this.isOrderable = isOrderable;
        this.madePeriod = madePeriod;
    }
}
