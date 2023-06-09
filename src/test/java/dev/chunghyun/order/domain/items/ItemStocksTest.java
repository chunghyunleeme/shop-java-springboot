package dev.chunghyun.order.domain.items;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class ItemStocksTest {
    @Test
    public void 상품_재고_차감_테스트() {
        //Given
        int stockQuantity = 10;
        int orderQuantity = 1;
        Items items = new Items();
        ItemStocks itemStocks = ItemStocks.builder()
                .stockQuantity(stockQuantity)
                .build();
        items.setItemStocks(itemStocks);

        //When
        itemStocks.removeStock(orderQuantity);

        //Then
        assertThat(itemStocks.getStockQuantity()).isEqualTo(stockQuantity - orderQuantity);
        assertThat(items.getStockQuantity()).isEqualTo(stockQuantity - orderQuantity);
        assertThat(items.getItemStocks().getStockQuantity()).isEqualTo(itemStocks.getStockQuantity());
    }
}
