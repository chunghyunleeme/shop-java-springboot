package dev.chunghyun.shop.domain.item;

import dev.chunghyun.shop.exception.SoldOutException;
import jakarta.persistence.*;
import lombok.Builder;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@DiscriminatorValue("READY_MADE")
public class ReadyMade extends Item {
    @Column
    private int stockQuantity;

    @JoinColumn(name = "item_stock_id")
    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    private ItemStock itemStock;

    @Builder
    public ReadyMade(int itemNumber, String name, int price, int stockQuantity){
        super(itemNumber, name, price);
        this.stockQuantity = stockQuantity;
    }

    @Override
    public boolean isOnSale() {
        if(0 < stockQuantity) {
            return true;
        }
        return false;
    }

    public void setStockQuantity(int quantity){
        this.stockQuantity = quantity;
    }

    public void removeStock(int quantity) {
        if(this.stockQuantity < quantity) {
            throw new SoldOutException();
        }
        this.stockQuantity -= quantity;
    }

    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }
}
