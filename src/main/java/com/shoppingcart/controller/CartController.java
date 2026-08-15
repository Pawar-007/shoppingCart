package com.shoppingcart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcart.DTO.AddToCartRequest;
import com.shoppingcart.DTO.CartResponse;
import com.shoppingcart.DTO.JwtUser;
import com.shoppingcart.service.CartService;
import com.shoppingcart.service.JwtService;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin("*")
public class CartController {

    @Autowired
    private CartService cartService;
    
    @Autowired
    private JwtService jwtService;

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(
    		@RequestHeader(value = "Authorization") String token,
            @RequestBody AddToCartRequest request) {
    	
    	if(token == null || token.isBlank()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Authorization token is missing");
        }
    	
    	token = token.replace("Bearer ", "");
    	
    	JwtUser jwtuser=jwtService.extractUser(token);

    	try {
    	    cartService.addToCart(jwtuser.getUserId(), request);

    	    return ResponseEntity.ok("Product Added Successfully");

    	} catch (RuntimeException e) {

    	    return ResponseEntity
    	            .status(HttpStatus.BAD_REQUEST)
    	            .body(e.getMessage());
    	}
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<?> removeFromCart(
    		@RequestHeader(value = "Authorization") String token,
            @PathVariable Long productId) {
    	
    	if(token == null || token.isBlank()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Authorization token is missing");
        }
    	
    	token = token.replace("Bearer ", "");
    	
    	JwtUser jwtuser=jwtService.extractUser(token);

    	String message = cartService.removeFromCart(
                jwtuser.getUserId(),
                productId
        );

        return ResponseEntity.ok(message);
    }

    @GetMapping("/{userId}")
    public CartResponse displayCart(
            @PathVariable Long userId) {

        return cartService.getCart(userId);
    }

    @DeleteMapping("/clear/{userId}")
    public String clearCart(
            @PathVariable Long userId) {

        cartService.clearCart(userId);

        return "Cart Cleared";
    }
}