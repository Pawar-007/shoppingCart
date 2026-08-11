package com.shoppingcart.DTO;

import lombok.Data;

@Data
public class ProductDTO {
    private Long productId;
    private String productName;
    private String description;
    private double price;
    private double discount;
    private int stock;
    private String category;
    private String brand;
    private String imageUrl;
}
