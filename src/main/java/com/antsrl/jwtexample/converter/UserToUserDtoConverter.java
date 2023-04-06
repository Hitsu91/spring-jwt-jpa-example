package com.antsrl.jwtexample.converter;

import com.antsrl.jwtexample.dto.response.UserDto;
import com.antsrl.jwtexample.entity.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserToUserDtoConverter implements Function<User, UserDto> {
    @Override
    public UserDto apply(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getRole(),
                user.isEnabled()
        );
    }
}
