package com.kenBank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
//@EnableJpaRepositories("com.kenBank.Repository")
//@EntityScan("com.kenBank.pojo")
//@EnableWebSecurity  // Not needed since we are using the spring boot required if we are using spring without springboot
//@ComponentScan("com.kenBank.Controller,com.kenBank.Configuration") // Since we have the controller class out of the parent package of the main method
public class SpringSecuritybasicBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecuritybasicBankApplication.class, args);
	}

//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/notices").allowedOrigins("*");
//			}
//		};
//	}

}
