package com.antsrl.jwtexample.controller;

import com.antsrl.jwtexample.dto.request.AuthenticationRequest;
import com.antsrl.jwtexample.dto.request.ChangePasswordRequest;
import com.antsrl.jwtexample.dto.response.AuthenticationResponse;
import com.antsrl.jwtexample.dto.response.UserDto;
import com.antsrl.jwtexample.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {
    private final AuthenticationService service;


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        log.info("User tried to login with username: {}", request.getUsername());
        return ResponseEntity.ok(service.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest request) {
        log.info("User tried to login with username: {}", request.getUsername());
        return ResponseEntity.ok(service.register(request));
    }


    @PutMapping("/change-password")
    public ResponseEntity<AuthenticationResponse> changePassword(@RequestBody ChangePasswordRequest request, Principal user) {
        log.info("User with username {} requested password change", user.getName());
        return ResponseEntity.ok(service.changePassword(request, user.getName()));
    }

    @GetMapping("/who-am-i")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDto> whoAmI(Principal principal) {
        log.info("User with username {} requested Who-am-i", principal.getName());
        return ResponseEntity.ok(service.whoAmI(principal.getName()));
    }

}
