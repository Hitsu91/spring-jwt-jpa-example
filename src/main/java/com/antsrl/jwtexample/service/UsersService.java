package com.antsrl.jwtexample.service;

import com.antsrl.jwtexample.converter.UserToUserDtoConverter;
import com.antsrl.jwtexample.dto.request.AdminChangePasswordRequest;
import com.antsrl.jwtexample.dto.request.ChangeRoleRequest;
import com.antsrl.jwtexample.dto.request.RegisterRequest;
import com.antsrl.jwtexample.dto.response.UserDto;
import com.antsrl.jwtexample.entity.User;
import com.antsrl.jwtexample.exception.ExistingUserException;
import com.antsrl.jwtexample.exception.PasswordNotMatchException;
import com.antsrl.jwtexample.exception.UserNotFoundException;
import com.antsrl.jwtexample.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsersService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final UserToUserDtoConverter userToUserDtoConverter;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.info("User with username: {} is not found in the system", username);
                    return new UsernameNotFoundException("User not found");
                });
    }

    public List<UserDto> loadAllExceptMe(String username) {
        return userRepository.findAllByUsernameNot(username)
                .stream()
                .map(userToUserDtoConverter).toList();
    }

    public UserDto add(RegisterRequest request) {
        var user = User.builder()
                .username(request.getUsername())
                .password(encoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            log.info("Cannot create user caused by duplicated username: {}", user.getUsername());
            throw new ExistingUserException(user.getUsername());
        }
        log.info("User registered successfully with username: {}", user.getUsername());

        return userToUserDtoConverter.apply(user);
    }

    public UserDto remove(Integer id) {
        var existingUser = existsOrThrow(id);
        userRepository.deleteById(id);
        return userToUserDtoConverter.apply(existingUser);
    }

    public UserDto changePassword(Integer id, AdminChangePasswordRequest request) {
        if (request.passwordsNotMatch()) {
            throw new PasswordNotMatchException();
        }

        var existingUser = existsOrThrow(id);
        existingUser.setPassword(encoder.encode(request.getNewPassword()));
        userRepository.save(existingUser);

        return userToUserDtoConverter.apply(existingUser);
    }

    private User existsOrThrow(Integer id) {
        var existingUser = userRepository.findById(id);
        if (existingUser.isEmpty()) {
            throw new UserNotFoundException(id);
        }
        return existingUser.get();
    }

    public UserDto changeRole(Integer id, ChangeRoleRequest request) {
        var existingUser = existsOrThrow(id);

        existingUser.setRole(request.getNewRole());
        userRepository.save(existingUser);

        return userToUserDtoConverter.apply(existingUser);
    }
}
