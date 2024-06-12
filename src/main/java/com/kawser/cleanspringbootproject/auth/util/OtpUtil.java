package com.kawser.cleanspringbootproject.auth.util;

import com.kawser.cleanspringbootproject.auth.models.OneTimePassword;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * Utility class to generate and validate OTPs
 */
@Component
public class OtpUtil {

    private final int MINUTES_TO_EXPIRE = 5;

    /**
     * Generates a 6-digit OTP
     * @return OneTimePassword object containing the OTP and the generation time
     */
    public OneTimePassword generateOtp() {
        Random random = new Random();
        String otp = String.format("%06d", random.nextInt(1000000));
        return new OneTimePassword(otp, LocalDateTime.now());
    }

    /**
     * Validates if the OTP is still valid
     * @param otp OTP to validate
     * @return true if the OTP is still valid, false otherwise
     */
    public boolean isValidOtp(OneTimePassword otp) {
        return otp.otpGenerationTime().plusMinutes(MINUTES_TO_EXPIRE).isAfter(LocalDateTime.now());
    }
}
