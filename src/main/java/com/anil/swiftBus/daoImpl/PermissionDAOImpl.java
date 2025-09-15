package com.anil.swiftBus.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.anil.swiftBus.dao.PermissionDAO;
import com.anil.swiftBus.entity.Permission;

@Repository
@Transactional
public class PermissionDAOImpl implements PermissionDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Permission> findAll() {
        return em.createQuery("FROM Permission p ORDER BY p.id ASC", Permission.class).getResultList();
    }

    @Override
    public Permission findById(Long id) {
        return em.find(Permission.class, id);
    }

    @Override
    public Permission save(Permission permission) {
    	
    	em.persist(permission);
    	em.flush();
        return permission;
    }

    @Override
    public void update(Permission permission) {
        em.merge(permission);
    }

    @Override
    public void delete(Long id) {
     
        Permission permission = em.find(Permission.class, id);
        if (permission == null) {
            throw new IllegalArgumentException("Permission not found");
        }

        // Check if assigned to roles
        Long count = em.createQuery(
            "SELECT COUNT(rp) FROM RolePermission rp WHERE rp.permission.id = :pid", Long.class)
            .setParameter("pid", id)
            .getSingleResult();

        if (count > 0) {
            throw new IllegalStateException("Permission is assigned to roles, cannot delete");
        }

        em.remove(permission);
    }

	@Override
	public boolean existsByName(String name) {
		Long count = em.createQuery("SELECT COUNT(p) FROM Permission p WHERE UPPER(p.name) = :name", Long.class)
                .setParameter("name", name.toUpperCase())
                .getSingleResult();
 return count > 0;
	}
}
