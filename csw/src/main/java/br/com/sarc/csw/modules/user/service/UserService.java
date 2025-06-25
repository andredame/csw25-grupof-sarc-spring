package br.com.sarc.csw.modules.user.service;

import br.com.sarc.csw.modules.user.model.User;
import br.com.sarc.csw.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + id));
    }



    public User createUser(User user) {
        // Adicione validações, se necessário (ex.: verificar email único)
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        if (!userRepository.existsById(user.getId())) {
            throw new RuntimeException("Usuário não encontrado: " + user.getId());
        }
        return userRepository.save(user);
    }

    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado: " + id);
        }
        userRepository.deleteById(id);
    }

    public List<User> listarTodosUsuarios() {
        return userRepository.findAllWithRoles(); // Alterado para usar a query otimizada
    }

    public Optional<User> buscarUsuarioPorId(UUID id) {
        return userRepository.findByIdWithRoles(id); // Alterado para usar a query otimizada
    }
}