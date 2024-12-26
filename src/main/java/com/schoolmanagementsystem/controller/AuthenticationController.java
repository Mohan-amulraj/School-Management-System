package com.schoolmanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.schoolmanagementsystem.dto.JwtAuthenticationResponseDTO;
import com.schoolmanagementsystem.dto.RefreshTokenRequestDTO;
import com.schoolmanagementsystem.dto.SignInRequestDTO;
import com.schoolmanagementsystem.dto.SignUpRequestDTO;
import com.schoolmanagementsystem.entity.User;
import com.schoolmanagementsystem.service.AuthenticationServiceImpl;

@RestController
@RequestMapping("api/v1/auth")

public class AuthenticationController {

	@Autowired
	private AuthenticationServiceImpl authenticationService;

	@PostMapping("/admin")
	public ResponseEntity<User> adminSignUp(@RequestBody final SignUpRequestDTO signUpRequestDTO) {
		return ResponseEntity.ok(this.authenticationService.adminSignUp(signUpRequestDTO));
	}

	@PostMapping("/student")
	public ResponseEntity<User> studentSignUp(@RequestBody final SignUpRequestDTO signUpRequestDTO) {
		return ResponseEntity.ok(this.authenticationService.studentSignUp(signUpRequestDTO));
	}

	@PostMapping("/teacher")
	public ResponseEntity<User> teacherSignUp(@RequestBody final SignUpRequestDTO signUpRequestDTO) {
		return ResponseEntity.ok(this.authenticationService.teacherSignUp(signUpRequestDTO));
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody final SignInRequestDTO signInRequestDTO) {
		if (!StringUtils.hasText(signInRequestDTO.getEmail())) {
			return ResponseEntity.badRequest().body("Enter email id");
		}
		if (!StringUtils.hasText(signInRequestDTO.getPassword())) {
			return ResponseEntity.badRequest().body("Enter Password");
		}
		try {
			return ResponseEntity.ok(this.authenticationService.signIn(signInRequestDTO));
		} catch (IllegalArgumentException | UsernameNotFoundException ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
	}

	@PostMapping("/refresh")
	public ResponseEntity<JwtAuthenticationResponseDTO> refresh(
			@RequestBody final RefreshTokenRequestDTO refreshTokenRequestDTO) {
		return ResponseEntity.ok(this.authenticationService.refreshToken(refreshTokenRequestDTO));
	}

	@ExceptionHandler({ IllegalArgumentException.class, UsernameNotFoundException.class })
	public ResponseEntity<String> handleAuthenticationException(Exception e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}

}
