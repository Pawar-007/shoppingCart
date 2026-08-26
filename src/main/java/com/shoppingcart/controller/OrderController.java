package com.shoppingcart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.shoppingcart.DTO.OrderDTO;
import com.shoppingcart.DTO.PlaceOrderRequest;
import com.shoppingcart.DTO.JwtUser;
import com.shoppingcart.service.JwtService;
import com.shoppingcart.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
    private OrderService orderService;
	@Autowired
    private JwtService jwtService;

  

    // =========================================================
    // 1. PLACE ORDER
    // =========================================================

    @PostMapping("/place")
    public ResponseEntity<?> placeOrder(
            @RequestHeader(value = "Authorization", required = false) String token,
            @RequestBody PlaceOrderRequest request) {

        try {

            // Token validation
            if (token == null || token.isBlank()) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body("Authorization token is missing");
            }

            // Remove Bearer prefix
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            // Extract user from JWT
            JwtUser jwtUser = jwtService.extractUser(token);

            if (jwtUser == null) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body("Invalid authorization token");
            }

            Long userId = jwtUser.getUserId();

            // Place order
            OrderDTO order = orderService.placeOrder(
                    userId,
                    request.getAddressId(),
                    request.getSelectedCartItemIds()
            );

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(order);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }


    // =========================================================
    // 2. GET PARTICULAR ORDER
    // =========================================================

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderById(
            @RequestHeader(value = "Authorization", required = false) String token,
            @PathVariable Long orderId) {

        try {

            if (token == null || token.isBlank()) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body("Authorization token is missing");
            }

            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            JwtUser jwtUser = jwtService.extractUser(token);

            if (jwtUser == null) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body("Invalid authorization token");
            }

            Long userId = jwtUser.getUserId();

            OrderDTO order =
                    orderService.getOrderById(userId, orderId);

            return ResponseEntity.ok(order);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }


    // =========================================================
    // 3. GET ALL USER ORDERS
    // =========================================================

    @GetMapping
    public ResponseEntity<?> getUserOrders(
            @RequestHeader(value = "Authorization", required = false) String token) {

        try {

            if (token == null || token.isBlank()) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body("Authorization token is missing");
            }

            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            JwtUser jwtUser = jwtService.extractUser(token);

            if (jwtUser == null) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body("Invalid authorization token");
            }

            Long userId = jwtUser.getUserId();

            List<OrderDTO> orders =
                    orderService.getUserOrders(userId);

            return ResponseEntity.ok(orders);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }


    // =========================================================
    // 4. GET ACTIVE ORDERS
    // =========================================================

    @GetMapping("/active")
    public ResponseEntity<?> getActiveOrders(
            @RequestHeader(value = "Authorization", required = false) String token) {

        try {

            if (token == null || token.isBlank()) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body("Authorization token is missing");
            }

            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            JwtUser jwtUser = jwtService.extractUser(token);

            if (jwtUser == null) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body("Invalid authorization token");
            }

            Long userId = jwtUser.getUserId();

            List<OrderDTO> orders =
                    orderService.getActiveOrders(userId);

            return ResponseEntity.ok(orders);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }


    // =========================================================
    // 5. CANCEL ORDER
    // =========================================================

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<?> cancelOrder(
            @RequestHeader(value = "Authorization", required = false) String token,
            @PathVariable Long orderId) {

        try {

            if (token == null || token.isBlank()) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body("Authorization token is missing");
            }

            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            JwtUser jwtUser = jwtService.extractUser(token);

            if (jwtUser == null) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body("Invalid authorization token");
            }

            Long userId = jwtUser.getUserId();

            orderService.cancelOrder(userId, orderId);

            return ResponseEntity.ok(
                    "Order cancelled successfully"
            );

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}