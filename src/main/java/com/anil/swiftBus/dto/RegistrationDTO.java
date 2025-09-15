package com.anil.swiftBus.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RegistrationDTO {
	
	private Long id;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Username is required")
    @Email(message = "Enter a valid user email")
    private String username;

    @NotBlank(message = "Phone number is required")
    @Size(max = 10, message = "Phone number should be at most 10 digits.")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be a valid 10-digit number.")
    private String phoneNumber;

   
    @NotBlank(message = "Gender is required")
    private String gender;
    @NotBlank(message = "State is required")
    private String cityState;
    @NotNull(message = "City is required")
    private Long cityId;

    @Size(min = 8, message = "Password must have at least 8 characters")
    private String password;

    private String confirmPassword;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCityState() {
		return cityState;
	}

	public void setCityState(String cityState) {
		this.cityState = cityState;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
    
    

}