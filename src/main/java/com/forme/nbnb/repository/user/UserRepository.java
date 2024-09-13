package com.forme.nbnb.repository.user;

import com.forme.nbnb.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    public User findByOauth2Id(String oauth2Id);
}