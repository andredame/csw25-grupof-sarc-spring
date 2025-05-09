package br.com.sarc.csw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration

public class Beans{
        @Bean
        public WebClient webClient(WebClient.Builder builder) {
            return builder.build();
        }
}