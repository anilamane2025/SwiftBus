package com.anil.swiftBus.dao;

import com.anil.swiftBus.entity.User;

public interface UserDAO {
    User findByUsername(String username);
    void saveUser(User user);
}