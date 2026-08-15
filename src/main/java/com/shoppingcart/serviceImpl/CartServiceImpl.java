package com.shoppingcart.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.shoppingcart.DTO.AddToCartRequest;
import com.shoppingcart.DTO.CartItemDTO;
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
      public String removeFromCart(Long userId, Long productId) {

          CartItem cartItem = cartItemRepository
                  .findByCart_User_UserIdAndProduct_ProductId(userId, productId)
                  .orElseThrow(() -> new RuntimeException("Product not found in cart"));

          cartItemRepository.delete(cartItem);
          return "Product removed from cart successfully";
      }
      
      @Override
      public CartResponse getCart(Long userId) {

          // 1. User ka cart find karo
          Cart cart = cartRepository
                  .findByUser_UserId(userId)
                  .orElseThrow(() -> new RuntimeException("Cart not found"));

          // 2. CartResponse create karo
          CartResponse response = new CartResponse();

          response.setCartId(cart.getCartId());

          // 3. CartItems ko DTO mein convert karo
          List<CartItemDTO> itemDTOs = cart.getCartItems()
                  .stream()
                  .map(cartItem -> {

                      CartItemDTO dto = new CartItemDTO();

                      Product product = cartItem.getProduct();

                      dto.setProductId(product.getProductId());
                      dto.setProductName(product.getProductName());
                      dto.setPrice(product.getPrice().doubleValue());
                      dto.setQuantity(cartItem.getQuantity());

                      double total = product.getPrice().doubleValue()
                              * cartItem.getQuantity();

                      dto.setTotal(total);

                      return dto;
                  })
                  .toList();

          response.setItems(itemDTOs);

          // 4. Grand total calculate karo
          double grandTotal = itemDTOs.stream()
                  .mapToDouble(CartItemDTO::getTotal)
                  .sum();

          response.setGrandTotal(grandTotal);

          return response;
      }
	
      @Override
      public void clearCart(Long userId) {

          // 1. User ka cart find karo
          Cart cart = cartRepository
                  .findByUser_UserId(userId)
                  .orElseThrow(() -> new RuntimeException("Cart not found"));

          // 2. Cart ke saare items delete karo
          cartItemRepository.deleteAll(
                  cart.getCartItems()
          );
      }
	
	@Override
	public void updateQuantity(Long userId, Long productId, int quantity) {

	    if (quantity <= 0) {
	        throw new RuntimeException("Quantity must be greater than 0");
	    }

	    CartItem cartItem = cartItemRepository
	            .findByCart_User_UserIdAndProduct_ProductId(userId, productId)
	            .orElseThrow(() -> new RuntimeException("Product not found in cart"));

	    cartItem.setQuantity(quantity);

	    cartItemRepository.save(cartItem);
	}
}
