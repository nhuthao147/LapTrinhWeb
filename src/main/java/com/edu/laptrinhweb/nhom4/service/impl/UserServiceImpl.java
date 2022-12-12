package com.edu.laptrinhweb.nhom4.service.impl;

import com.edu.laptrinhweb.nhom4.model.User;
import com.edu.laptrinhweb.nhom4.repository.UserRepository;
import com.edu.laptrinhweb.nhom4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
@Component
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    public Page<User> findAll(org.springframework.data.domain.Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public long count() {
        return userRepository.count();
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void removeUserById(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }
}
