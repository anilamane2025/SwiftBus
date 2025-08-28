package com.anil.swiftBus.dao;

import java.util.Optional;

import com.anil.swiftBus.entity.User;

public interface UserDAO {
	Optional<User> findByUsername(String username);
    void saveUser(User user);
}