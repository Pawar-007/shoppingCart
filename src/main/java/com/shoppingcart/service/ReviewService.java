package com.shoppingcart.service;

import java.util.List;

import com.shoppingcart.DTO.ReviewDTO;
import com.shoppingcart.model.Review;

public interface ReviewService {

    Review addReview(Review review);

    List<ReviewDTO> getReviews(Long productId);

    void deleteReview(Long reviewId);

}