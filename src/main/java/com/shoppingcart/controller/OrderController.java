//package com.shoppingcart.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.shoppingcart.DTO.OrderDTO;
//import com.shoppingcart.service.OrderService;
//
//@RestController
//@RequestMapping("/api/orders")
//@CrossOrigin("*")
//public class OrderController {
//
//    @Autowired
//    private OrderService orderService;
//
//    @PostMapping("/place/{userId}")
//    public String placeOrder(
//            @PathVariable Long userId) {
//
//        orderService.placeOrder(userId);
//
//        return "Order Placed Successfully";
//    }
//
//    @GetMapping("/{userId}")
//    public List<OrderDTO> getOrders(
//            @PathVariable Long userId) {
//
//        return orderService.getOrders(userId);
//    }
//
//    @PutMapping("/cancel/{orderId}")
//    public String cancelOrder(
//            @PathVariable Long orderId) {
//
//        orderService.cancelOrder(orderId);
//
//        return "Order Cancelled";
//    }
//}