//package com.shoppingcart.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.shoppingcart.DTO.AddToCartRequest;
//import com.shoppingcart.DTO.CartResponse;
//import com.shoppingcart.service.CartService;
//
//@RestController
//@RequestMapping("/api/cart")
//@CrossOrigin("*")
//public class CartController {
//
//    @Autowired
//    private CartService cartService;
//
//    @PostMapping("/add")
//    public String addToCart(
//            @RequestBody AddToCartRequest request) {
//
//        cartService.addToCart(request);
//
//        return "Product Added Successfully";
//    }
//
//    @DeleteMapping("/remove/{userId}/{productId}")
//    public String removeFromCart(
//            @PathVariable Long userId,
//            @PathVariable Long productId) {
//
//        cartService.removeFromCart(userId, productId);
//
//        return "Product Removed";
//    }
//
//    @GetMapping("/{userId}")
//    public CartResponse displayCart(
//            @PathVariable Long userId) {
//
//        return cartService.getCart(userId);
//    }
//
//    @DeleteMapping("/clear/{userId}")
//    public String clearCart(
//            @PathVariable Long userId) {
//
//        cartService.clearCart(userId);
//
//        return "Cart Cleared";
//    }
//}