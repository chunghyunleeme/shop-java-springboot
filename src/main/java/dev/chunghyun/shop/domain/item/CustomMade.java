package dev.chunghyun.shop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CUSTOM_MADE")
public class CustomMade extends Item {
    private boolean isOrderable;
    private int madePeriod;
}
