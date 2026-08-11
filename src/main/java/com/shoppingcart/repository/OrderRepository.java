package com.shoppingcart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppingcart.model.Order;

public interface OrderRepository extends JpaRepository<Order,Long>{

}
