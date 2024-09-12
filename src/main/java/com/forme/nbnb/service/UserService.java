package com.forme.nbnb.service;

import com.forme.nbnb.dto.RegisterDto;
import com.forme.nbnb.entity.user.Role;
import com.forme.nbnb.entity.user.Tenant;
import com.forme.nbnb.entity.user.User;
import com.forme.nbnb.repository.user.UserRepository;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User createUserProvider(User user) {
        return userRepository.save(user);
    }

    public User createUserClassic(RegisterDto user) {
        User newUser = Tenant.builder()
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                //TODO: hash password
                .password(user.getPassword())
                .picture(user.getPicture() != null ? user.getPicture() : null)
                .oauth2Provider(user.getOauth2Provider() != null ? user.getOauth2Provider() : null)
                .oauth2Id(user.getOauth2Id() != null ? user.getOauth2Id() : null)
                .role(Role.USER)
                .build();

        return userRepository.save(newUser);
    }

    public User getByOauthId(String oauthId) {
        return userRepository.findByOauth2Id(oauthId);
    }
}
