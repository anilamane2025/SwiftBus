package com.anil.swiftBus.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anil.swiftBus.dao.PermissionDAO;
import com.anil.swiftBus.dao.RoleDAO;
import com.anil.swiftBus.dto.PermissionDTO;
import com.anil.swiftBus.entity.Permission;
import com.anil.swiftBus.entity.Role;
import com.anil.swiftBus.entity.RolePermission;
import com.anil.swiftBus.enums.UserType;
import com.anil.swiftBus.service.PermissionService;

@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionDAO permissionDAO;
    
    @Autowired
    private RoleDAO roleDAO;

    @Override
    public List<Permission> getAllPermissions() {
        return permissionDAO.findAll();
    }

    @Override
    public Permission getPermissionById(Long id) {
        return permissionDAO.findById(id);
    }

    @Override
    @Transactional
    public void addPermission(PermissionDTO permission) {
    	Permission permissionEntity = new Permission();
    	permissionEntity.setName(permission.getName().toUpperCase());
        Permission saved = permissionDAO.save(permissionEntity);
        
     // Find ADMIN role 
        Role adminRole = roleDAO.findByUserType(UserType.ADMIN);

        // Check if already assigned
        boolean alreadyAssigned = adminRole.getRolePermissions().stream()
                .anyMatch(rp -> rp.getPermission().getId().equals(saved.getId()));

        if (!alreadyAssigned) {
            // Create RolePermission
            RolePermission rp = new RolePermission();
            rp.setRole(adminRole);
            rp.setPermission(saved);

            roleDAO.saveRolePermission(rp);

            adminRole.getRolePermissions().add(rp);
        }
    }

    @Override
    public void updatePermission(Permission permission) {
        permissionDAO.update(permission);
    }

    @Override
    public void deletePermission(Long id) {
    	try {
            permissionDAO.delete(id);
        } catch (IllegalStateException e) {
            // rethrow for controller
            throw e;
        }
    }

	@Override
	public boolean existsByName(String name) {
		if (permissionDAO.existsByName(name)) {
            return true;
        }
		return false;
	}
}
