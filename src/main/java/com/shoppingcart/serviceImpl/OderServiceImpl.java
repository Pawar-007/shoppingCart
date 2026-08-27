package com.shoppingcart.serviceImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingcart.DTO.CartItemDTO;
import com.shoppingcart.DTO.CartResponse;
import com.shoppingcart.DTO.OrderDTO;
import com.shoppingcart.enumerated.OrderStatus;
import com.shoppingcart.model.Address;
import com.shoppingcart.model.Cart;
import com.shoppingcart.model.CartItem;
import com.shoppingcart.model.Order;
import com.shoppingcart.model.OrderItem;
import com.shoppingcart.model.Product;
import com.shoppingcart.model.User;
import com.shoppingcart.repository.CartItemRepository;
import com.shoppingcart.repository.OrderItemRepository;
import com.shoppingcart.repository.OrderRepository;
import com.shoppingcart.repository.ProductRepository;
import com.shoppingcart.service.AddressService;
import com.shoppingcart.service.CartService;
import com.shoppingcart.service.OrderService;
import com.shoppingcart.service.UserService;

@Service
public class OderServiceImpl implements OrderService{
	
	@Autowired
	private UserService userServive;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;

	@Override
	@Transactional
	public OrderDTO placeOrder(Long userId, Long addressId, List<Long> selectedCartItemIds) {
		
		if (selectedCartItemIds == null || selectedCartItemIds.isEmpty()) {
		    throw new RuntimeException("Please select at least one cart item");
		}
		
		
		// 1. User find
        User user=userServive.getProfile(userId);
        
	    // 2. Cart find
        CartResponse cart=cartService.getCart(userId);
        
	    // 3. Address find
        Address address=addressService.getAddressById(userId, addressId);
        
     // 4. Selected CartItems validate
        List<CartItemDTO> selectedItems = cart.getItems()
                .stream()
                .filter(item ->
                        selectedCartItemIds.contains(item.getCartItemId()))
                .toList();

        if (selectedItems.size() != selectedCartItemIds.size()) {
            throw new RuntimeException(
                    "One or more selected cart items are invalid");
        }


        // 5. Stock check
        Map<Long, Product> products = new HashMap<>();

        for (CartItemDTO item : selectedItems) {

            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Product not found with id: "
                                            + item.getProductId()));

            if (product.getStockQuantity() < item.getQuantity()) {
                throw new RuntimeException(
                        "Insufficient stock for product: "
                                + product.getProductName());
            }

            products.put(item.getProductId(), product);
        }


        // 6. Order create
        Order order = new Order();

        order.setUser(user);
        order.setAddress(address);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.PENDING);


        // 7. OrderItems create
        List<OrderItem> orderItems = new ArrayList<>();


        // 8. Total calculate
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (CartItemDTO item : selectedItems) {

            Product product = products.get(item.getProductId());

            OrderItem orderItem = new OrderItem();

            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(product.getPrice());

            BigDecimal itemTotal = product.getPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantity()));

            totalAmount = totalAmount.add(itemTotal);

            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);
        order.setTotalAmount(totalAmount);

     // 9. Order save
        Order savedOrder = orderRepository.save(order);


        // 10. Stock reduce
        for (CartItemDTO item : selectedItems) {

            Product product = products.get(item.getProductId());

            product.setStockQuantity(
                    product.getStockQuantity() - item.getQuantity()
            );

            productRepository.save(product);
        }


		        // 11. Selected CartItems remove
		        for (Long cartItemId : selectedCartItemIds) {
		
		            CartItem cartItem = cartItemRepository.findById(cartItemId)
		                    .orElseThrow(() -> new RuntimeException("Cart item not found with id: " + cartItemId));
		
		            if (!cartItem.getCart().getUser().getUserId().equals(userId)) {
		                throw new RuntimeException("You are not authorized to remove this cart item");
		            }
		
		            // Parent ke collection se bhi remove karo — orphanRemoval ko trigger
		            // karne ka sahi tarika, agar Cart entity persistence context mein
		            // already loaded/managed hai to.
		            Cart parentCart = cartItem.getCart();
		            if (parentCart.getCartItems() != null) {
		                parentCart.getCartItems().remove(cartItem);
		            }
		
		            cartItemRepository.delete(cartItem);
		        }
		
		        cartItemRepository.flush(); // turant DB pe apply karo, transaction end tak wait mat karo


        // 12. OrderDTO return
        return mapToOrderDTO(savedOrder);
	}

	private OrderDTO mapToOrderDTO(Order order) {

	    OrderDTO dto = new OrderDTO();

	    dto.setOrderId(order.getOrderId());
	    dto.setTotalAmount(order.getTotalAmount());
	    dto.setStatus(order.getOrderStatus());
	    dto.setOrderDate(order.getOrderDate());

	    return dto;
	}
	
	@Override
	public OrderDTO getOrderById(Long userId, Long orderId) {

	    Order order = orderRepository
	            .findByOrderIdAndUser_UserId(orderId, userId)
	            .orElseThrow(() ->
	                    new RuntimeException(
	                            "Order not found or you are not authorized to access this order"
	                    ));

	    return mapToOrderDTO(order);
	}

	@Override
	public List<OrderDTO> getUserOrders(Long userId) {

	    List<Order> orders =
	            orderRepository.findByUser_UserIdOrderByOrderDateDesc(userId);

	    return orders.stream()
	            .map(this::mapToOrderDTO)
	            .toList();
	}

	@Override
	public List<OrderDTO> getActiveOrders(Long userId) {

		List<OrderStatus> activeStatuses = List.of(
				OrderStatus.PENDING,
	            OrderStatus.CONFIRMED,
	            OrderStatus.SHIPPED,
	            OrderStatus.OUT_FOR_DELIVERY
	    );

	    List<Order> orders =
	            orderRepository.findByUser_UserIdAndOrderStatusIn(
	                    userId,
	                    activeStatuses
	            );

	    return orders.stream()
	            .map(this::mapToOrderDTO)
	            .toList();
	}
	
	
	@Override
	@Transactional
	public void cancelOrder(Long userId, Long orderId) {

	    // 1. Order find + ownership check
	    Order order = orderRepository
	            .findByOrderIdAndUser_UserId(orderId, userId)
	            .orElseThrow(() ->
	                    new RuntimeException(
	                            "Order not found or you are not authorized to cancel this order"
	                    ));

	    // 2. Check cancellation status
	    if (order.getOrderStatus() != OrderStatus.PENDING) {
	        throw new RuntimeException(
	            "Order cannot be cancelled at this stage"
	        );
	    }

	    // 3. Restore stock
	    List<OrderItem> orderItems =
	            orderItemRepository.findByOrder_OrderId(orderId);

	    for (OrderItem orderItem : orderItems) {

	        Product product = orderItem.getProduct();

	        product.setStockQuantity(
	                product.getStockQuantity()
	                        + orderItem.getQuantity()
	        );

	        productRepository.save(product);
	    }

	    // 4. Change order status
	    order.setOrderStatus(OrderStatus.CANCELLED);

	    orderRepository.save(order);
	}

}
