package com.shoppingcart.service;

import com.shoppingcart.DTO.AddToCartRequest;
import com.shoppingcart.DTO.CartResponse;
import com.shoppingcart.model.User;

public interface CartService {

    void addToCart(Long userId,AddToCartRequest request);

    void removeFromCart(Long productId);

    CartResponse getCart(Long userId);

    void clearCart(Long userId);

    void updateQuantity(Long userId,Long productId,int quantity);

}