package com.forme.nbnb.dto;

import com.forme.nbnb.entity.Provider;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {
    private String firstname;
    private String lastname;
    @Email
    private String email;
    private String password;

    @Nullable
    private Provider oauth2Provider;
    @Nullable
    private String oauth2Id;

    @Nullable
    private String picture;
}
