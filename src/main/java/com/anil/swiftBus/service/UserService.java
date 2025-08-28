package com.anil.swiftBus.service;

import java.util.Optional;

import com.anil.swiftBus.entity.User;

public interface UserService {
	Optional<User> findByUsername(String username);
    void saveUser(User user);
}