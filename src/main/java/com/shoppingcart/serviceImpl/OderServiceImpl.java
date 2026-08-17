package com.shoppingcart.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.shoppingcart.DTO.CartResponse;
import com.shoppingcart.DTO.OrderDTO;
import com.shoppingcart.model.Address;
import com.shoppingcart.model.Cart;
import com.shoppingcart.model.User;
import com.shoppingcart.service.AddressService;
import com.shoppingcart.service.CartService;
import com.shoppingcart.service.OrderService;
import com.shoppingcart.service.UserService;

public class OderServiceImpl implements OrderService{
	
	@Autowired
	private UserService userServive;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private AddressService addressService;
	

	@Override
	public OrderDTO placeOrder(Long userId, Long addressId, List<Long> selectedCartItemIds) {
		
		// 1. User find
        User user=userServive.getProfile(userId);
        
	    // 2. Cart find
        CartResponse cart=cartService.getCart(userId);
        
	    // 3. Address find
        Address address=addressService.getAddressById(userId, addressId);
        
	    // 4. Selected CartItems validate
        

	    // 5. Stock check

	    // 6. Order create

	    // 7. OrderItems create

	    // 8. Total calculate

	    // 9. Order save

	    // 10. Stock reduce

	    // 11. Selected CartItems remove

	    // 12. OrderDTO return
		return null;
	}

	@Override
	public OrderDTO getOrderById(Long userId, Long orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderDTO> getUserOrders(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrderDTO> getActiveOrders(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void cancelOrder(Long userId, Long orderId) {
		// TODO Auto-generated method stub
		
	}

}
