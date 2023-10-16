package dev.chunghyun.shop.loader;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import lombok.*;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import dev.chunghyun.shop.domain.item.ItemService;

@RequiredArgsConstructor
public class ItemsDataLoader {
    private final ItemService itemService;

    public void loadInitialItemsData() {
        try {
            createInitialItemsList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createInitialItemsList() throws IOException {
        try (CSVReader csvReader = new CSVReader(new FileReader("_items.csv"))) {
            List<String[]> csvLines = csvReader.readAll();
            int i = 0;
            for (String[] nextLine : csvLines) {
                if(i != 0) {// 첫 줄 제외
//                    processCSVLine(nextLine);
                }
                ++i;
            }
        } catch (CsvException e) {
            e.printStackTrace();
        }
    }

//    private void processCSVLine(String[] csvLine) {
//        try {
//            int itemNumber = Integer.parseInt(csvLine[0]);
//            String name = csvLine[1];
//            int price = Integer.parseInt(csvLine[2]);
//            int stockQuantity = Integer.parseInt(csvLine[3]);
//
//            ItemsSaveRequestDto itemSaveRequestDto = ItemsSaveRequestDto.builder()
//                    .itemNumber(itemNumber)
//                    .name(name)
//                    .stockQuantity(stockQuantity)
//                    .price(price)
//                    .build();
//
//            itemService.saveItems(itemSaveRequestDto);
//        } catch (NumberFormatException e) {
//            e.printStackTrace();
//        }
//    }
}
