package com.shoppingcart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppingcart.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long>{

}
