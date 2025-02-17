package com.schoolmanagementsystem.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schoolmanagementsystem.dto.SignUpRequestDTO;
import com.schoolmanagementsystem.entity.User;
import com.schoolmanagementsystem.enums.Role;
import com.schoolmanagementsystem.service.LoginService;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LoginService loginService;

    @InjectMocks
    private LoginController loginController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testStudentSignUp() throws Exception {
        SignUpRequestDTO signUpRequest = new SignUpRequestDTO();
        signUpRequest.setEmail("test@student.com");
        signUpRequest.setPassword("password123");

        User user = new User();
        user.setEmail("test@student.com");
        user.setRole(Role.STUDENT);

        when(loginService.studentSignUp(any(SignUpRequestDTO.class))).thenReturn(user);

        mockMvc.perform(post("/api/v1/auth/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.email").value(user.getEmail()))
                .andExpect(jsonPath("$.data.role").value(user.getRole().toString()));
        verify(loginService).studentSignUp(any(SignUpRequestDTO.class));
    }

    @Test
    void testTeacherSignUp() throws Exception {
        SignUpRequestDTO signUpRequest = new SignUpRequestDTO();
        signUpRequest.setEmail("test@teacher.com");
        signUpRequest.setPassword("password123");

        User user = new User();
        user.setEmail("test@teacher.com");
        user.setRole(Role.TEACHER);

        when(loginService.teacherSignUp(any(SignUpRequestDTO.class))).thenReturn(user);

        mockMvc.perform(post("/api/v1/auth/teacher")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.email").value(user.getEmail()))
                .andExpect(jsonPath("$.data.role").value(user.getRole().toString()));
        verify(loginService).teacherSignUp(any(SignUpRequestDTO.class));
    }

    @Test
    void testAdminSignUp() throws Exception {
        SignUpRequestDTO signUpRequest = new SignUpRequestDTO();
        signUpRequest.setEmail("test@admin.com");
        signUpRequest.setPassword("password123");

        User user = new User();
        user.setEmail("test@admin.com");
        user.setRole(Role.PRINCIPAL);

        when(loginService.adminSignUp(any(SignUpRequestDTO.class))).thenReturn(user);

        mockMvc.perform(post("/api/v1/auth/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.email").value(user.getEmail()))
                .andExpect(jsonPath("$.data.role").value(user.getRole().toString()));
        verify(loginService).adminSignUp(any(SignUpRequestDTO.class));
    }
}
