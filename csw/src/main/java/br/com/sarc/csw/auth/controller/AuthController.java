package br.com.sarc.csw.auth.controller;

import br.com.sarc.csw.auth.Service.AuthService;
import br.com.sarc.csw.auth.dto.LoginRequest;
import br.com.sarc.csw.auth.dto.TokenResponse;
import br.com.sarc.csw.auth.dto.UserRequest;
import br.com.sarc.csw.auth.dto.UserResponse;
import br.com.sarc.csw.user.JwtService;
import br.com.sarc.csw.user.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private final AuthService authService;

    // Endpoint para login
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest loginRequest) {
        TokenResponse tokenResponse = authService.login(loginRequest);
        return ResponseEntity.ok(tokenResponse);
    }

    // Endpoint para criar um usuário
    @PostMapping("/users")
    public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest, @RequestHeader("Authorization") String token) {
        authService.createUser(userRequest, token.replace("Bearer ", ""));
        return ResponseEntity.ok("User created successfully");
    }

    // Endpoint para listar usuários
    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getUsers(@RequestHeader("Authorization") String token, @RequestParam Optional<Boolean> enabled) {
        List<UserResponse> users = authService.getUsers(token.replace("Bearer ", ""), enabled);
        return ResponseEntity.ok(users);
    }
}