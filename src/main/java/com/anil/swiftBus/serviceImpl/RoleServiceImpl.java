package com.anil.swiftBus.serviceImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anil.swiftBus.dao.PermissionDAO;
import com.anil.swiftBus.dao.RoleDAO;
import com.anil.swiftBus.dto.RolePermissionDTO;
import com.anil.swiftBus.entity.Permission;
import com.anil.swiftBus.entity.Role;
import com.anil.swiftBus.entity.RolePermission;
import com.anil.swiftBus.service.RoleService;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDAO roleDAO;
	
	@Autowired
	private PermissionDAO permissionDAO;
	
	@PersistenceContext
    private EntityManager em;
	
	@Override
	public List<Role> findAll() {
		return roleDAO.findAll();
	}

	@Override
	public Role findById(Long roleId) {
		return roleDAO.findById(roleId);
	}



	@Override
	@Transactional
	public void removeAllPermissionsFromRole(Long roleId) {
		//roleDAO.deleteRolePermissions(roleId);
	}

	@Override
	public void updateRolePermissions(RolePermissionDTO dto) {
		Map<Long, List<Long>> rolePermissionsMap = dto.getRolePermissions();
System.out.println("rolePermissionsMap.entrySet()"+rolePermissionsMap.entrySet());
        for (Map.Entry<Long, List<Long>> entry : rolePermissionsMap.entrySet()) {
            Long roleId = entry.getKey();
            
            List<Long> newPermissionIds = entry.getValue().stream()
                    .filter(Objects::nonNull)
                    .filter(id -> id != 0) // defensive check
                    .collect(Collectors.toList());

            System.out.println("roleId" + roleId);
            System.out.println("newPermissionIds" + newPermissionIds);

            Role role = roleDAO.findById(roleId);

            // Current permissions
            Set<RolePermission> currentRolePermissions = new HashSet<>(role.getRolePermissions());
            Set<Long> currentPermissionIds = currentRolePermissions.stream()
                    .map(rp -> rp.getPermission().getId())
                    .collect(Collectors.toSet());

            // Remove unchecked permissions
            for (RolePermission rp : currentRolePermissions) {
                if (!newPermissionIds.contains(rp.getPermission().getId())) {
                	roleDAO.removeRolePermission(rp);
                    role.getRolePermissions().remove(rp);
                }
            }

            // Add new permissions
            for (Long pid : newPermissionIds) {
                if (!currentPermissionIds.contains(pid)) {
                    Permission permission = permissionDAO.findById(pid);
                    RolePermission rp = new RolePermission();
                    rp.setRole(role);
                    rp.setPermission(permission);
                    roleDAO.saveRolePermission(rp);
                    role.getRolePermissions().add(rp);
                }
            }

            em.flush();
        }
	}

	
	
}