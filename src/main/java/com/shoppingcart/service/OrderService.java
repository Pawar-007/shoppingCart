package com.shoppingcart.service;

import java.util.List;

import com.shoppingcart.DTO.OrderDTO;

public interface OrderService {

    void placeOrder(Long userId);

    List<OrderDTO> getOrders(Long userId);

    OrderDTO getOrder(Long orderId);

    void cancelOrder(Long orderId);

}