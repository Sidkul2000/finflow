package com.java.project.FinFlow.Services;

import com.java.project.FinFlow.DataRepositories.UserRepository;
import com.java.project.FinFlow.Tables.User;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public User createUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }
    
    public Optional<User> getById(Long userId) {
    	return userRepository.findById(userId);
    }

    public Float getUserBudget(String userId) {
        return userRepository.getBudgetByUserId(userId);
    }

    public User setBudget(Long userId, Float newBudget) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setBudget(newBudget);
        return userRepository.save(user);
    }
}