package com.ecommerce.dreamshops.service.cart;

import com.ecommerce.dreamshops.model.Cart;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

    Long initializeNewCart();

//    Cart initializeNewCart(User user);

    Cart getCartByUserId(Long userId);
}
