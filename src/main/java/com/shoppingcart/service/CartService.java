package com.shoppingcart.service;

import com.shoppingcart.DTO.AddToCartRequest;
import com.shoppingcart.DTO.CartResponse;

public interface CartService {

    void addToCart(AddToCartRequest request);

    void removeFromCart(Long userId,
                        Long productId);

    CartResponse getCart(Long userId);

    void clearCart(Long userId);

    void updateQuantity(Long userId,
                        Long productId,
                        int quantity);

}