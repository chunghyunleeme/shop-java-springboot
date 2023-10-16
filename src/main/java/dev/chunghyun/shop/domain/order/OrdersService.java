package dev.chunghyun.shop.domain.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import dev.chunghyun.shop.domain.item.ItemService;
import dev.chunghyun.shop.domain.order.dto.OrdersResponseDto;

@RequiredArgsConstructor
@Service
public class OrdersService {
    private final OrdersRepository orderRepository;
    private final ItemService itemService;

//    @Transactional
//    public Long order(OrdersRequestListDto ordersRequestListDto) {
//        List<OrderItems> orderItemsList = ordersRequestListDto.getOrdersList().stream()
//                .map(this::createOrderItems)
//                .toList();
//
//        Orders order = Orders.createOrder(orderItemsList);
//        orderRepository.save(order);
//        return order.getId();
//    }

//    private OrderItems createOrderItems(OrdersRequestDto ordersRequestDto) {
//        Items items = itemsService.getItemByItemNumber(ordersRequestDto.getItemNumber());
//
//        ItemStocks itemStocks = itemsService.getItemStocksForOrder(items.getItemStocks().getId());
//
//        itemStocks.removeStock(ordersRequestDto.getOrderQuantity());
//        return OrderItems.createOrderItem(items, ordersRequestDto.getOrderQuantity());
//    }

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
