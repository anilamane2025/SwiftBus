package com.anil.swiftBus.service;

import java.util.List;

import com.anil.swiftBus.dto.PermissionDTO;
import com.anil.swiftBus.entity.Permission;

public interface PermissionService {
    List<Permission> getAllPermissions();
    Permission getPermissionById(Long id);
    void addPermission(PermissionDTO permission);
    void updatePermission(Permission permission);
    void deletePermission(Long id);
	boolean existsByName(String name);
}