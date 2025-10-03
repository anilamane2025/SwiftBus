package com.anil.swiftBus.ModelMapper;

import java.time.format.DateTimeFormatter;

import com.anil.swiftBus.dto.AgentDTO;
import com.anil.swiftBus.entity.AgentCommissionRule;
import com.anil.swiftBus.entity.User;
import com.anil.swiftBus.enums.CommissionType;

public class AgentMapper extends UserMapper {

    public static AgentDTO toDTO(User user, AgentCommissionRule rule) {
        if (user == null) {
            return null;
        }

        AgentDTO dto = new AgentDTO();

        // map common fields from User
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

        if (rule != null) {
            dto.setCommissionType(rule.getCommissionType().name());
            dto.setCommissionValue(rule.getCommissionValue());
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a");
        if (user.getCreatedAt() != null) {
            dto.setCreatedAtFormatted(user.getCreatedAt().format(formatter));
        }
        if (user.getUpdatedAt() != null) {
            dto.setUpdatedAtFormatted(user.getUpdatedAt().format(formatter));
        }
        return dto;
    }

    public static User toEntity(AgentDTO dto) {
        if (dto == null) {
            return null;
        }

        User user = UserMapper.toEntity(dto); // reuse user mapping
        return user;
    }

    public static AgentCommissionRule toRuleEntity(AgentDTO dto, User agent) {
        if (dto == null || agent == null) {
            return null;
        }

        AgentCommissionRule rule = new AgentCommissionRule();
        rule.setAgent(agent);
        if (dto.getCommissionType() != null) {
            try {
                CommissionType type = CommissionType.valueOf(dto.getCommissionType().toUpperCase());
                rule.setCommissionType(type);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid commission type: " + dto.getCommissionType());
            }
        }
        
        rule.setCommissionValue(dto.getCommissionValue());

        return rule;
    }
}
