package br.com.sarc.csw.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer; // Import para csrf
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

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
            "/webjars/**" // Algumas versões do swagger podem precisar disso
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable) // Nova forma de desabilitar CSRF desde Spring Security 6.x
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(SWAGGER_PATHS).permitAll() // Permitir acesso aos paths do Swagger
                .requestMatchers("/public/**").permitAll()
                .requestMatchers("/api/auth/login").permitAll()
                .requestMatchers("/api/admin/**").hasRole("ADMIN") // Usando hasRole
                .requestMatchers("/api/professor/**").hasRole("PROFESSOR") // Usando hasRole
                .requestMatchers("/api/aluno/**").hasRole("ALUNO") // Usando hasRole
                .requestMatchers("/api/coordenador/**").hasRole("COORDENADOR") // Usando hasRole
                .anyRequest().authenticated()
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
