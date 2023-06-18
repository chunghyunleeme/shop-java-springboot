package dev.chunghyun.order.domain.items;

import dev.chunghyun.order.domain.items.dto.ItemsResponseDto;
import dev.chunghyun.order.domain.items.dto.ItemsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ItemsService {
    private final ItemsRepository itemRepository;
    private final ItemStocksRepository itemStocksRepository;

    @Transactional
    public Long saveItems(ItemsSaveRequestDto requestDto) {
        Items items = requestDto.toEntity();
        ItemPrices itemPrice = ItemPrices.builder()
                .price(requestDto.getPrice())
                .build();
        items.addNewItemPrices(itemPrice);
        ItemStocks itemStocks = ItemStocks.builder()
                .stockQuantity(requestDto.getStockQuantity())
                .build();
        items.setItemStocks(itemStocks);
        return itemRepository.save(items).getId();
    }

    public ItemStocks  getItemStocksForOrder(Long id) {
        return itemStocksRepository.findByIdForUpdate(id)
                .orElseThrow(() -> new IllegalArgumentException("Item Stock not found"));
    }

    public List<ItemsResponseDto> getAllItemList() {
        return itemRepository.findAllWithItemPrices()
                .stream()
                .map(ItemsResponseDto::new)
                .collect(Collectors.toList());
    }

    public Items  getItemByItemNumber(int itemNumber) {
        return itemRepository.findByItemNumberWithItemPrices(itemNumber)
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));
    }

    public Items getItemByItemNumberWithItemStocks(int itemNumber) {
        return itemRepository.findByItemNumberWithItemStocks(itemNumber)
                .orElseThrow(() -> new IllegalArgumentException("item not found"));
    }
}
