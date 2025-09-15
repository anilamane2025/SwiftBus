package com.anil.swiftBus.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class PermissionDTO {
	private Long id;

    @NotBlank(message = "Name is required")
    @Pattern(regexp = "^[A-Za-z0-9_-]+$", message = "Name must not contain spaces")
    private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    
}