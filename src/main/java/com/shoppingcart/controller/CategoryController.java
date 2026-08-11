//package com.shoppingcart.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.shoppingcart.model.Category;
//import com.shoppingcart.service.CategoryService;
//
//@RestController
//@RequestMapping("/api/categories")
//public class CategoryController {
//
//    @Autowired
//    private CategoryService service;
//
//    @GetMapping
//    public List<Category> getAllCategories() {
//
//        return service.getAllCategories();
//    }
//
//    @PostMapping
//    public Category saveCategory(
//            @RequestBody Category category) {
//
//        return service.saveCategory(category);
//    }
//}