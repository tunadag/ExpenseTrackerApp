package com.tunadag.services;

import com.tunadag.configuration.jwt.JwtService;
import com.tunadag.dto.request.LoginRequestDto;
import com.tunadag.dto.request.RegisterRequestDto;
import com.tunadag.dto.response.AuthenticationResponseDto;
import com.tunadag.exceptions.custom.UserAlreadyExistsException;
import com.tunadag.repositories.UserRepository;
import com.tunadag.repositories.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public String register(RegisterRequestDto request) {
        if (userRepository.findOptionalByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Email already registered.");
        }

        User auth = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();
        userRepository.save(auth);

        return "Kayıt başarılı";
    }

    public AuthenticationResponseDto authenticate(LoginRequestDto request){
        Optional<User> user = userRepository.findOptionalByEmail(request.getEmail());
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Username does not exist.");
        }
        userRepository.save(user.get());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        return AuthenticationResponseDto.builder()
                .token(jwtService.generateToken(user.get()))
                .build();
    }
}
