package com.shoppingcart.service;

import java.util.List;

import com.shoppingcart.DTO.OrderDTO;
import com.shoppingcart.model.User;

public interface AdminService {

    List<User> getAllUsers();

    List<OrderDTO> getAllOrders();

    void updateStatus(Long orderId,
                      String status);

    long totalUsers();

    long totalOrders();

    long totalProducts();

}