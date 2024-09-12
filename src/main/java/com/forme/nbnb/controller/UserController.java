package com.forme.nbnb.controller;

import com.forme.nbnb.dto.RegisterDto;
import com.forme.nbnb.entity.Provider;
import com.forme.nbnb.entity.user.Role;
import com.forme.nbnb.entity.user.Tenant;
import com.forme.nbnb.entity.user.User;
import com.forme.nbnb.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/user-info")
    @ResponseBody
    public ResponseEntity<?> userInfo(@AuthenticationPrincipal Object principal) {

        // with google
        if (principal instanceof OAuth2User oauth2User) {

            String email = oauth2User.getAttribute("email");
            User user = userService.getByEmail(email);
            if (user != null) {
                return ResponseEntity.ok(user);
            }


            String firstname = oauth2User.getAttribute("given_name");
            String lastname = oauth2User.getAttribute("family_name");
            String id = oauth2User.getAttribute("sub");
            String picture = oauth2User.getAttribute("picture") != null ? oauth2User.getAttribute("picture") : null;


            User newUser = Tenant.builder()
                    .firstname(firstname)
                    .lastname(lastname)
                    .email(email)
                    .oauth2Id(id)
                    .oauth2Provider(Provider.google)
                    .picture(picture)
                    .role(Role.USER)
                    .build();

            userService.createUserProvider(newUser);

            return ResponseEntity.ok(newUser);

        } else if (principal instanceof UserDetails userDetails) {
            User user = userService.getByEmail(userDetails.getUsername());
            if (user != null) {
                return ResponseEntity.ok(user);
            }

            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/signup")
    @ResponseBody
    public ResponseEntity<?> signup(@RequestBody RegisterDto req) {

        User user = userService.getByEmail(req.getEmail());
        if (user == null) {
            User newUser = userService.createUserClassic(req);
            if (newUser != null) return ResponseEntity.ok().body(newUser);

        }
        return ResponseEntity.badRequest().body("User already exists");
    }

}
