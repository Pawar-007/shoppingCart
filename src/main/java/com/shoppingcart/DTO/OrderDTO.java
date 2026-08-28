package com.shoppingcart.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.shoppingcart.enumerated.OrderStatus;

import lombok.Data;

@Data
public class OrderDTO {
    private Long orderId;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private List<OrderItemDTO> items;
}