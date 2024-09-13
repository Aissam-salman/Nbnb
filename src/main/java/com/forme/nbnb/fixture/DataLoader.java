package com.forme.nbnb.fixture;

import com.forme.nbnb.dto.RegisterDto;
import com.forme.nbnb.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserDetailsServiceImpl userService;

    @Override
    public void run(String... args) throws Exception {

        RegisterDto registerDto = new RegisterDto();

        registerDto.setFirstname("test");
        registerDto.setLastname("test1");
        registerDto.setEmail("test@test.com");
        registerDto.setPassword("secret");
        registerDto.setPicture("test.png");

        userService.createUserClassic(registerDto);
    }
}
