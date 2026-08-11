package com.shoppingcart.DTO;

import lombok.Data;

@Data
public class ReviewDTO {
    private String customerName;
    private int rating;
    private String review;
}
