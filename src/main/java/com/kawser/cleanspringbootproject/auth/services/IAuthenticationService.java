package com.kawser.cleanspringbootproject.auth.services;

import com.kawser.cleanspringbootproject.auth.models.dto.authentication.LoginDTO;
import com.kawser.cleanspringbootproject.auth.models.dto.authentication.LoginResponseDTO;
import com.kawser.cleanspringbootproject.auth.models.dto.authentication.SignupDTO;

public interface IAuthenticationService {

    LoginResponseDTO login(LoginDTO data);

    void signup(SignupDTO data);

    void verifyAccount(String email, String otp);

    void resendVerification(String email);
}
