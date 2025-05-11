package br.com.sarc.csw.modules.user.controller;
import br.com.sarc.csw.modules.user.model.User;
import br.com.sarc.csw.modules.user.service.UserService;
import lombok.AllArgsConstructor;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<User> getUserInfo(@AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject();
        User user = userService.getUserById(UUID.fromString(userId));
        return ResponseEntity.ok(user);
    }
}
