package com.anil.swiftBus.service;

import com.anil.swiftBus.entity.User;

public interface UserService {
    User findByUsername(String username);
    void saveUser(User user);
}