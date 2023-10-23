package dev.chunghyun.shop.item;

import jakarta.persistence.*;
import dev.chunghyun.shop.BaseTimeEntity;
import lombok.*;
import static jakarta.persistence.FetchType.*;

@Getter
@NoArgsConstructor
@Entity
public class ItemPrice extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int price;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "ITEMS_ID")
    private Item item;

    @Builder
    public ItemPrice(int price) {
        this.price = price;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
