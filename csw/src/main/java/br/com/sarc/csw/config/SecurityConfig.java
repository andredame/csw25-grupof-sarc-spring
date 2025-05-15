package br.com.sarc.csw.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

    // Caminhos do Swagger/OpenAPI para permitir acesso
    private static final String[] SWAGGER_PATHS = {
        "/swagger-ui.html",
        "/swagger-ui/**",
        "/v3/api-docs",
        "/v3/api-docs/**",
        "/swagger-resources",
        "/swagger-resources/**",
        "/webjars/**",
        "/favicon.ico"
    };

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000", "https://meditamamente.com", "https://*.meditamamente.com"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Configuração de CORS
            .csrf(AbstractHttpConfigurer::disable) // Desabilitar CSRF
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(SWAGGER_PATHS).permitAll() // Permitir acesso público ao Swagger
                .requestMatchers("/public/**").permitAll() // Outras rotas públicas
                .requestMatchers("/api/auth/login").permitAll() // Login público
                .requestMatchers("/api/admin/**").hasRole("ADMIN") // Rotas para ADMIN
                .requestMatchers("/api/professor/**").hasRole("PROFESSOR") // Rotas para PROFESSOR
                .requestMatchers("/api/aluno/**").hasRole("ALUNO") // Rotas para ALUNO
                .requestMatchers("/api/coordenador/**").hasRole("COORDENADOR") // Rotas para COORDENADOR
                .anyRequest().authenticated() // Qualquer outra rota requer autenticação
            )
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));
        return http.build();
    }

    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            List<GrantedAuthority> authorities = new ArrayList<>();
            Map<String, Object> realmAccess = jwt.getClaim("realm_access");

            if (realmAccess != null && realmAccess.containsKey("roles")) {
                Object rolesObj = realmAccess.get("roles");
                System.out.println("Roles from JWT: " + rolesObj); // Bom para debug
                if (rolesObj instanceof List<?>) {
                    List<?> rolesList = (List<?>) rolesObj;
                    for (Object role : rolesList) {
                        if (role instanceof String roleStr) {
                            // Adiciona o prefixo ROLE_ aqui, pois hasRole espera isso implicitamente
                            authorities.add(new SimpleGrantedAuthority("ROLE_" + roleStr.toUpperCase()));
                            System.out.println("Granted Authority: ROLE_" + roleStr.toUpperCase()); // Bom para debug
                        }
                    }
                }
            }
            return authorities;
        });
        return converter;
    }
}