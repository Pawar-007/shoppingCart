package com.shoppingcart.serviceImpl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.shoppingcart.DTO.AddToCartRequest;
import com.shoppingcart.DTO.CartResponse;
import com.shoppingcart.model.Cart;
import com.shoppingcart.model.CartItem;
import com.shoppingcart.model.Product;
import com.shoppingcart.model.User;
import com.shoppingcart.repository.CartItemRepository;
import com.shoppingcart.repository.CartRepository;
import com.shoppingcart.repository.ProductRepository;
import com.shoppingcart.repository.UserRepository;
import com.shoppingcart.service.CartService;

public class CartServiceImpl implements CartService{
	
	  @Autowired
	  private CartRepository cartRepository;

	  @Autowired
      private CartItemRepository cartItemRepository;

      @Autowired
      private ProductRepository productRepository;
      
      @Autowired
      private UserRepository userRepository;

      @Override
      public void addToCart(Long userId, AddToCartRequest request) {

          // 1. Product find karo
          Product product = productRepository
                  .findById(request.getProductId())
                  .orElseThrow(() -> new RuntimeException("Product not found"));

          // 2. User find karo
          User user = userRepository
                  .findById(userId)
                  .orElseThrow(() -> new RuntimeException("User not found"));

          // 3. User ka Cart find karo
          Cart cart = cartRepository
                  .findByUser(user)
                  .orElseGet(() -> {

                      // Cart nahi hai to new Cart create karo
                      Cart newCart = new Cart();
                      newCart.setUser(user);
                      newCart.setCreatedAt(LocalDateTime.now());

                      return cartRepository.save(newCart);
                  });

          // 4. Check karo product already Cart mein hai ya nahi
          Optional<CartItem> optionalCartItem =
                  cartItemRepository.findByCartAndProduct(cart, product);

          if (optionalCartItem.isPresent()) {

              // 5. Product already cart mein hai
              CartItem cartItem = optionalCartItem.get();

              cartItem.setQuantity(
                      cartItem.getQuantity() + request.getQuantity()
              );

              cartItemRepository.save(cartItem);

          } else {

              // 6. Product cart mein nahi hai
              CartItem cartItem = new CartItem();

              cartItem.setCart(cart);
              cartItem.setProduct(product);
              cartItem.setQuantity(request.getQuantity());

              cartItemRepository.save(cartItem);
          }
      }
	@Override
	public void removeFromCart(Long productId) {
		
	}
	
	@Override
	public CartResponse getCart(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void clearCart(Long userId) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void updateQuantity(Long userId, Long productId, int quantity) {
		// TODO Auto-generated method stub
		
	}

}
