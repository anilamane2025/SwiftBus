package com.anil.swiftBus.ModelMapper;

import java.time.format.DateTimeFormatter;

import com.anil.swiftBus.dto.UserDTO;
import com.anil.swiftBus.entity.User;

public class UserMapper {

    public static UserDTO toDTO(User user) {
    	if (user == null) {
            return null;
        }
    	
        UserDTO dto = new UserDTO();
        dto.setId(user.getId().toString());
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        dto.setEnabled(user.isEnabled());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setGender(user.getGender());
        if (user.getCity() != null) {
        	dto.setCityId(user.getCity().getCityId());
            dto.setCityName(user.getCity().getCityName());
            dto.setCityState(user.getCity().getCityState());
        }

        if (user.getRole() != null && user.getRole().getUserType() != null) {
            dto.setRoleName(user.getRole().getUserType().name());
        }

        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a");
        if (user.getCreatedAt() != null) {
            dto.setCreatedAtFormatted(user.getCreatedAt().format(formatter));
        }
        if (user.getUpdatedAt() != null) {
            dto.setUpdatedAtFormatted(user.getUpdatedAt().format(formatter));
        }
        return dto;
    }
    
    public static User toEntity(UserDTO dto) {
        if (dto == null) {
            return null;
        }
        
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setEnabled(dto.isEnabled());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setGender(dto.getGender());
        
        return user;
    }
}
