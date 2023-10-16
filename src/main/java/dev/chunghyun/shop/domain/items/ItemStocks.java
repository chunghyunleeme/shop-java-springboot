package dev.chunghyun.shop.domain.items;

import dev.chunghyun.shop.exception.SoldOutException;
import jakarta.persistence.*;
import dev.chunghyun.shop.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class ItemStocks extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int stockQuantity;
    @OneToOne(mappedBy = "itemStocks")
    private Items items;

    @Builder
    public ItemStocks(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public void setItems(Items items) {
        this.items = items;
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
