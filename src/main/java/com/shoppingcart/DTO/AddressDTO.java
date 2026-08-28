package com.shoppingcart.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    private Long addressId;
    private String fullName;
    private String addressLine1;
    private String addressLine2;
    private String addressType;
    private String city;
    private String state;
    private String pincode;
    private String country;
    private String phone;
    private Boolean isDefault;
}