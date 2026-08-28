package com.shoppingcart.mapper;

import java.util.List;
import com.shoppingcart.DTO.AddressDTO;
import com.shoppingcart.DTO.UserProfileDTO;
import com.shoppingcart.model.User;

public class UserMapper {

    public static UserProfileDTO toDTO(User user) {

        UserProfileDTO dto = new UserProfileDTO();

        dto.setUserId(user.getUserId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setRole(user.getRole() != null ? user.getRole().toString() : null);

        if (user.getAddresses() != null) {
            List<AddressDTO> addressDTOs = user.getAddresses().stream()
                    .map(addr -> new AddressDTO(
                            addr.getAddressId(),
                            addr.getFullName(),
                            addr.getAddressLine1(),
                            addr.getAddressLine2(),
                            addr.getAddressType() != null ? addr.getAddressType().toString() : null,
                            addr.getCity(),
                            addr.getState(),
                            addr.getPincode(),
                            addr.getCountry(),
                            addr.getPhone(),
                            addr.getIsDefault()
                    ))
                    .toList();
            dto.setAddresses(addressDTOs);
        }

        return dto;
    }
    
    public static List<UserProfileDTO> toDTOList(List<User> users) {
        return users.stream().map(UserMapper::toDTO).toList();
    }
    
}