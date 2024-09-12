package com.forme.nbnb.fixture;

import com.forme.nbnb.dto.RegisterDto;
import com.forme.nbnb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserService userService;

    @Override
    public void run(String... args) throws Exception {

        RegisterDto registerDto = new RegisterDto();

        registerDto.setFirstname("test");
        registerDto.setLastname("test1");
        registerDto.setEmail("test@test.com");
        registerDto.setPassword("123456");
        registerDto.setPicture("test.png");

        userService.createUserClassic(registerDto);
    }
}
