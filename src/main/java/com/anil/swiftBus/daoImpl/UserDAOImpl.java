package com.anil.swiftBus.daoImpl;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.anil.swiftBus.dao.UserDAO;
import com.anil.swiftBus.entity.User;
import com.anil.swiftBus.exception.DataAccessException;

@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<User> findByUsername(String username) {
        try {
        	User user = entityManager.createQuery(
                    "SELECT u FROM User u " +
                            "LEFT JOIN FETCH u.role r " +       //  User has a single Role (ManyToOne)
                            "LEFT JOIN FETCH r.rolePermissions rp " +
                            "LEFT JOIN FETCH rp.permission p " +
                            "WHERE u.username = :username", User.class)
                        .setParameter("username", username)
                        .getSingleResult();

                    return Optional.of(user);

                } catch (NoResultException e) {
                    return Optional.empty();  // correct way for Optional
                } catch (Exception e) {
                    throw new RuntimeException("Error accessing data", e); // wrap in runtime exception
                }
    }
    
    @Override
    @Transactional
    public void saveUser(User user) {
        try {
            entityManager.persist(user);
        } catch (Exception e) {
            throw new DataAccessException("Error saving user", e);
        }
    }
}