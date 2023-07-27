package dev.chunghyun.shop.domain.items;

import dev.chunghyun.shop.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Items extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int itemNumber;
    private String name;
    private int stockQuantity;
    @OneToMany(mappedBy = "items", cascade = CascadeType.ALL)
    private final List<ItemPrices> itemPricesList = new ArrayList<>();
    @JoinColumn(name = "ITEM_STOCKS_ID")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ItemStocks itemStocks;

    @Builder
    public Items(int itemNumber, String name) {
        this.itemNumber = itemNumber;
        this.name = name;
        this.name = name;
    }

    public int getItemPrice() {
        if(this.itemPricesList.size() == 0) {
            throw new IllegalArgumentException("price no setting");
        }
        return this.itemPricesList.get(this.itemPricesList.size() - 1).getPrice();
    }

    public void addNewItemPrices(ItemPrices itemPrices) {
        this.itemPricesList.add(itemPrices);
        itemPrices.setItems(this);
    }

    public void addStockQuantity(int quantity) {
        this.stockQuantity += quantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public void setItemStocks(ItemStocks itemStocks) {
        this.setStockQuantity(itemStocks.getStockQuantity());

        this.itemStocks = itemStocks;
        itemStocks.setItems(this);
    }
}
