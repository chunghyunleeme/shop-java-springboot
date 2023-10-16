package dev.chunghyun.shop.domain.item;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class ItemStocksTest {
    @Test
    public void 상품_재고_차감_테스트() {
        //Given
        int stockQuantity = 10;
        int orderQuantity = 1;
        Item item = new Item();
        ItemStock itemStock = ItemStock.builder()
                .stockQuantity(stockQuantity)
                .build();
        item.setItemStocks(itemStock);

        //When
        itemStock.removeStock(orderQuantity);

        //Then
        assertThat(itemStock.getStockQuantity()).isEqualTo(stockQuantity - orderQuantity);
        assertThat(item.getStockQuantity()).isEqualTo(stockQuantity - orderQuantity);
        assertThat(item.getItemStock().getStockQuantity()).isEqualTo(itemStock.getStockQuantity());
    }
}
