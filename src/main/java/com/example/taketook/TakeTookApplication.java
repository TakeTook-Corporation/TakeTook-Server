package com.example.taketook;

import com.example.taketook.service.UserDetailsServiceImpl;
import com.example.taketook.service.storage.StoragePreferences;
import com.example.taketook.service.storage.StorageService;
import com.example.taketook.utils.AuthEntryPointJwt;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

@SpringBootApplication
@EnableConfigurationProperties(StoragePreferences.class)
public class TakeTookApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(TakeTookApplication.class, args);
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
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            storageService.init();
        };
    }
}
