package com.tunadag.services;

import com.tunadag.exceptions.custom.UserNotFoundException;
import com.tunadag.repositories.UserRepository;
import com.tunadag.repositories.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findActiveById(Long userOid) {
        return userRepository.findActiveById(userOid);
    }


    public void saveAll(List<User> users) {
        userRepository.saveAll(users);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User getCurrentUser() {
        User user = findActiveById((Long)
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getCredentials()
        ).orElseThrow(() -> new UserNotFoundException("No such user."));
        return user;
    }
}
