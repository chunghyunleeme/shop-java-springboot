package dev.chunghyun.shop.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Builder;

@Entity
@DiscriminatorValue("CUSTOM_MADE")
public class CustomMade extends Item {
    private boolean isOrderable;
    private int madePeriod;

    @Builder
    public CustomMade(int itemNumber, String name, int price, boolean isOrderable, int madePeriod) {
        super(itemNumber, name, price);
        this.isOrderable = isOrderable;
        this.madePeriod = madePeriod;
    }

    @Override
    public boolean isOnSale() {
        return isOrderable;
    }
}
