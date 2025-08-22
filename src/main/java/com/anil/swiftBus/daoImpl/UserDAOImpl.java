package com.anil.swiftBus.daoImpl;

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
    public User findByUsername(String username) {
        try {
        	return entityManager.createQuery("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;  // Or throw custom exception
        } catch (Exception e) {
            throw new DataAccessException("Error accessing data", e);
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