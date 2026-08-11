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
//import com.shoppingcart.model.Brand;
//import com.shoppingcart.service.BrandService;
//
//@RestController
//@RequestMapping("/api/brands")
//public class BrandController {
//
//    @Autowired
//    private BrandService service;
//
//    @GetMapping
//    public List<Brand> getAllBrands() {
//
//        return service.getAllBrands();
//    }
//
//    @PostMapping
//    public Brand saveBrand(
//            @RequestBody Brand brand) {
//
//        return service.saveBrand(brand);
//    }
//}