package dev.chunghyun.shop.item;

import dev.chunghyun.shop.exception.SoldOutException;
import jakarta.persistence.*;
import dev.chunghyun.shop.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class ItemStock extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int stockQuantity;

    @OneToOne(mappedBy = "itemStock")
    private ReadyMade item;

    @Builder
    public ItemStock(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public void setItem(ReadyMade item) {
        this.item = item;
    }

    public void removeStock(int orderQuantity) {
        int restStock = this.stockQuantity - orderQuantity;
        if(restStock < 0) {
            throw new SoldOutException();
        }
        this.stockQuantity = restStock;
        this.item.setStockQuantity(restStock);
    }
}
