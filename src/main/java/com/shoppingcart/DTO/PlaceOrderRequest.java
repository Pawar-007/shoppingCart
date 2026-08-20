package com.shoppingcart.DTO;

import java.util.List;

import lombok.Data;

@Data
public class PlaceOrderRequest {

    private Long addressId;

    private List<Long> selectedCartItemIds;

}