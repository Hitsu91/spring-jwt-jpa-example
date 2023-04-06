package com.antsrl.jwtexample.service;

import com.antsrl.jwtexample.converter.UserToUserDtoConverter;
import com.antsrl.jwtexample.dto.request.AuthenticationRequest;
import com.antsrl.jwtexample.dto.request.ChangePasswordRequest;
import com.antsrl.jwtexample.dto.response.AuthenticationResponse;
import com.antsrl.jwtexample.dto.response.UserDto;
import com.antsrl.jwtexample.entity.Role;
import com.antsrl.jwtexample.entity.User;
import com.antsrl.jwtexample.exception.ExistingUserException;
import com.antsrl.jwtexample.exception.OldPasswordNotMatchException;
import com.antsrl.jwtexample.exception.UnAuthorizedException;
import com.antsrl.jwtexample.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private final UserToUserDtoConverter userToUserDtoConverter;


    public AuthenticationResponse login(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (BadCredentialsException ex) {
            throw new UnAuthorizedException();
        }

        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(UnAuthorizedException::new);

        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);


        log.info("User: {} logged successfully", user.getUsername());
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse register(AuthenticationRequest request) {
        var existing = userRepository.findByUsername(request.getUsername());

        if (existing.isPresent()) {
            throw new ExistingUserException(request.getUsername());
        }

        var newUser = User.builder()
                .role(Role.USER)
                .username(request.getUsername())
                .password(encoder.encode(request.getPassword()))
                .build();

        var jwtToken = jwtService.generateToken(newUser);

        return new AuthenticationResponse(jwtToken);
    }

    public UserDto whoAmI(String username) {
        return userRepository
                .findByUsername(username)
                .map(userToUserDtoConverter)
                .orElseThrow();
    }

    public AuthenticationResponse changePassword(ChangePasswordRequest request, String username) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            username,
                            request.getOldPassword()
                    )
            );
        } catch (BadCredentialsException ex) {
            log.info("User: {} failed to update password caused by mismatching old password", username);
            throw new OldPasswordNotMatchException();
        }

        var user = userRepository.findByUsername(username).orElseThrow();
        user.setPassword(encoder.encode(request.getNewPassword()));
        userRepository.save(user);

        var token = jwtService.generateToken(user);
        log.info("User {} changed password successfully", username);
        return new AuthenticationResponse(token);
    }
}
