package com.antsrl.jwtexample.dto.request;

import lombok.Data;

@Data
public class AdminChangePasswordRequest {
    private String newPassword;
    private String newPasswordRepeat;

    public boolean passwordsNotMatch() {
        return newPassword == null ||
                !newPassword.equals(newPasswordRepeat);
    }
}
