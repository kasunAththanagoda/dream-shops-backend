package com.ecommerce.dreamshops.service.order;

import com.ecommerce.dreamshops.model.Order;

public interface IOrderService {
    Order placeOrder(Long userId);
//    OrderDto getOrder(Long orderId);
    Order getOrder(Long orderId);
//    List<OrderDto> getUserOrders(Long userId);
//
//    OrderDto convertToDto(Order order);
}
