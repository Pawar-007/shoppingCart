package com.shoppingcart.DTO;

import java.util.List;
import lombok.Data;

@Data
public class CartResponse {
    private Long cartId;
    private List<CartItemDTO> items;
    private double grandTotal;
}
