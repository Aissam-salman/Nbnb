package com.forme.nbnb.repository.user;

import com.forme.nbnb.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByEmail(String email);

    public User findByOauth2Id(String oauth2Id);
}