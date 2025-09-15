package com.anil.swiftBus.daoImpl;

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
}