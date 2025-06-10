package br.com.sarc.csw.service.auth;

import br.com.sarc.csw.auth.dto.LoginRequest;
import br.com.sarc.csw.auth.dto.TokenResponse;
import br.com.sarc.csw.auth.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        authService = new AuthService(restTemplate);
        authService.setKeycloakUrl("http://localhost:8080");
        authService.setRealm("meu-reino");
        authService.setClientId("meu-client");
        authService.setClientSecret("segredo");
    }

    @Test
    void deveRetornarTokenQuandoLoginForValido() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("usuario");
        loginRequest.setPassword("senha");

        TokenResponse fakeResponse = new TokenResponse();
        fakeResponse.setAccessToken("fake-token");
        fakeResponse.setRefreshToken("fake-refresh");

        String url = "http://localhost:8080/realms/meu-reino/protocol/openid-connect/token";

        when(restTemplate.postForEntity(
                eq(url),
                any(HttpEntity.class),
                eq(TokenResponse.class)
        )).thenReturn(new ResponseEntity<>(fakeResponse, HttpStatus.OK));

        // Act
        TokenResponse response = authService.login(loginRequest);

        // Assert
        assertEquals("fake-token", response.getAccessToken());
        assertEquals("fake-refresh", response.getRefreshToken());
    }
}
