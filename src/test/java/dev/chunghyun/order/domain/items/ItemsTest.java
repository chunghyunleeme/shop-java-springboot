package dev.chunghyun.order.domain.items;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ItemsTest {
    @Test
    public void 롬복_빌더_테스트() {
        //Given
        int itemNumber = 1;
        String itemName = "테스트 상품";

        //When
        Items items = Items.builder()
                .itemNumber(itemNumber)
                .name(itemName)
                .build();

        //Then
        assertThat(items.getItemNumber()).isEqualTo(itemNumber);
        assertThat(items.getName()).isEqualTo(itemName);
    }

    @Test
    public void 상품_가격_설정_테스트() {
        //Given
        int itemPrice = 1000;
        ItemPrices itemPrices = ItemPrices.builder()
                .price(itemPrice)
                .build();

        Items items = new Items();

        //When
        items.addNewItemPrices(itemPrices);

        //Then
        assertThat(items.getItemPricesList().size()).isEqualTo(1);
        assertThat(items.getItemPricesList().get(0).getPrice()).isEqualTo(itemPrice);
        assertThat(itemPrices.getItems()).isEqualTo(items);
    }

    @Test
    public void 상품_현재_가격_조회_테스트() {
        //Given
        int itemPrice1 = 1000;
        ItemPrices itemPrices1 = ItemPrices.builder()
                .price(itemPrice1)
                .build();

        int itemPrice2 = 2000;
        ItemPrices itemPrices2 = ItemPrices.builder()
                .price(itemPrice2)
                .build();

        Items items = new Items();

        //When
        items.addNewItemPrices(itemPrices1);
        items.addNewItemPrices(itemPrices2);

        //Then
        assertThat(items.getItemPrice()).isEqualTo(itemPrices2.getPrice());
    }

    @Test
    public void 가격_설정안한_상품_가격조회_예외처리_테스트() {
        //Given
        Items items = new Items();
        int illegalArgumentExceptionCount = 0;

        //When
        try {
            items.getItemPrice();
        } catch (IllegalArgumentException e) {
            ++illegalArgumentExceptionCount;
        }

        //Then
        assertThat(illegalArgumentExceptionCount).isEqualTo(1);
    }

    @Test
    public void 상품_재고_설정_테스트() {
        //Given
        int stockQuantity = 10;
        Items items = new Items();
        ItemStocks itemStocks = ItemStocks.builder()
                .stockQuantity(stockQuantity)
                .build();

        //When
        items.setItemStocks(itemStocks);

        //Then
        assertThat(items.getStockQuantity()).isEqualTo(stockQuantity);
        assertThat(items.getItemStocks().getStockQuantity()).isEqualTo(stockQuantity);
        assertThat(items.getItemStocks()).isEqualTo(itemStocks);
    }
}
