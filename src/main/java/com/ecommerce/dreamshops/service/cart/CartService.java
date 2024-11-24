package com.ecommerce.dreamshops.service.cart;

import com.ecommerce.dreamshops.exceptions.ResourceNotFoundException;
import com.ecommerce.dreamshops.model.Cart;
import com.ecommerce.dreamshops.model.User;
import com.ecommerce.dreamshops.repository.CartItemRepository;
import com.ecommerce.dreamshops.repository.CartRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;


@Service
@AllArgsConstructor
public class CartService implements ICartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final AtomicLong cartIdGenerator = new AtomicLong(0);

    @Override
    public Cart getCart(Long id) { // can't understand this
        Cart cart = cartRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Cart Not Found"));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }

    @Transactional
    @Override
    public void clearCart(Long id) {
        Cart cart = getCart(id);
        cartItemRepository.deleteAllByCartId(id);
        cart.getItems().clear();
        cartRepository.delete(cart);
//
//        i think this is better
//        cartItemRepository.deleteAllByCartId(id); // Delete all items associated with the cart
//        cartRepository.deleteById(id);

    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = getCart(id);
//        return cart.getItems()
//                .stream()
//                .map(CartItem:: getTotalPrice)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return cart.getTotalAmount();
    }

    @Override
    public Cart initializeNewCart(User user) {
        return  Optional.ofNullable(getCartByUserId(user.getId()))
                .orElseGet(() ->{
                    Cart cart = new Cart();
                    cart.setUser(user);
                    return cartRepository.save(cart);
                });

    }
//    @Override
//    public Cart initializeNewCart(User user) {
//        return Optional.ofNullable(getCartByUserId(user.getId()))
//                .orElseGet(() -> {
//                    Cart cart = new Cart();
//                    cart.setUser(user);
//                    return cartRepository.save(cart);
//                });
//    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }
}
