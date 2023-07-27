package dev.chunghyun.shop.domain.items;

import jakarta.persistence.*;
import dev.chunghyun.shop.domain.BaseTimeEntity;
import lombok.*;

@Getter
@NoArgsConstructor
@Entity
public class ItemPrices extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int price;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEMS_ID")
    private Items items;

    @Builder
    public ItemPrices(int price) {
        this.price = price;
    }

    public void setItems(Items items) {
        this.items = items;
    }
}
