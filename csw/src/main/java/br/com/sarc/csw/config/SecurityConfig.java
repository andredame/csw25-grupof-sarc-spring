// filepath: d:\Faculdade\semestre_6\construcao_software\-csw25-grupof-sarc-spring\csw\src\main\java\br\com\sarc\csw\config\SecurityConfig.java
package br.com.sarc.csw.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desabilita CSRF
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/public/**", "/v3/api-docs/**","/swagger-ui/**","/swagger-ui.html").permitAll()
                .requestMatchers("/api/auth/login").permitAll() 
                .requestMatchers("/api/admin/**").hasAuthority("ADMIN") // Apenas usuários com ROLE_SADMIN
                .requestMatchers("/api/professor/**").hasAuthority("PROFESSOR") // Apenas PROFESSOR
                .requestMatchers("/api/aluno/**").hasAuthority("ALUNO") // Apenas ALUNO
                .requestMatchers("/api/coordenador/**").hasAuthority("COORDENADOR") // Apenas COORDENADOR
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
                System.out.println("Roles: " + rolesObj);
                if (rolesObj instanceof List<?>) {
                    List<?> rolesList = (List<?>) rolesObj;
                    for (Object role : rolesList) {
                        if (role instanceof String roleStr) {
                            authorities.add(new SimpleGrantedAuthority("ROLE_" + roleStr));
                            
                        }
                    }
                }
            }

            return authorities;
        });
        return converter;
    }
}