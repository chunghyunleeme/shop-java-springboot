package dev.chunghyun.shop.domain.item;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ItemsTest {
    @Test
    public void 롬복_빌더_테스트() {
        //Given
        int itemNumber = 1;
        String itemName = "테스트 상품";

        //When
        Item item = Item.builder()
                .itemNumber(itemNumber)
                .name(itemName)
                .build();

        //Then
        assertThat(item.getItemNumber()).isEqualTo(itemNumber);
        assertThat(item.getName()).isEqualTo(itemName);
    }

    @Test
    public void 상품_가격_설정_테스트() {
        //Given
        int itemPrice = 1000;
        ItemPrice itemPrices = ItemPrice.builder()
                .price(itemPrice)
                .build();

        Item item = new Item();

        //When
        item.addNewItemPrices(itemPrices);

        //Then
        assertThat(item.getItemPriceList().size()).isEqualTo(1);
        assertThat(item.getItemPriceList().get(0).getPrice()).isEqualTo(itemPrice);
        assertThat(itemPrices.getItem()).isEqualTo(item);
    }

    @Test
    public void 상품_현재_가격_조회_테스트() {
        //Given
        int itemPrice1 = 1000;
        ItemPrice itemPrices1 = ItemPrice.builder()
                .price(itemPrice1)
                .build();

        int itemPrice2 = 2000;
        ItemPrice itemPrices2 = ItemPrice.builder()
                .price(itemPrice2)
                .build();

        Item item = new Item();

        //When
        item.addNewItemPrices(itemPrices1);
        item.addNewItemPrices(itemPrices2);

        //Then
        assertThat(item.getItemPrice()).isEqualTo(itemPrices2.getPrice());
    }

    @Test
    public void 가격_설정안한_상품_가격조회_예외처리_테스트() {
        //Given
        Item item = new Item();
        int illegalArgumentExceptionCount = 0;

        //When
        try {
            item.getItemPrice();
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
        Item item = new Item();
        ItemStock itemStock = ItemStock.builder()
                .stockQuantity(stockQuantity)
                .build();

        //When
        item.setItemStocks(itemStock);

        //Then
        assertThat(item.getStockQuantity()).isEqualTo(stockQuantity);
        assertThat(item.getItemStock().getStockQuantity()).isEqualTo(stockQuantity);
        assertThat(item.getItemStock()).isEqualTo(itemStock);
    }
}
