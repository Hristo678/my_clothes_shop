package com.example.clothes_shop.config;

import com.cloudinary.Cloudinary;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import javax.transaction.Transactional;
import java.util.Map;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableScheduling
@Configuration
@Transactional
public class ApplicationConficuration {

    private CloudinaryConfig config;

    public ApplicationConficuration(CloudinaryConfig config) {
        this.config = config;
    }

    @Bean
    public Cloudinary cloudinary(){
        return new Cloudinary(Map.of(
                "cloud_name", config.getCloudName(),
                "api_key", config.getApiKey(),
                "api_secret", config.getApiSecret()
        ));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Pbkdf2PasswordEncoder();
    }
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
