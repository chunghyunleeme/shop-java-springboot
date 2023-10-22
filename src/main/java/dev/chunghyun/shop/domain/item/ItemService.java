package dev.chunghyun.shop.domain.item;

import dev.chunghyun.shop.domain.item.dto.CustomMadeSaveRequestDto;
import dev.chunghyun.shop.domain.item.dto.ItemsSaveRequestDto;
import dev.chunghyun.shop.domain.item.dto.ReadyMadeSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemStockRepository itemStockRepository;

    /**
     * 기성품 저장
     * @param requestDto
     * @return
     */
    @Transactional
    public Long saveItem(ReadyMadeSaveRequestDto requestDto) {
        ReadyMade item = ReadyMade.builder()
                .itemNumber(requestDto.getItemNumber())
                .name(requestDto.getName())
                .price(requestDto.getPrice())
                .stockQuantity(requestDto.getStockQuantity())
                .build();
        itemRepository.save(item);
        return item.getId();
    }

    /**
     * 수제 상품 저장
     * @param requestDto
     * @return
     */
    @Transactional
    public Long saveItem(CustomMadeSaveRequestDto requestDto) {
        CustomMade item = CustomMade.builder()
                .itemNumber(requestDto.getItemNumber())
                .name(requestDto.getName())
                .price(requestDto.getPrice())
                .isOrderable(requestDto.isOrderable())
                .madePeriod(requestDto.getMadePeriod())
                .build();
        itemRepository.save(item);
        return item.getId();
    }

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
