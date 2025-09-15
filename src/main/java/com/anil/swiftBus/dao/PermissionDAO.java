package com.anil.swiftBus.dao;

import java.util.List;

import com.anil.swiftBus.entity.Permission;

public interface PermissionDAO {
    List<Permission> findAll();
    Permission findById(Long id);
    Permission save(Permission permission);
    void update(Permission permission);
    void delete(Long id);
    
    boolean existsByName(String name);
}