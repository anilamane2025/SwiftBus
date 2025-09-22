package com.anil.swiftBus.dto;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AgentDTO extends UserDTO {

	@NotBlank(message = "Commission type is required")
    private String commissionType;   // "PERCENTAGE" or "FIXED"
	@NotNull(message = "Commission value is required")
	@DecimalMin(value = "0.0", inclusive = false, message = "Commission value must be greater than 0")
    private BigDecimal commissionValue;

    // Getters and setters
    public String getCommissionType() {
        return commissionType;
    }

    public void setCommissionType(String commissionType) {
        this.commissionType = commissionType;
    }

    public BigDecimal getCommissionValue() {
        return commissionValue;
    }

    public void setCommissionValue(BigDecimal commissionValue) {
        this.commissionValue = commissionValue;
    }
}
