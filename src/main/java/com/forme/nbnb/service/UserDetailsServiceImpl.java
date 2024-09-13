package com.forme.nbnb.service;

import com.forme.nbnb.dto.LoginRequest;
import com.forme.nbnb.dto.RegisterDto;
import com.forme.nbnb.entity.user.Role;
import com.forme.nbnb.entity.user.User;
import com.forme.nbnb.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Not found: " + email));
    }

    public User createUserProvider(User user) {
        return userRepository.save(user);
    }

    public User createUserClassic(RegisterDto user) {
        User newUser = User.builder()
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .email(user.getEmail())
                //TODO: hash password
                .password(passwordEncoder.encode(user.getPassword()))
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

    public User authenticate(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
        return userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new UsernameNotFoundException("Not found: " + loginRequest.getEmail()));
    }
}
