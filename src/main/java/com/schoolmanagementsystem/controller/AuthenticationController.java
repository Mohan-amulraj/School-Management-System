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

	@PostMapping("/user-signup")
	public ResponseEntity<User> signUp(@RequestBody final SignUpRequestDTO signUpRequestDTO) {
		return ResponseEntity.ok(authenticationService.signUp(signUpRequestDTO));
	}

	@PostMapping("/admin-signup")
	public ResponseEntity<User> adminSignUp(@RequestBody final SignUpRequestDTO signUpRequestDTO) {
		return ResponseEntity.ok(authenticationService.adminSignUp(signUpRequestDTO));
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
			return ResponseEntity.ok(authenticationService.signIn(signInRequestDTO));
		} catch (IllegalArgumentException | UsernameNotFoundException ex) {
			return ResponseEntity.badRequest().body(ex.getMessage());
		}
	}

	@PostMapping("/refresh")
	public ResponseEntity<JwtAuthenticationResponseDTO> refresh(@RequestBody final RefreshTokenRequestDTO refreshTokenRequestDTO) {
		return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequestDTO));
	}

	@ExceptionHandler({ IllegalArgumentException.class, UsernameNotFoundException.class })
	public ResponseEntity<String> handleAuthenticationException(Exception e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}

}
