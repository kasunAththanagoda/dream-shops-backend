package com.ecommerce.dreamshops.service.order;

import com.ecommerce.dreamshops.enums.OrderStatus;
import com.ecommerce.dreamshops.exceptions.ResourceNotFoundException;
import com.ecommerce.dreamshops.model.Cart;
import com.ecommerce.dreamshops.model.Order;
import com.ecommerce.dreamshops.model.OrderItem;
import com.ecommerce.dreamshops.model.Product;
import com.ecommerce.dreamshops.repository.OrderRepository;
import com.ecommerce.dreamshops.repository.ProductRepository;
import com.ecommerce.dreamshops.service.cart.CartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final ProductRepository productRepository;

    @Transactional
    @Override
    public Order placeOrder(Long userId) {
//        Cart cart   = cartService.getCartByUserId(userId);
//        Order order = createOrder(cart);
//        List<OrderItem> orderItemList = createOrderItems(order, cart);
//        order.setOrderItems(new HashSet<>(orderItemList));
//        order.setTotalAmount(calculateTotalAmount(orderItemList));
//        Order savedOrder = orderRepository.save(order);
//        cartService.clearCart(cart.getId());
//        return savedOrder;
        return null;
    }
    private Order createOrder(Cart cart) {
        Order order = new Order();
//        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return  order;
    }
    private List<OrderItem> createOrderItems(Order order, Cart cart) {
        return  cart.getItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();
            product.setInventory(product.getInventory() - cartItem.getQuantity());
            productRepository.save(product);
            return  new OrderItem(
                    order,
                    product,
                    cartItem.getQuantity(),
                    cartItem.getUnitPrice());
        }).toList();
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> orderItemList) {
        return  orderItemList
                .stream()
                .map(item -> item.getPrice()
                        .multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(
                () -> new ResourceNotFoundException("Order not found")
        );
    }
}
