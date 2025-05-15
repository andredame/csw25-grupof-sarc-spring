package br.com.sarc.csw.auth.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
// import org.springframework.web.reactive.function.BodyInserters;
// import org.springframework.web.reactive.function.client.WebClient;

import br.com.sarc.csw.auth.dto.LoginRequest;
import br.com.sarc.csw.auth.dto.TokenResponse;
import br.com.sarc.csw.auth.dto.UserRequest;
import br.com.sarc.csw.auth.dto.UserResponse;
// import reactor.core.publisher.Mono;


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

        private final RestTemplate restTemplate = new RestTemplate();


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

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(formData, headers);

        String url = String.format(keycloakUrl + TOKEN_URI, realm);

        ResponseEntity<TokenResponse> response = restTemplate.postForEntity(url, request, TokenResponse.class);

        return response.getBody();
        }



   
}

