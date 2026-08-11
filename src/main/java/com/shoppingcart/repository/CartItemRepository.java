package com.shoppingcart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppingcart.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{
     
}
