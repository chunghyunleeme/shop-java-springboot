package dev.chunghyun.shop.domain.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemStockRepository itemStockRepository;

//    @Transactional
//    public Long saveItems(ItemsSaveRequestDto requestDto) {
//        Items items = requestDto.toEntity();
//        ItemPrices itemPrice = ItemPrices.builder()
//                .price(requestDto.getPrice())
//                .build();
//        items.addNewItemPrices(itemPrice);
//        ItemStocks itemStocks = ItemStocks.builder()
//                .stockQuantity(requestDto.getStockQuantity())
//                .build();
//        items.setItemStocks(itemStocks);
//        return itemRepository.save(items).getId();
//    }

    public ItemStock getItemStocksForOrder(Long id) {
        return itemStockRepository.findByIdForUpdate(id)
                .orElseThrow(() -> new IllegalArgumentException("Item Stock not found"));
    }

//    public List<ItemsResponseDto> getAllItemList() {
//        return itemRepository.findAllWithItemPrices()
//                .stream()
//                .map(ItemsResponseDto::new)
//                .collect(Collectors.toList());
//    }

    public Item getItemByItemNumber(int itemNumber) {
        return itemRepository.findByItemNumberWithItemPrices(itemNumber)
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));
    }

    public Item getItemByItemNumberWithItemStocks(int itemNumber) {
        return itemRepository.findByItemNumberWithItemStocks(itemNumber)
                .orElseThrow(() -> new IllegalArgumentException("item not found"));
    }
}
