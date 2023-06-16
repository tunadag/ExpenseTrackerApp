package com.tunadag.controller;

import com.tunadag.dto.request.LoginRequestDto;
import com.tunadag.dto.request.RegisterRequestDto;
import com.tunadag.dto.response.AuthenticationResponseDto;
import com.tunadag.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/register")
    @Operation(summary = "Yeni kullanıcı kaydı yapılır")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequestDto request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    @Operation(summary = "Mevcut bir kullanıcı uyuglamaya giriş yapar")
    public ResponseEntity<AuthenticationResponseDto> login(@RequestBody @Valid LoginRequestDto request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
