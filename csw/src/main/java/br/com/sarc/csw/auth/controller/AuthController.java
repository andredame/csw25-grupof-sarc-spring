package br.com.sarc.csw.auth.controller;

import br.com.sarc.csw.auth.dto.LoginRequest;
import br.com.sarc.csw.auth.dto.TokenResponse;
import br.com.sarc.csw.auth.dto.UserRequest;
import br.com.sarc.csw.auth.dto.UserResponse;
import br.com.sarc.csw.auth.Service.AuthService;
import br.com.sarc.csw.modules.user.service.UserService;
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

   
}