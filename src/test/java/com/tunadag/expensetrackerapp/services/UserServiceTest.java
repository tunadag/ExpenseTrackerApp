package com.tunadag.expensetrackerapp.services;

import com.tunadag.repositories.UserRepository;
import com.tunadag.repositories.entity.User;
import com.tunadag.repositories.entity.enums.State;
import com.tunadag.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testFindActiveById() {
        // Kullanıcı
        User user = new User();
        user.setOid(1L);
        user.setState(State.ACTIVE);

        Mockito.when(userRepository.findActiveById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = userService.findActiveById(1L);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(user, result.get());
    }

    @Test
    public void testFindActiveById_NotFound() {
        Mockito.when(userRepository.findActiveById(2L)).thenReturn(Optional.empty());

        Optional<User> result = userService.findActiveById(2L);

        Assertions.assertFalse(result.isPresent());
    }

}
