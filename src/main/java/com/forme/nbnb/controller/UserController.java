package com.forme.nbnb.controller;

import com.forme.nbnb.dto.LoginRequest;
import com.forme.nbnb.dto.LoginResponse;
import com.forme.nbnb.dto.RegisterDto;
import com.forme.nbnb.dto.UserDto;
import com.forme.nbnb.entity.user.User;
import com.forme.nbnb.mapper.MapperDTO;
import com.forme.nbnb.security.token.JwtService;
import com.forme.nbnb.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {
    private final UserDetailsServiceImpl userService;
    private final JwtService jwtService;


    @GetMapping("/user-info")
    @ResponseBody
    public ResponseEntity<?> userInfo(@AuthenticationPrincipal Object principal) {
        if (principal instanceof OAuth2User oauth2User) {

            String email = oauth2User.getAttribute("email");
            User user = userService.getByEmail(email);

            if (user != null) {
                return ResponseEntity.ok(user);
            }

            User newUser = userService.createUserFromProvider(oauth2User, email);

            return ResponseEntity.ok(newUser);

        } else {

            UserDto userDto = MapperDTO.currentUserToUserDto();

            if (userDto == null) {
                return ResponseEntity.badRequest().body("Error creating user");
            }

            return ResponseEntity.ok(userDto);

        }
    }

    @PostMapping("/signup")
    @ResponseBody
    public ResponseEntity<?> signup(@RequestBody RegisterDto req) {

        User user = userService.getByEmail(req.getEmail());
        if (user == null) {
            User newUser = userService.createUserClassic(req);
            if (newUser != null) return ResponseEntity.ok(newUser);

        }
        return ResponseEntity.badRequest().body("User already exists");
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        User authenticatedUser = userService.authenticate(loginRequest);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getJwtExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/role/{roleName}")
    @ResponseBody
    public ResponseEntity<?> role(@PathVariable String roleName) {
        UserDto userDto = MapperDTO.currentUserToUserDto();

        assert userDto != null;
        boolean update = userService.updateRole(userDto.getId(), roleName);

        if (update) {
            return ResponseEntity.ok(userDto);
        }
        return ResponseEntity.badRequest().body("Error updating role");
    }

}
