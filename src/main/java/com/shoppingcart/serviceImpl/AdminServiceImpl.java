package com.shoppingcart.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingcart.DTO.OrderDTO;
import com.shoppingcart.enumerated.OrderStatus;
import com.shoppingcart.model.Order;
import com.shoppingcart.model.User;
import com.shoppingcart.repository.OrderRepository;
import com.shoppingcart.repository.ProductRepository;
import com.shoppingcart.repository.UserRepository;
import com.shoppingcart.service.AdminService;

@Service
public class AdminServiceImpl  implements AdminService{

	@Autowired
	UserRepository userRepository;
	@Autowired
	OrderRepository orderRepositery;
	@Autowired
	ProductRepository productRepositery;
	
	
	@Override
	public List<User> getAllUsers() {
		
		return userRepository.findAll();
	}
	
	
	@Override
	public List<OrderDTO> getAllOrders() {
		List<Order> orders=orderRepositery.findAll();
		List<OrderDTO> res = orders.stream()
		        .map(order -> {
		            OrderDTO dto = new OrderDTO();

		            dto.setOrderId(order.getOrderId());
		            dto.setOrderDate(order.getOrderDate());
		            dto.setTotalAmount(order.getTotalAmount());
		            dto.setStatus(order.getOrderStatus());

		            return dto;
		        })
		        .toList();
		return res;
	}
	
	@Override
	public void updateStatus(Long orderId, String status) {

		OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());

	    Order order = orderRepositery.findById(orderId)
	            .orElseThrow(() -> new RuntimeException("Order not found"));

	    order.setOrderStatus(orderStatus);

	    orderRepositery.save(order);
	}

	@Override
	public long totalUsers() {
		// TODO Auto-generated method stub
		return userRepository.count();
	}

	@Override
	public long totalOrders() {
		return orderRepositery.count();

	}

	@Override
	public long totalProducts() {
		// TODO Auto-generated method stub
		return productRepositery.count();
	}

}
