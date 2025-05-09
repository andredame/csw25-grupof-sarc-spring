package br.com.sarc.csw.user;
import br.com.sarc.csw.user.JwtService;
import br.com.sarc.csw.user.User;
import br.com.sarc.csw.user.UserService;
import lombok.AllArgsConstructor;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final JwtService jwtService;
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<User> getUserInfo(@AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject();
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }
}
