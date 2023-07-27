package dev.chunghyun.order.domain.orders;

import dev.chunghyun.order.domain.items.Items;
import dev.chunghyun.order.domain.items.ItemsService;
import dev.chunghyun.order.domain.items.dto.ItemsResponseDto;
import dev.chunghyun.order.domain.orders.dto.OrdersRequestDto;
import dev.chunghyun.order.domain.orders.dto.OrdersRequestListDto;
import dev.chunghyun.order.domain.orders.dto.OrdersResponseDto;
import dev.chunghyun.order.exception.SoldOutException;
import dev.chunghyun.order.loader.ItemsDataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;


import static org.assertj.core.api.Assertions.assertThat;
@ActiveProfiles("test")
@SpringBootTest
public class OrdersServiceIntegrationTest {
    @Autowired
    OrdersService ordersService;
    @Autowired
    ItemsService itemsService;

    @BeforeEach
    public void beforeEach() {
        ItemsDataLoader dataLoader = new ItemsDataLoader(itemsService);
        dataLoader.loadInitialItemsData();
    }

    @Test
    public void 주문_동시요청_테스트() throws Exception {
        //Given
        AtomicInteger orderItemsSuccessCount = new AtomicInteger();
        AtomicInteger soldOutExceptionCount = new AtomicInteger();
        ItemsResponseDto items = itemsService.getAllItemList().get(0);
        int itemNumber = items.getItemNumber();
        int stockQuantity = items.getStockQuantity();

        // 동시에 주문 요청
        int numThreads = stockQuantity * 2; // 동시 스레드 수
        int orderQuantity = 1; // 주문 수량
        ExecutorService executorService = Executors.newCachedThreadPool();
        CountDownLatch latch = new CountDownLatch(numThreads);

        //When
        for (int i = 0; i < numThreads; i++) {
            executorService.submit(() -> {
                try {
                    OrdersRequestListDto orderRequestListDto = new OrdersRequestListDto();
                    orderRequestListDto.addOrderRequestDto(OrdersRequestDto.builder().itemNumber(itemNumber).orderQuantity(orderQuantity).build());
                    ordersService.order(orderRequestListDto);
                    orderItemsSuccessCount.getAndIncrement();
                } catch (SoldOutException e) {
                    System.out.println(e.getMessage());
                    soldOutExceptionCount.getAndIncrement();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executorService.shutdown();

        //Then
        assertThat(orderItemsSuccessCount.get()).isEqualTo(stockQuantity);
        assertThat(soldOutExceptionCount.get()).isEqualTo(numThreads - stockQuantity);

        Items orderedItems = itemsService.getItemByItemNumberWithItemStocks(itemNumber);
        assertThat(orderedItems.getStockQuantity()).isEqualTo(0);
        assertThat(orderedItems.getItemStocks().getStockQuantity()).isEqualTo(0);

        List<OrdersResponseDto> orderList = ordersService.getAllOrdersList();
        assertThat(orderList.size()).isEqualTo(stockQuantity / orderQuantity);
    }
}