package com.schoolmanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.schoolmanagementsystem.dto.JwtAuthenticationResponseDTO;
import com.schoolmanagementsystem.dto.RefreshTokenRequestDTO;
import com.schoolmanagementsystem.dto.ResponseDTO;
import com.schoolmanagementsystem.dto.SignInRequestDTO;
import com.schoolmanagementsystem.dto.SignUpRequestDTO;
import com.schoolmanagementsystem.service.LoginService;
import com.schoolmanagementsystem.util.Constants;

@RestController
@RequestMapping("api/v1/auth")

public class LoginController {

	@Autowired
	private LoginService loginService;

	@PostMapping("/admin")
	public ResponseDTO adminSignUp(@RequestBody final SignUpRequestDTO signUpRequestDTO) {
		return new ResponseDTO(Constants.SIGNUP,200,"Admin signup successfully",this.loginService.adminSignUp(signUpRequestDTO));
	}

	@PostMapping("/student")
	public ResponseDTO studentSignUp(@RequestBody final SignUpRequestDTO signUpRequestDTO) {
		return new ResponseDTO(Constants.SIGNUP,200,"User signup successfully",this.loginService.studentSignUp(signUpRequestDTO));
	}

	@PostMapping("/teacher")
	public ResponseDTO teacherSignUp(@RequestBody final SignUpRequestDTO signUpRequestDTO) {
		return new ResponseDTO(Constants.SIGNUP,200,"User signup successfully",this.loginService.teacherSignUp(signUpRequestDTO));
	}

	@PostMapping("/login")
	public ResponseDTO login(@RequestBody final SignInRequestDTO signInRequestDTO) {
		return new ResponseDTO(Constants.TOKEN,200,"Login successfully",this.loginService.signIn(signInRequestDTO));
	}
	
	@PostMapping("/refresh")
	public ResponseEntity<JwtAuthenticationResponseDTO> refresh(@RequestBody final RefreshTokenRequestDTO refreshTokenRequestDTO) {
		return ResponseEntity.ok(this.loginService.refreshToken(refreshTokenRequestDTO));
	}
}
