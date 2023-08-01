package dev.chunghyun.shop.domain.items;

import dev.chunghyun.shop.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("READY_MADE")
public class ReadyMade extends Items {
    @Column
    private int stockQuantity;
}
