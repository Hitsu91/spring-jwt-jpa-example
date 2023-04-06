package com.antsrl.jwtexample.dto.request;

import com.antsrl.jwtexample.entity.Role;
import lombok.Data;

@Data
public class ChangeRoleRequest {
    private Role newRole;
}
