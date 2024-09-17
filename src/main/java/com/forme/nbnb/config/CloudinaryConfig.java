package com.forme.nbnb.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {
    @Value("${cloudinary.url}")
    private String cloudUrl;

    @Bean
    public Cloudinary cloudinary() {
        Cloudinary cloudConf = new Cloudinary(cloudUrl);
        cloudConf.config.secure = true;
        
        return cloudConf;
    }
}
