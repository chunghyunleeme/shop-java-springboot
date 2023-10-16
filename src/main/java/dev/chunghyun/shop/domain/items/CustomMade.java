package dev.chunghyun.shop.domain.items;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CUSTOM_MADE")
public class CustomMade extends Items{
    private boolean isOrderable;
    private int madePeriod;
}
