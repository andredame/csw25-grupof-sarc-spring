package br.com.sarc.csw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
@SpringBootApplication
public class CswApplication {
	public static void main(String[] args) {
		SpringApplication.run(CswApplication.class, args);
	}

}
