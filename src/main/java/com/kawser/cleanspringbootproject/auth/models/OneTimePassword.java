package com.kawser.cleanspringbootproject.auth.models;

import jakarta.persistence.Embeddable;

import java.time.LocalDateTime;

@Embeddable
public record OneTimePassword(String otp, LocalDateTime otpGenerationTime) {

    public OneTimePassword {
        if (otp == null || otpGenerationTime == null) {
            throw new IllegalArgumentException("OTP and generation time must not be null");
        }
    }
}
    