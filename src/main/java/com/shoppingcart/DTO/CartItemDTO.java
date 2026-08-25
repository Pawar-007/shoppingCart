package com.shoppingcart.DTO;

import java.util.List;

import lombok.Data;

@Data
public class CartItemDTO {
	private Long cartItemId;
    private Long productId;
    private String productName;
    private double price;
    private int quantity;
    private double total;
    private List<String> imageUrls;
}
