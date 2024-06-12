package com.kawser.cleanspringbootproject.auth.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import java.time.LocalDateTime;
import java.util.Optional;
import com.kawser.cleanspringbootproject.auth.models.OneTimePassword;
import com.kawser.cleanspringbootproject.auth.models.User;
import com.kawser.cleanspringbootproject.auth.models.dto.authentication.SignupDTO;
import com.kawser.cleanspringbootproject.auth.repositories.UserRepository;
import com.kawser.cleanspringbootproject.auth.services.impl.AuthenticationService;
import com.kawser.cleanspringbootproject.auth.util.EmailUtil;
import com.kawser.cleanspringbootproject.auth.util.OtpUtil;
import com.kawser.cleanspringbootproject.exception.auth.domain.authentication.InvalidOtpException;
import com.kawser.cleanspringbootproject.exception.auth.domain.user.EmailAlreadyExistsException;
import com.kawser.cleanspringbootproject.exception.auth.domain.user.UserAlreadyVerifiedException;
import com.kawser.cleanspringbootproject.exception.auth.domain.user.UserNotFoundException;
import com.kawser.cleanspringbootproject.exception.auth.domain.user.UsernameAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import jakarta.mail.MessagingException;

public class AuthenticationServiceTest {

    @InjectMocks
    private AuthenticationService authenticationService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailUtil emailUtil;

    @Mock
    private OtpUtil otpUtil;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSignupUserLoginAlreadyExists() {
        SignupDTO data = new SignupDTO("login_test", "PasswordA12@", "test@test.com", "01912990007");

        // Mocking the behavior of the methods that will be called in the service method
        when(userRepository.existsByUsername(data.username())).thenReturn(true);

        // Requesting the password reset and expecting an exception
        assertThrows(UsernameAlreadyExistsException.class, () -> authenticationService.signup(data));

        // Verifying if the methods were called
        verify(userRepository, times(1)).existsByUsername(data.username());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testSignupUserEmailAlreadyExists() {
        SignupDTO data = new SignupDTO("login_test", "PasswordA12@", "test@test.com", "01912990007");

        // Mocking the behavior of the methods that will be called in the service method
        when(userRepository.existsByUsername(data.username())).thenReturn(false);
        when(userRepository.existsByEmail(data.email())).thenReturn(true);

        // Requesting the password reset and expecting an exception
        assertThrows(EmailAlreadyExistsException.class, () -> authenticationService.signup(data));

        // Verifying if the methods were called
        verify(userRepository, times(1)).existsByUsername(data.username());
        verify(userRepository, times(1)).existsByEmail(data.email());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testSignupSuccess() throws MessagingException {
        SignupDTO data = new SignupDTO("login_test", "PasswordA12@", "test@test.com", "01912990007");

        // Mocking the behavior of the methods that will be called in the service method
        when(userRepository.existsByUsername(data.username())).thenReturn(false);
        when(userRepository.existsByEmail(data.email())).thenReturn(false);
        doNothing().when(emailUtil).sendOtpEmail(anyString(), anyString());
        when(otpUtil.generateOtp()).thenReturn(new OneTimePassword("123456", LocalDateTime.now()));

        // Requesting the password reset
        authenticationService.signup(data);

        // Verifying if the methods were called
        verify(userRepository, times(1)).existsByUsername(data.username());
        verify(userRepository, times(1)).existsByEmail(data.email());
        verify(emailUtil, times(1)).sendOtpEmail(anyString(), anyString());
        verify(userRepository, times(1)).save(any(User.class));
        verifyNoMoreInteractions(userRepository, emailUtil);
    }

    @Test
    public void testVerifyAccountInvalidOtp() {
        String email = "test@test.com";
        String otp = "123456";

        User user = new User();
        user.setOtp(new OneTimePassword("654321", LocalDateTime.now()));

        // Mocking the behavior of the methods that will be called in the service method
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // Requesting the password reset and expecting an exception
        assertThrows(InvalidOtpException.class, () -> authenticationService.verifyAccount(email, otp));

        // Verifying if the methods were called
        verify(userRepository, times(1)).findByEmail(email);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testVerifyAccountUserNotFound() {
        String email = "test@test.com";
        String otp = "123456";

        // Mocking the behavior of the methods that will be called in the service method
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Requesting the password reset and expecting an exception
        assertThrows(UserNotFoundException.class, () -> authenticationService.verifyAccount(email, otp));

        // Verifying if the methods were called
        verify(userRepository, times(1)).findByEmail(email);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testVerifyAccountSuccess() {
        String email = "test@test.com";
        String token = "123456";
        OneTimePassword otp = new OneTimePassword(token, LocalDateTime.now());

        User user = new User();
        user.setOtp(otp);

        // Mocking the behavior of the methods that will be called in the service method
        when(otpUtil.isValidOtp(otp)).thenReturn(true);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // Requesting the password reset
        authenticationService.verifyAccount(email, token);

        // Verifying if the methods were called
        verify(userRepository, times(1)).findByEmail(email);
        verify(userRepository, times(1)).save(user);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testResendVerificationUserAlreadyVerified() {
        String email = "test@test.com";

        User user = new User();
        user.setEnabled(true);

        // Mocking the behavior of the methods that will be called in the service method
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // Requesting the password reset and expecting an exception
        assertThrows(UserAlreadyVerifiedException.class, () -> authenticationService.resendVerification(email));

        // Verifying if the methods were called
        verify(userRepository, times(1)).findByEmail(email);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testResendVerificationSuccess() throws MessagingException {
        String email = "test@test.com";

        User user = new User();
        user.setEnabled(false);

        // Mocking the behavior of the methods that will be called in the service method
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        doNothing().when(emailUtil).sendOtpEmail(anyString(), anyString());
        when(otpUtil.generateOtp()).thenReturn(new OneTimePassword("123456", LocalDateTime.now()));

        // Requesting the password reset
        authenticationService.resendVerification(email);

        // Verifying if the methods were called
        verify(userRepository, times(1)).findByEmail(email);
        verify(emailUtil, times(1)).sendOtpEmail(anyString(), anyString());
    }

}
