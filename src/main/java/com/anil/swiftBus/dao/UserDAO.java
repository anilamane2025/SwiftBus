package com.anil.swiftBus.dao;

import java.util.List;
import java.util.Optional;

import com.anil.swiftBus.entity.User;

public interface UserDAO {
	Optional<User> findByUsername(String username);
    
    Optional<User> findById(Long id);
    
    void save(User user);
    void update(User user);
    void delete(Long id);
    List<User> findAll();
    
    boolean existsByUsername(String username);
    boolean existsByPhoneNumber(String phoneNumber);
}