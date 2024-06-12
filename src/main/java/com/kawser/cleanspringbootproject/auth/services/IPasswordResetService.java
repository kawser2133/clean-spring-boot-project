package com.kawser.cleanspringbootproject.auth.services;

import com.kawser.cleanspringbootproject.auth.models.dto.password.PasswordResetDTO;
import com.kawser.cleanspringbootproject.auth.models.dto.password.PasswordResetRequestDTO;

public interface IPasswordResetService {

    void requestReset(PasswordResetRequestDTO data);

    void reset(String email, String token, PasswordResetDTO data);
}
