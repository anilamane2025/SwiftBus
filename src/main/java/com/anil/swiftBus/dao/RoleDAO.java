package com.anil.swiftBus.dao;

import com.anil.swiftBus.entity.Role;
import com.anil.swiftBus.entity.RolePermission;
import com.anil.swiftBus.enums.UserType;

public interface RoleDAO {
    Role findByUserType(UserType userType);
    void saveRolePermission(RolePermission rolePermission);
}