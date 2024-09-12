package com.forme.nbnb.entity.user;

import com.forme.nbnb.entity.Provider;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "_user")
@SuperBuilder
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User implements Serializable, UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private long id;

    private String firstname;
    private String lastname;

    @Column(unique = true)
    private String email;

    private String password;


    @Enumerated(EnumType.STRING)
    private Role role;

    private String picture;
    private Provider oauth2Provider;
    private String oauth2Id;

    public User() {
    }

    public User(String firstname, String lastname, String email, String password, Role role) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public User(String firstname, String lastname, String email, String password, Role role,
                String picture, Provider oauth2Provider
            , String oauth2Id) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.picture = picture;
        this.password = password;
        this.role = role;
        this.oauth2Provider = oauth2Provider;
        this.oauth2Id = oauth2Id;
    }
}
