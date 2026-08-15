package com.shoppingcart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppingcart.model.Cart;
import com.shoppingcart.model.CartItem;
import com.shoppingcart.model.Product;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{
	 Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
	 Optional<CartItem> findByCart_User_UserIdAndProduct_ProductId(
		        Long userId,
		        Long productId
		);
}
