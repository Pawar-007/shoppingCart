package com.shoppingcart.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoppingcart.enumerated.OrderStatus;
import com.shoppingcart.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUser_UserId(Long userId);

    List<Order> findByUser_UserIdOrderByOrderDateDesc(Long userId);

    List<Order> findByUser_UserIdAndOrderStatus(
            Long userId,
            OrderStatus orderStatus);
    Optional<Order> findByOrderIdAndUser_UserId(
            Long orderId,
            Long userId);
    
    List<Order> findByUser_UserIdAndOrderStatusIn(
            Long userId,
            List<OrderStatus> statuses);
}
