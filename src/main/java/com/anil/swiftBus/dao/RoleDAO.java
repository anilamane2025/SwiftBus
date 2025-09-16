package com.anil.swiftBus.dao;

import java.util.List;

import com.anil.swiftBus.entity.Role;
import com.anil.swiftBus.entity.RolePermission;
import com.anil.swiftBus.enums.UserType;

public interface RoleDAO {
	List<Role> findAll();
	Role findById(Long roleId);
    Role findByUserType(UserType userType);
    void saveRolePermission(RolePermission rolePermission);
    void removeRolePermission(RolePermission rolePermission);

}