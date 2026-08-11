package com.shoppingcart.DTO;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class OrderDTO {
    private Long orderId;
    private LocalDateTime orderDate;
    private double totalAmount;
    private String status;
}
