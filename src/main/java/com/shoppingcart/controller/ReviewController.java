//package com.shoppingcart.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.shoppingcart.DTO.ReviewDTO;
//import com.shoppingcart.service.ReviewService;
//
//@RestController
//@RequestMapping("/api/reviews")
//public class ReviewController {
//
//    @Autowired
//    private ReviewService reviewService;
//
//    @GetMapping("/{productId}")
//    public List<ReviewDTO> getReviews(
//            @PathVariable Long productId) {
//
//        return reviewService.getReviews(productId);
//    }
//}