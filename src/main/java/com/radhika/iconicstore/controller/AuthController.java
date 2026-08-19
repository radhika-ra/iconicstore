package com.radhika.iconicstore.controller;

import com.radhika.iconicstore.dto.request.LoginRequest;
import com.radhika.iconicstore.dto.response.LoginResponse;
import com.radhika.iconicstore.entity.User;
import com.radhika.iconicstore.service.AuthService;
import com.radhika.iconicstore.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserDetailsServiceImpl userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/addUser")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        userDetailsService.addUser(user);
        return ResponseEntity.ok(user);
    }
}
