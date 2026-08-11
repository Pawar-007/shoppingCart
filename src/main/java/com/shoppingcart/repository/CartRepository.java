package com.shoppingcart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppingcart.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{

}
