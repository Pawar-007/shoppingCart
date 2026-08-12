package com.shoppingcart.DTO;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductDTO {
	 private Long productId;

	    private String name;

	    private String description;

	    private BigDecimal price;

	    private int stockQuantity;

	    private Long categoryId;

	    private String categoryName;

	    private Long brandId;

	    private String brandName;
	    
	    private String imageUrl;
	    
}
