package br.com.sarc.csw.auth.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import br.com.sarc.csw.auth.dto.LoginRequest;
import br.com.sarc.csw.auth.dto.TokenResponse;
import br.com.sarc.csw.auth.dto.UserRequest;
import br.com.sarc.csw.auth.dto.UserResponse;
import reactor.core.publisher.Mono;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthService {

    @Value("${keycloak.server-url}")
    private String keycloakUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    private final WebClient webClient;


    // Constantes para melhorar legibilidade
    private static final String TOKEN_URI = "/realms/%s/protocol/openid-connect/token";
    private static final String USERS_URI = "api/admin/realms/%s/users";

    public TokenResponse login(LoginRequest loginRequest) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "password");
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("username", loginRequest.getUsername());
        formData.add("password", loginRequest.getPassword());

        return webClient.post()
                .uri(String.format(keycloakUrl + TOKEN_URI, realm))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> Mono.error(new RuntimeException("Keycloak request failed: " + response.statusCode())))
                .bodyToMono(TokenResponse.class)
                .block();
    }

    public void createUser(UserRequest user, String token) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("username", user.getUsername());
        payload.put("email", user.getUsername());
        payload.put("emailVerified", true);
        payload.put("credentials", List.of(Map.of(
                "type", "password",
                "value", user.getPassword(),
                "temporary", false
        )));

        webClient.post()
            .uri(String.format(keycloakUrl + USERS_URI, realm))
            .header(HttpHeaders.AUTHORIZATION, token)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(payload)
            .retrieve()
            .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                    response -> Mono.error(new RuntimeException("Failed to create user: " + response.statusCode())))
            .toBodilessEntity()
            .block();
    }

     

    public List<UserResponse> getUsers(String token, Optional<Boolean> enabled) {
        List<Map<String, Object>> response = webClient.get()
                .uri(String.format(keycloakUrl + USERS_URI, realm))
                .header(HttpHeaders.AUTHORIZATION, token)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),errorResponse -> Mono.error(new RuntimeException("Failed to fetch users: " + errorResponse.statusCode())))
                .bodyToMono(new ParameterizedTypeReference<List<Map<String, Object>>>() {})
                .block();

        assert response != null;

        return response.stream()
                .map(map -> {
                    UserResponse u = new UserResponse();
                    u.setId((String) map.get("id"));
                    u.setUsername((String) map.get("username"));
                    return u;
                })
                .filter(user -> enabled.map(e -> user.isEnabled() == e).orElse(true))
                .toList();
    }

   
}

