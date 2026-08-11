package com.shoppingcart.DTO;

import lombok.Data;

@Data
public class CartItemDTO {
    private Long productId;
    private String productName;
    private double price;
    private int quantity;
    private double total;
}
