package com.forme.nbnb.service;

import com.forme.nbnb.dto.LoginRequest;
import com.forme.nbnb.dto.RegisterDto;
import com.forme.nbnb.entity.Provider;
import com.forme.nbnb.entity.user.Role;
import com.forme.nbnb.entity.user.User;
import com.forme.nbnb.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Not found: " + email));
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
        Authentication authResp = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
        return userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new UsernameNotFoundException("Not found: " + loginRequest.getEmail()));
    }

    public User createUserFromProvider(OAuth2User oauth2User, String email) {
        User newUser = User.builder()
                .firstname(oauth2User.getAttribute("given_name"))
                .lastname(oauth2User.getAttribute("family_name"))
                .email(email)
                .oauth2Id(oauth2User.getAttribute("sub"))
                .oauth2Provider(Provider.google)
                .picture(oauth2User.getAttribute("picture") != null ? oauth2User.getAttribute("picture") : null)
                .role(Role.USER)
                .build();


        return userRepository.save(newUser);
    }

    public boolean updateRole(Long id, String role) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Not found: " + id));

        //FIX: maybe change user to Role entity

        try {
            user.setRole(Role.valueOf(role));
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
