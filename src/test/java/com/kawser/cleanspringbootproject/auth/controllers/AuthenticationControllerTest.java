package com.kawser.cleanspringbootproject.auth.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.kawser.cleanspringbootproject.auth.models.dto.authentication.LoginDTO;
import com.kawser.cleanspringbootproject.auth.models.dto.authentication.LoginResponseDTO;
import com.kawser.cleanspringbootproject.auth.models.dto.authentication.SignupDTO;
import com.kawser.cleanspringbootproject.auth.services.impl.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthenticationService authService;

    private Faker faker = new Faker();

    @Test
    public void testLogin() throws Exception {
        String login = faker.lorem().characters(5, 15); // valid login length is between 5 and 15 characters
        String password = faker.lorem().characters(10, 15) + "A1#";

        LoginDTO loginDTO = new LoginDTO(login, password);
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO("token");

        when(authService.login(loginDTO)).thenReturn(loginResponseDTO);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String response = result.getResponse().getContentAsString();
                    assertThat(response).isNotNull();
                    assertThat(response).isNotEmpty();
                    assertThat(response).isEqualTo(objectMapper.writeValueAsString(loginResponseDTO));
                });

        verify(authService, times(1)).login(loginDTO);
    }

    @Test
    public void testSignup() throws Exception {
        String username = faker.lorem().characters(5, 15);
        String password = faker.lorem().characters(10, 15) + "A1#";
        String email = faker.internet().emailAddress();
        String mobilePhone = "01912990007";

        SignupDTO signupDTO = new SignupDTO(username, password, email, mobilePhone);

        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signupDTO)))
                .andExpect(status().isOk());

        verify(authService, times(1)).signup(signupDTO);
    }

    @Test
    public void testVerifyAccount() throws Exception {
        String email = faker.internet().emailAddress();
        String token = faker.lorem().characters(6);

        mockMvc.perform(post("/auth/verify-account")
                        .param("email", email)
                        .param("token", token))
                .andExpect(status().isOk());

        verify(authService, times(1)).verifyAccount(email, token);
    }

    @Test
    public void testResendVerification() throws Exception {
        String email = faker.internet().emailAddress();

        mockMvc.perform(post("/auth/resend-verification")
                        .param("email", email))
                .andExpect(status().isOk());

        verify(authService, times(1)).resendVerification(email);
    }
}