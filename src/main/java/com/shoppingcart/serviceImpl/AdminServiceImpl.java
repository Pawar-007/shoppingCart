package com.shoppingcart.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingcart.DTO.OrderDTO;
import com.shoppingcart.model.User;
import com.shoppingcart.repository.UserRepository;
import com.shoppingcart.service.AdminService;

@Service
public class AdminServiceImpl  implements AdminService{

	@Autowired
	UserRepository userRepository;
	
	@Override
	public List<User> getAllUsers() {
		
		return userRepository.findAll();
	}

	@Override
	public List<OrderDTO> getAllOrders() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateStatus(Long orderId, String status) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long totalUsers() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long totalOrders() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long totalProducts() {
		// TODO Auto-generated method stub
		return 0;
	}

}
