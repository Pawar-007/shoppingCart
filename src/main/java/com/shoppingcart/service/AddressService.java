package com.shoppingcart.service;

import java.util.List;

import com.shoppingcart.model.Address;

public interface AddressService {

    Address addAddress(Long userId, Address address);

    List<Address> getUserAddresses(Long userId);

    Address getAddressById(Long userId, Long addressId);

    Address updateAddress(Long userId, Long addressId, Address address);

    void deleteAddress(Long userId, Long addressId);
}