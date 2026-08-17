package com.shoppingcart.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shoppingcart.model.Address;
import com.shoppingcart.model.User;
import com.shoppingcart.repository.AddressRepository;
import com.shoppingcart.repository.UserRepository;
import com.shoppingcart.service.AddressService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

	
    private final AddressRepository addressRepository;
    
    private final UserRepository userRepository;


    @Override
    public Address addAddress(Long userId, Address address) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found with id: " + userId));

        address.setUser(user);

        if (address.getIsDefault() == null) {
            address.setIsDefault(false);
        }

        return addressRepository.save(address);
    }


    @Override
    public List<Address> getUserAddresses(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new RuntimeException(
                    "User not found with id: " + userId);
        }

        return addressRepository.findByUser_UserId(userId);
    }


    @Override
    public Address getAddressById(Long userId, Long addressId) {

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Address not found with id: " + addressId));

        // Check address belongs to current user
        if (!address.getUser().getUserId().equals(userId)) {
            throw new RuntimeException(
                    "You are not authorized to access this address");
        }

        return address;
    }


    @Override
    public Address updateAddress(
            Long userId,
            Long addressId,
            Address updatedAddress) {

        Address existingAddress =
                addressRepository.findById(addressId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Address not found with id: " + addressId));

        // Ownership check
        if (!existingAddress.getUser().getUserId().equals(userId)) {
            throw new RuntimeException(
                    "You are not authorized to update this address");
        }

        existingAddress.setFullName(updatedAddress.getFullName());
        existingAddress.setPhone(updatedAddress.getPhone());
        existingAddress.setAddressLine1(updatedAddress.getAddressLine1());
        existingAddress.setAddressLine2(updatedAddress.getAddressLine2());
        existingAddress.setCity(updatedAddress.getCity());
        existingAddress.setState(updatedAddress.getState());
        existingAddress.setCountry(updatedAddress.getCountry());
        existingAddress.setPincode(updatedAddress.getPincode());
        existingAddress.setAddressType(updatedAddress.getAddressType());
        existingAddress.setIsDefault(updatedAddress.getIsDefault());

        return addressRepository.save(existingAddress);
    }


    @Override
    public void deleteAddress(Long userId, Long addressId) {

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Address not found with id: " + addressId));

        // Ownership check
        if (!address.getUser().getUserId().equals(userId)) {
            throw new RuntimeException(
                    "You are not authorized to delete this address");
        }

        addressRepository.delete(address);
    }
}