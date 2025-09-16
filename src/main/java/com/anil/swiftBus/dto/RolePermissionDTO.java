package com.anil.swiftBus.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RolePermissionDTO {
    private Map<Long, List<Long>> rolePermissions = new HashMap<>();

	public Map<Long, List<Long>> getRolePermissions() {
		return rolePermissions;
	}

	public void setRolePermissions(Map<Long, List<Long>> rolePermissions) {
		this.rolePermissions = rolePermissions;
	}
    
    
}