package dev.chunghyun.shop.domain.item;

import jakarta.persistence.*;
import dev.chunghyun.shop.domain.BaseTimeEntity;
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
    private Item item;

    @Builder
    public ItemStock(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public void setItem(Item item) {
        this.item = item;
    }

//    public void removeStock(int orderQuantity) {
//        int restStock = this.stockQuantity - orderQuantity;
//        if(restStock < 0) {
//            throw new SoldOutException();
//        }
//        this.stockQuantity = restStock;
//        this.items.setStockQuantity(restStock);
//    }
}
