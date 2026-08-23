package com.shoppingcart.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.shoppingcart.DTO.JwtUser;
import com.shoppingcart.model.Address;
import com.shoppingcart.service.AddressService;
import com.shoppingcart.service.JwtService;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService addressService;
    private final JwtService jwtService;

    public AddressController(
            AddressService addressService,
            JwtService jwtService) {

        this.addressService = addressService;
        this.jwtService = jwtService;
    }


    // =========================================================
    // GET ALL USER ADDRESSES
    // GET /api/addresses
    // =========================================================

    @GetMapping
    public ResponseEntity<?> getUserAddresses(
            @RequestHeader(
                    value = "Authorization",
                    required = false) String token) {

        try {

            JwtUser jwtUser = getJwtUser(token);

            List<Address> addresses =
                    addressService.getUserAddresses(
                            jwtUser.getUserId());

            return ResponseEntity.ok(addresses);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(e.getMessage());
        }
    }


    // =========================================================
    // GET PARTICULAR ADDRESS
    // GET /api/addresses/{id}
    // =========================================================

    @GetMapping("/{id}")
    public ResponseEntity<?> getAddress(
            @RequestHeader(
                    value = "Authorization",
                    required = false) String token,

            @PathVariable Long id) {

        try {

            JwtUser jwtUser = getJwtUser(token);

            Address address =
                    addressService.getAddressById(
                            jwtUser.getUserId(),
                            id);

            return ResponseEntity.ok(address);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }


    // =========================================================
    // CREATE ADDRESS
    // POST /api/addresses
    // =========================================================

    @PostMapping
    public ResponseEntity<?> createAddress(
            @RequestHeader(
                    value = "Authorization",
                    required = false) String token,

            @RequestBody Address address) {

        try {

            JwtUser jwtUser = getJwtUser(token);

            Address savedAddress =
                    addressService.addAddress(
                            jwtUser.getUserId(),
                            address);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(savedAddress);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }


    // =========================================================
    // UPDATE ADDRESS
    // PUT /api/addresses/{id}
    // =========================================================

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAddress(
            @RequestHeader(
                    value = "Authorization",
                    required = false) String token,

            @PathVariable Long id,

            @RequestBody Address address) {

        try {

            JwtUser jwtUser = getJwtUser(token);

            Address updatedAddress =
                    addressService.updateAddress(
                            jwtUser.getUserId(),
                            id,
                            address);

            return ResponseEntity.ok(updatedAddress);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }


    // =========================================================
    // DELETE ADDRESS
    // DELETE /api/addresses/{id}
    // =========================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAddress(
            @RequestHeader(
                    value = "Authorization",
                    required = false) String token,

            @PathVariable Long id) {

        try {

            JwtUser jwtUser = getJwtUser(token);

            addressService.deleteAddress(
                    jwtUser.getUserId(),
                    id);

            return ResponseEntity.ok(
                    "Address deleted successfully");

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }


    // =========================================================
    // SET DEFAULT ADDRESS
    // PUT /api/addresses/{id}/default
    // =========================================================

    @PutMapping("/{id}/default")
    public ResponseEntity<?> setDefaultAddress(
            @RequestHeader(
                    value = "Authorization",
                    required = false) String token,

            @PathVariable Long id) {

        try {

            JwtUser jwtUser = getJwtUser(token);

            Address address =
                    addressService.setDefaultAddress(
                            jwtUser.getUserId(),
                            id);

            return ResponseEntity.ok(address);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }


    // =========================================================
    // JWT HELPER
    // =========================================================

    private JwtUser getJwtUser(String token) {

        if (token == null || token.isBlank()) {

            throw new RuntimeException(
                    "Authorization token is missing");
        }

        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        JwtUser jwtUser =
                jwtService.extractUser(token);

        if (jwtUser == null) {

            throw new RuntimeException(
                    "Invalid authorization token");
        }

        return jwtUser;
    }
}