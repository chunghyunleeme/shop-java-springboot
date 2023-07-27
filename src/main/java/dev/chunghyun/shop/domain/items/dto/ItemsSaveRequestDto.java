package dev.chunghyun.shop.domain.items.dto;

import dev.chunghyun.shop.domain.items.Items;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ItemsSaveRequestDto {
    private int itemNumber;
    private String name;
    private int price;
    private int stockQuantity;

    @Builder
    public ItemsSaveRequestDto(int itemNumber, String name, int price, int stockQuantity) {
        this.itemNumber = itemNumber;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public Items toEntity() {
        return Items.builder()
                .itemNumber(itemNumber)
                .name(name)
                .build();
    }
}
