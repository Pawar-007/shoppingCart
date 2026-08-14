package com.shoppingcart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.shoppingcart.model.Brand;
import com.shoppingcart.service.AuthService;
import com.shoppingcart.service.BrandService;

@RestController
@RequestMapping("/brands")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @Autowired
    private AuthService authService;

    // ADMIN ONLY
    @PostMapping
    public ResponseEntity<?> saveBrand(
            @RequestHeader(value = "Authorization", required = false) String token,
            @RequestBody Brand brand) {

        if (!authService.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Only ADMIN can add brand");
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(brandService.saveBrand(brand));
    }

    // ADMIN ONLY
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBrand(
            @RequestHeader(value = "Authorization", required = false) String token,
            @PathVariable Long id,
            @RequestBody Brand brand) {

        if (!authService.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Only ADMIN can update brand");
        }

        return ResponseEntity.ok(
                brandService.updateBrand(id, brand)
        );
    }

    // ADMIN ONLY
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBrand(
            @RequestHeader(value = "Authorization", required = false) String token,
            @PathVariable Long id) {

        if (!authService.isAdmin(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Only ADMIN can delete brand");
        }

        brandService.deleteBrand(id);

        return ResponseEntity.ok("Brand deleted successfully");
    }

    // CUSTOMER + ADMIN
    @GetMapping
    public ResponseEntity<List<Brand>> getAllBrands() {

        return ResponseEntity.ok(
                brandService.getAllBrands()
        );
    }

    // CUSTOMER + ADMIN
    @GetMapping("/{id}")
    public ResponseEntity<Brand> getBrand(@PathVariable Long id) {

        return ResponseEntity.ok(
                brandService.getBrand(id)
        );
    }
}