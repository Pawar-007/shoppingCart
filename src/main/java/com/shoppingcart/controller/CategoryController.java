package com.shoppingcart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.shoppingcart.model.Category;
import com.shoppingcart.service.AuthService;
import com.shoppingcart.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    private AuthService authService;
    
    @PostMapping
    public ResponseEntity<?> createCategory(
    		@RequestHeader(value="Authorization",required = false) String token,
            @RequestBody Category category) {
    	
    	if (!authService.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Only ADMIN can update brand");
        }


        Category savedCategory = categoryService.saveCategory(category);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(
    		@RequestHeader(value="Authorization",required = false) String token,
            @PathVariable Long id,
            @RequestBody Category category) {
    	
    	if (!authService.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Only ADMIN can update brand");
        }



        Category updatedCategory =
                categoryService.updateCategory(id, category);

        return ResponseEntity.ok(updatedCategory);
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {

        return ResponseEntity.ok(
                categoryService.getAllCategories()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                categoryService.getCategory(id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(
    		@RequestHeader(value="Authorization",required = false) String token,
            @PathVariable Long id) {
    	
    	if (!authService.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Only ADMIN can update brand");
        }
        categoryService.deleteCategory(id);

        return ResponseEntity.noContent().build();
    }
}