package dev.chunghyun.shop.domain.item;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("READY_MADE")
public class ReadyMade extends Item {
    @Column
    private int stockQuantity;
}
