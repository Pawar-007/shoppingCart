package com.shoppingcart.DTO;

import java.math.BigDecimal;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    private Long productId;
    private String productName;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private Object imageUrl; // Product entity ka image field jo bhi type ho (String ya List<String>)
}