package com.tunadag.service;

import com.tunadag.repositories.UserRepository;
import com.tunadag.repositories.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
