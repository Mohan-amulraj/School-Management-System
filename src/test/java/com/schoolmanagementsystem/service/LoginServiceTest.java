package com.schoolmanagementsystem.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.schoolmanagementsystem.dto.SignUpRequestDTO;
import com.schoolmanagementsystem.entity.User;
import com.schoolmanagementsystem.enums.Role;
import com.schoolmanagementsystem.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private LoginService loginService;

    private SignUpRequestDTO signUpRequest;
    private SignUpRequestDTO signUpRequest1;
    private SignUpRequestDTO signUpRequest2;

    @BeforeEach
    void setUp() {
        signUpRequest = new SignUpRequestDTO();
        signUpRequest.setEmail("test@student.com");
        signUpRequest.setPassword("password123");

        signUpRequest1 = new SignUpRequestDTO();
        signUpRequest1.setEmail("test@teacher.com");
        signUpRequest1.setPassword("password123");

        signUpRequest2 = new SignUpRequestDTO();
        signUpRequest2.setEmail("test@admin.com");
        signUpRequest2.setPassword("password123");
    }

    @Test
    void testStudentSignUp() {
        User user = new User();
        user.setEmail("test@student.com");
        user.setPassword("encodedPassword");
        user.setRole(Role.STUDENT);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        User savedUser = loginService.studentSignUp(signUpRequest);
        assertNotNull(savedUser);
        assertEquals(signUpRequest.getEmail(), savedUser.getEmail());
        assertEquals(Role.STUDENT, savedUser.getRole());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testTeacherSignUp() {
        User user1 = new User();
        user1.setEmail("test@teacher.com");
        user1.setPassword("encodedPassword");
        user1.setRole(Role.TEACHER);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user1);
        User savedUser = loginService.teacherSignUp(signUpRequest1);
        assertNotNull(savedUser);
        assertEquals(signUpRequest1.getEmail(), savedUser.getEmail());
        assertEquals(Role.TEACHER, savedUser.getRole());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testAdminSignUp() {
        User user2 = new User();
        user2.setEmail("test@admin.com");
        user2.setPassword("encodedPassword");
        user2.setRole(Role.PRINCIPAL);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user2);
        User savedUser = loginService.teacherSignUp(signUpRequest2);
        assertNotNull(savedUser);
        assertEquals(signUpRequest2.getEmail(), savedUser.getEmail());
        assertEquals(Role.PRINCIPAL, savedUser.getRole());
        verify(userRepository, times(1)).save(any(User.class));
    }
    @AfterAll
    static void toEndSignUp(){
        System.out.println("SignUp ServiceTestcase execution finished");
    }
}
