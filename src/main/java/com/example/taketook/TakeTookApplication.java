package com.example.taketook;

import com.example.taketook.service.FileStorageService;
import com.example.taketook.service.UserDetailsServiceImpl;
import com.example.taketook.utils.AuthEntryPointJwt;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

@SpringBootApplication
public class TakeTookApplication implements CommandLineRunner {
    @Resource
    FileStorageService fileStorageService;

    public static void main(String[] args) {
        SpringApplication.run(TakeTookApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            fileStorageService.init();
        } catch (RuntimeException ignored) {
        }
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsServiceImpl userDetailsServiceBean() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public AuthEntryPointJwt authEntryPointJwtBean() {
        return new AuthEntryPointJwt();
    }

    @Bean
    public RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder();
    }
}
