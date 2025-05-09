// filepath: d:\Faculdade\semestre_6\construcao_software\-csw25-grupof-sarc-spring\csw\src\main\java\br\com\sarc\csw\config\SecurityConfig.java
package br.com.sarc.csw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/public/**").permitAll()
                .requestMatchers("/api/auth/login").permitAll() // Permitir acesso público ao logi
                .requestMatchers("/api/admin/**").hasAuthority("ADMIN") // Apenas usuários com ROLE_ADMIN

                .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        return http.build();
    }
}