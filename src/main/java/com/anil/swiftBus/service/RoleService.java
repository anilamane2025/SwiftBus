package com.anil.swiftBus.service;

import java.util.List;

import com.anil.swiftBus.dto.RolePermissionDTO;
import com.anil.swiftBus.entity.Role;

public interface RoleService {

	List<Role> findAll();
	Role findById(Long roleId);
	void updateRolePermissions(RolePermissionDTO rolePermissionDTO);

    void removeAllPermissionsFromRole(Long roleId);
}