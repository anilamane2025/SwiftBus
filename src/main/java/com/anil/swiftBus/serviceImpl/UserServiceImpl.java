package com.anil.swiftBus.serviceImpl;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anil.swiftBus.dao.UserDAO;
import com.anil.swiftBus.entity.User;
import com.anil.swiftBus.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        userDAO.saveUser(user);
    }
}