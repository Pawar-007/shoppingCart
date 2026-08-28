package com.shoppingcart.service;

import java.util.List;

import com.shoppingcart.DTO.OrderDTO;

public interface OrderService {

	 // Cart se new order create karna
	OrderDTO placeOrder(
	        Long userId,
	        Long addressId,
	        List<Long> selectedCartItemIds
	);

    // Particular order dekhna
    OrderDTO getOrderById(Long userId, Long orderId);

    // User ke saare orders
    List<OrderDTO> getUserOrders(Long userId);

    // User ke active/current orders
    List<OrderDTO> getActiveOrders(Long userId);

    // Order cancel karna
    void cancelOrder(Long userId, Long orderId);
    
    OrderDTO getOrderByIdForAdmin(Long orderId);
}