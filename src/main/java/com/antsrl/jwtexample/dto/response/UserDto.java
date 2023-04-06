package com.antsrl.jwtexample.dto.response;

import com.antsrl.jwtexample.entity.Role;

public record UserDto(Integer id, String username, Role role, boolean enabled) {
}
