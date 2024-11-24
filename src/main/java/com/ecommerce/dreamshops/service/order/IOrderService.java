package com.ecommerce.dreamshops.service.order;

import com.ecommerce.dreamshops.dto.OrderDto;
import com.ecommerce.dreamshops.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);
//    OrderDto getOrder(Long orderId);
    OrderDto getOrder(Long orderId);

    List<OrderDto> getUserOrders(Long userId);

    OrderDto convertToDto(Order order);
}
