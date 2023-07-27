package dev.chunghyun.order.domain.orders;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import dev.chunghyun.order.domain.items.ItemStocks;
import dev.chunghyun.order.domain.items.Items;
import dev.chunghyun.order.domain.items.ItemsService;
import dev.chunghyun.order.domain.orders.dto.OrdersResponseDto;
import dev.chunghyun.order.domain.orders.dto.OrdersRequestDto;
import dev.chunghyun.order.domain.orders.dto.OrdersRequestListDto;

@RequiredArgsConstructor
@Service
public class OrdersService {
    private final OrdersRepository orderRepository;
    private final ItemsService itemsService;

    @Transactional
    public Long order(OrdersRequestListDto ordersRequestListDto) {
        List<OrderItems> orderItemsList = ordersRequestListDto.getOrdersList().stream()
                .map(this::createOrderItems)
                .toList();

        Orders order = Orders.createOrder(orderItemsList);
        orderRepository.save(order);
        return order.getId();
    }

    private OrderItems createOrderItems(OrdersRequestDto ordersRequestDto) {
        Items items = itemsService.getItemByItemNumber(ordersRequestDto.getItemNumber());

        ItemStocks itemStocks = itemsService.getItemStocksForOrder(items.getItemStocks().getId());

        itemStocks.removeStock(ordersRequestDto.getOrderQuantity());
        return OrderItems.createOrderItem(items, ordersRequestDto.getOrderQuantity());
    }

    public List<OrdersResponseDto> getAllOrdersList() {
        return orderRepository.findOrdersDesc().stream()
                .map(OrdersResponseDto::new)
                .collect(Collectors.toList());
    }

    public OrdersResponseDto getLastOrders() {
        Orders latestOrder = orderRepository.findOrdersDesc().get(0);
        return new OrdersResponseDto(latestOrder);
    }
}
