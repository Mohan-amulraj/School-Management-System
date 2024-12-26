package com.schoolmanagementsystem.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.schoolmanagementsystem.dto.JwtAuthenticationResponseDTO;
import com.schoolmanagementsystem.dto.RefreshTokenRequestDTO;
import com.schoolmanagementsystem.dto.SignInRequestDTO;
import com.schoolmanagementsystem.dto.SignUpRequestDTO;
import com.schoolmanagementsystem.entity.User;
import com.schoolmanagementsystem.enums.Role;
import com.schoolmanagementsystem.repository.UserRepository;

@Service
public class AuthenticationServiceImpl {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtServiceImpl jwtService;

	public User studentSignUp(final SignUpRequestDTO signUpRequestDTO) {
		User user = new User();
		user.setEmail(signUpRequestDTO.getEmail());
		user.setPassword(passwordEncoder.encode(signUpRequestDTO.getPassword()));
		user.setRole(Role.STUDENT);
		return this.userRepository.save(user);
	}

	public User teacherSignUp(final SignUpRequestDTO signUpRequestDTO) {
		User user = new User();
		user.setEmail(signUpRequestDTO.getEmail());
		user.setPassword(passwordEncoder.encode(signUpRequestDTO.getPassword()));
		user.setRole(Role.TEACHER);
		return this.userRepository.save(user);
	}

	public JwtAuthenticationResponseDTO signIn(final SignInRequestDTO signInRequestDTO) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequestDTO.getEmail(),
					signInRequestDTO.getPassword()));
		} catch (BadCredentialsException e) {
			throw new IllegalArgumentException("Incorrect email or password");
		}

		User user = userRepository.findByEmail(signInRequestDTO.getEmail()).orElseThrow(
				() -> new UsernameNotFoundException("user not found with email: " + signInRequestDTO.getEmail()));

		String jwt = jwtService.generateToken(user);
		String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

		JwtAuthenticationResponseDTO jwtAuthenticationResponse = new JwtAuthenticationResponseDTO();
		jwtAuthenticationResponse.setToken(jwt);
		jwtAuthenticationResponse.setRefreshToken(refreshToken);

		return jwtAuthenticationResponse;
	}

	public User adminSignUp(SignUpRequestDTO signUpRequestDTO) {
		User user = new User();
		user.setEmail(signUpRequestDTO.getEmail());
		user.setPassword(passwordEncoder.encode(signUpRequestDTO.getPassword()));
		user.setRole(Role.PRINCIPAL);
		return this.userRepository.save(user);
	}

	public JwtAuthenticationResponseDTO refreshToken(RefreshTokenRequestDTO refreshTokenRequestDTO) {
		String userEmail = jwtService.extractUserName(refreshTokenRequestDTO.getToken());

		User user = userRepository.findByEmail(userEmail).orElseThrow();

		if (jwtService.isTokenValid(refreshTokenRequestDTO.getToken(), user)) {
			var jwt = jwtService.generateToken(user);

			JwtAuthenticationResponseDTO jwtAuthenticationResponse = new JwtAuthenticationResponseDTO();
			jwtAuthenticationResponse.setToken(jwt);
			jwtAuthenticationResponse.setRefreshToken(refreshTokenRequestDTO.getToken());

			return jwtAuthenticationResponse;
		}
		return null;
	}
}
