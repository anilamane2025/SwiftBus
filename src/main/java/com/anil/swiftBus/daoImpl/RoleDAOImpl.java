package com.anil.swiftBus.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.anil.swiftBus.dao.RoleDAO;
import com.anil.swiftBus.entity.Role;
import com.anil.swiftBus.entity.RolePermission;
import com.anil.swiftBus.enums.UserType;

@Repository
public class RoleDAOImpl implements RoleDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Role findByUserType(UserType userType) {
        return em.createQuery("SELECT r FROM Role r WHERE r.userType = :role", Role.class)
                .setParameter("role", userType)
                .getSingleResult();
    }

	@Override
	public void saveRolePermission(RolePermission rolePermission) {
		em.persist(rolePermission);
	}

	@Override
	public List<Role> findAll() {
		return em.createQuery("SELECT DISTINCT r FROM Role r LEFT JOIN FETCH r.rolePermissions rp LEFT JOIN FETCH rp.permission ORDER BY r.id ASC", Role.class)
                .getResultList();
	}

	@Override
	public Role findById(Long roleId) {
		return em.find(Role.class, roleId);
	}

	@Override
	public void removeRolePermission(RolePermission rolePermission) {
		em.remove(rolePermission);
	}

}