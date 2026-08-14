package com.shoppingcart.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO {

    private Long productId;

    private String name;

    private String description;

    private BigDecimal price;

    private Integer stockQuantity;

    private LocalDateTime createdAt;

    private CategoryResponseDTO category;

    private BrandResponseDTO brand;

    private List<String> imageUrl;
}