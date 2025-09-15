package com.anil.swiftBus.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ChangePasswordDTO {

    private Long id; // user id (hidden field)

    @NotBlank(message = "New password cannot be empty")
    @Size(min = 8, message = "Your password must have at least 8 characters")
    private String newPassword;

    @NotBlank(message = "Confirm password cannot be empty")
    private String confirmPassword;

    // getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getNewPassword() {
        return newPassword;
    }
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}