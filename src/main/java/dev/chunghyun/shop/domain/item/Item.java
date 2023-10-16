package dev.chunghyun.shop.domain.item;

import dev.chunghyun.shop.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import static jakarta.persistence.FetchType.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorColumn(name = "DTYPE")
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
public abstract class Item extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int itemNumber;
    private String name;
//    private int stockQuantity;
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private final List<ItemPrice> itemPriceList = new ArrayList<>();
    @JoinColumn(name = "ITEM_STOCKS_ID")
    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    private ItemStock itemStock;

//    @Builder
//    public Items(int itemNumber, String name) {
//        this.itemNumber = itemNumber;
//        this.name = name;
//        this.name = name;
//    }

    public int getItemPrice() {
        if(this.itemPriceList.size() == 0) {
            throw new IllegalArgumentException("price no setting");
        }
        return this.itemPriceList.get(this.itemPriceList.size() - 1).getPrice();
    }

    public void addNewItemPrices(ItemPrice itemPrice) {
        this.itemPriceList.add(itemPrice);
        itemPrice.setItem(this);
    }

//    public void addStockQuantity(int quantity) {
//        this.stockQuantity += quantity;
//    }
//
//    public void setStockQuantity(int stockQuantity) {
//        this.stockQuantity = stockQuantity;
//    }

//    public void setItemStocks(ItemStocks itemStocks) {
//        this.setStockQuantity(itemStocks.getStockQuantity());
//
//        this.itemStocks = itemStocks;
//        itemStocks.setItems(this);
//    }
}
