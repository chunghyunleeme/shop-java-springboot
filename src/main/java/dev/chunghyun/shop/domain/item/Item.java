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
    @Column(name = "item_id")
    private Long id;

    private int itemNumber;

    private String name;

    private int price;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private final List<ItemPrice> itemPriceList = new ArrayList<>();

    protected Item(int itemNumber, String name, int price){
        this.itemNumber = itemNumber;
        this.name = name;
        this.price = price;
    }

    public abstract boolean isOnSale();

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
}
