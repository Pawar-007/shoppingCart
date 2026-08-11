//package com.shoppingcart.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.shoppingcart.DTO.ProductDTO;
//import com.shoppingcart.model.Product;
//import com.shoppingcart.service.ProductService;
//
//
//
//@RestController
//@RequestMapping("/api/products")
//@CrossOrigin("*")
//public class ProductController {
//
//    @Autowired
//    private ProductService productService;
//
//    @GetMapping
//    public List<ProductDTO> getAllProducts() {
//
//        return productService.getAllProducts();
//    }
//
//    @GetMapping("/{id}")
//    public ProductDTO getProductById(@PathVariable Long id) {
//
//        return productService.getProduct(id);
//    }
//
//    @GetMapping("/search")
//    public List<ProductDTO> searchProduct(
//            @RequestParam String keyword) {
//
//        return productService.search(keyword);
//    }
//
//    @PostMapping
//    public Product addProduct(@RequestBody Product product) {
//
//        return productService.addProduct(product);
//    }
//
//    @PutMapping("/{id}")
//    public Product updateProduct(@PathVariable Long id,
//                                 @RequestBody Product product) {
//
//        return productService.updateProduct(id, product);
//    }
//
//    @DeleteMapping("/{id}")
//    public String deleteProduct(@PathVariable Long id) {
//
//        productService.deleteProduct(id);
//
//        return "Product Deleted Successfully";
//    }
//}