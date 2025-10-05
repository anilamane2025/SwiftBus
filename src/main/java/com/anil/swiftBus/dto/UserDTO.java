package com.anil.swiftBus.dto;
import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.anil.swiftBus.validation.ValidPhoneNumber;

public class UserDTO {
	private String id;

	@NotNull(message = "Username is required")
    @Email(message = "Invalid email format.")
	@NotBlank(message = "User name cannot be blank.")
    @Size(max = 150, message = "Email should be less than 150 characters.")
    private String username;  // This will be the email

//    @NotBlank(message = "Password cannot be blank.")
//    @Size(min = 6, message = "Password should be at least 6 characters.")
    private String password;

    private boolean enabled;

    @Size(max = 10, message = "Phone number should be at most 10 digits.")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be a valid 10-digit number.")
    @ValidPhoneNumber(region = "IN", mobileOnly = true)
    private String phoneNumber;

    @NotBlank(message = "First name cannot be blank.")
    @Size(max = 150, message = "First name should be less than 150 characters.")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank.")
    @Size(max = 150, message = "Last name should be less than 150 characters.")
    private String lastName;
    
    @NotBlank(message = "Gender is required")
    private String gender;

    

    @NotNull(message = "City is required")
    private Long cityId;
    
    private String cityName;  // This would come from the City entity
    
    @NotBlank(message = "State is required")
    private String cityState;

    private String roleName;  // This would come from the Role entity

    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    private String createdAtFormatted;
    private String updatedAtFormatted;
   
    // Getters and Setters

    public String getUsername() {
        return username;
    }

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityState() {
		return cityState;
	}

	public void setCityState(String cityState) {
		this.cityState = cityState;
	}

	public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

	public String getCreatedAtFormatted() {
		return createdAtFormatted;
	}

	public void setCreatedAtFormatted(String createdAtFormatted) {
		this.createdAtFormatted = createdAtFormatted;
	}

	public String getUpdatedAtFormatted() {
		return updatedAtFormatted;
	}

	public void setUpdatedAtFormatted(String updatedAtFormatted) {
		this.updatedAtFormatted = updatedAtFormatted;
	}
	
	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}
	
	
    
}
