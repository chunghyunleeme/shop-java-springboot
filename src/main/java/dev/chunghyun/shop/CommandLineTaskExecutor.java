package dev.chunghyun.shop;

import dev.chunghyun.shop.domain.item.ItemService;
import dev.chunghyun.shop.domain.orders.OrderItem;
import dev.chunghyun.shop.domain.orders.OrdersService;
import dev.chunghyun.shop.domain.orders.dto.OrdersRequestListDto;
import dev.chunghyun.shop.domain.orders.dto.OrdersResponseDto;
import dev.chunghyun.shop.loader.ItemsDataLoader;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;
import lombok.RequiredArgsConstructor;

import java.util.Scanner;
@Profile("!test")
@RequiredArgsConstructor
@Component
public class CommandLineTaskExecutor implements CommandLineRunner {
    private final ItemService itemService;
    private final OrdersService orderService;

    @Override
    public void run(String... args) throws Exception {
        ItemsDataLoader dataLoader = new ItemsDataLoader(itemService);
        dataLoader.loadInitialItemsData();

        try (Scanner scanner = new Scanner(System.in)) {
            boolean isRunning = true;

            OrdersRequestListDto orderRequestListDto = new OrdersRequestListDto();

            while (isRunning) {
                System.out.print("입력(o[order]: 주문, q[quit]: 종료) : ");
                String input = scanner.nextLine();
                CommandType commandType = getCommandType(input);
                switch (commandType) {
                    case ORDER:
//                        displayItemList();
//                        while (true) {
//                            try {
//                                System.out.print("상품번호:");
//                                String itemNumberInput = scanner.nextLine();
//                                // 상품번호 space 입력하면 이전것까지 Order 생성
//                                if (itemNumberInput.trim().isEmpty()) {
//                                    orderService.order(orderRequestListDto);
//                                    orderRequestListDto.getOrdersList().clear();
//                                }
//                                System.out.print("수량:");
//                                String orderQuantityInput = scanner.nextLine();
//                                // 수량 space 입력하면 order 조회
//                                if (orderQuantityInput.trim().isEmpty()) {
//                                    displayOrderList();
//                                    break;
//                                }
//                                int itemNumber = Integer.parseInt(itemNumberInput);
//                                int orderQuantity = Integer.parseInt(orderQuantityInput);
//                                orderRequestListDto.addOrderRequestDto(OrdersRequestDto.builder()
//                                        .itemNumber(itemNumber)
//                                        .orderQuantity(orderQuantity)
//                                        .build());
//                            }catch (SoldOutException e) {
//                                System.out.println(e.getMessage());
//                                break;
//                            }
//                        }
                        break;
                    case QUIT:
                        System.out.println("고객님의 주문 감사합니다.");
                        isRunning = false;
                        break;
                    default:
                        System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                        break;
                }
            }
        }
    }

    private CommandType getCommandType(String input) {
        if (input.equalsIgnoreCase("o") || input.equalsIgnoreCase("order")) {
            return CommandType.ORDER;
        } else if (input.equalsIgnoreCase("q") || input.equalsIgnoreCase("quit")) {
            return CommandType.QUIT;
        }
        return CommandType.INVALID;
    }

//    private void displayItemList() {
//        try {
//            List<ItemsResponseDto> itemList = itemService.getAllItemList();
//            StringBuilder sb = new StringBuilder();
//            sb.append(String.format("%-10s%-50s%-10s%-10s\n", "상품번호", "상품명", "판매가격", "재고수"));
//            for (ItemsResponseDto item : itemList) {
//                sb.append(String.format("%-10s%-50s%-10s%-10s\n", item.getItemNumber(), item.getName(), item.getPrice(), item.getStockQuantity()));
//            }
//            System.out.println(sb.toString());
//        } catch (Exception e) {
//            System.out.println("Failed to display item list: " + e.getMessage());
//        }
//    }

    private void displayOrderList() {
        try {
            OrdersResponseDto orders = orderService.getLastOrders();
            System.out.println("---------------------------------");
            for (OrderItem orderItem : orders.getOrderItemsList()) {
                System.out.printf("%s - %d개%n", orderItem.getItem().getName(), orderItem.getOrderQuantity());
            }
            System.out.println("---------------------------------");
            System.out.println("주문금액: " + String.format("%,d", orders.getOrderAmount()));
            if(orders.getShippingFee() != 0) {
                System.out.println("배송비: " + String.format("%,d", orders.getShippingFee()));
            }
            System.out.println("---------------------------------");
            System.out.println("지불금액: " + String.format("%,d", orders.getPaymentAmount()));
            System.out.println("---------------------------------\n");
        } catch (Exception e) {
            System.out.println("Failed to display order list: " + e.getMessage());
        }
    }

    enum CommandType {
        ORDER,
        QUIT,
        INVALID
    }
}
