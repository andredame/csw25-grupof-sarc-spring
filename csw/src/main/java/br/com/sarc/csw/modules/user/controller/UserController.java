package br.com.sarc.csw.modules.user.controller;

import br.com.sarc.csw.modules.user.model.User;
import br.com.sarc.csw.modules.user.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserService userService;

    // existente: Dados do usuário autenticado
    @GetMapping("/me")
    public ResponseEntity<User> getUserInfo(@AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject();
        User user = userService.getUserById(UUID.fromString(userId));
        return ResponseEntity.ok(user);
    }

    // rota: Listar todos os usuários
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('COORDENADOR')")
    public ResponseEntity<List<User>> listar() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // rota: Buscar usuário por ID
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('COORDENADOR') or (#id == principal.subject)")
    public ResponseEntity<User> buscarPorId(@PathVariable UUID id, @AuthenticationPrincipal Jwt jwt) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    // rota: Criar um novo usuário
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> criar(@RequestBody User user) {
        User novoUser = userService.createUser(user);
        return ResponseEntity.status(201).body(novoUser);
    }

    // rota: Atualizar um usuário existente
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (#id == principal.subject)")
    public ResponseEntity<User> atualizar(@PathVariable UUID id, @RequestBody User user, @AuthenticationPrincipal Jwt jwt) {
        user.setId(id);
        User updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(updatedUser);
    }

    // rota: Deletar um usuário
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}