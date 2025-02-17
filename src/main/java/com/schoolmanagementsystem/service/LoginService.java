package com.schoolmanagementsystem.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.schoolmanagementsystem.dto.JwtAuthenticationResponseDTO;
import com.schoolmanagementsystem.dto.RefreshTokenRequestDTO;
import com.schoolmanagementsystem.dto.SignInRequestDTO;
import com.schoolmanagementsystem.dto.SignUpRequestDTO;
import com.schoolmanagementsystem.entity.User;
import com.schoolmanagementsystem.enums.Role;
import com.schoolmanagementsystem.repository.UserRepository;

@Service
public class LoginService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtServiceImpl jwtService;
	private User user;

	public User studentSignUp(final SignUpRequestDTO signUpRequestDTO) {
		final User user = new User();
		user.setEmail(signUpRequestDTO.getEmail());
		user.setPassword(passwordEncoder.encode(signUpRequestDTO.getPassword()));
		user.setRole(Role.STUDENT);
		return this.userRepository.save(user);
	}

	public User teacherSignUp(final SignUpRequestDTO signUpRequestDTO) {
		final User user = new User();
		user.setEmail(signUpRequestDTO.getEmail());
		user.setPassword(passwordEncoder.encode(signUpRequestDTO.getPassword()));
		user.setRole(Role.TEACHER);
		return this.userRepository.save(user);
	}

	public JwtAuthenticationResponseDTO signIn(final SignInRequestDTO signInRequestDTO) {
		 authenticationManager.authenticate(
		            new UsernamePasswordAuthenticationToken(signInRequestDTO.getEmail(), signInRequestDTO.getPassword()));
		
		 if (!StringUtils.hasText(signInRequestDTO.getEmail())) {
	        throw new BadCredentialsException("Email not found");
	    }
	    if (!StringUtils.hasText(signInRequestDTO.getPassword())) {
	        throw new BadCredentialsException("Password not found");
	    }
	        
	    final User user = this.userRepository.findByEmail(signInRequestDTO.getEmail())
	            .orElseThrow(() -> new  BadCredentialsException	("Incorrect email"));
	    if (!passwordEncoder.matches(signInRequestDTO.getPassword(), user.getPassword())) {
	        throw new BadCredentialsException("Incorrect password");
	    }
	 	    
	    final String jwt = jwtService.generateToken(user);
	    final String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

	    final JwtAuthenticationResponseDTO jwtAuthenticationResponse = new JwtAuthenticationResponseDTO();
	    jwtAuthenticationResponse.setToken(jwt);
	    jwtAuthenticationResponse.setRefreshToken(refreshToken);

	    return jwtAuthenticationResponse;
	}

	public User adminSignUp(final SignUpRequestDTO signUpRequestDTO) {
		final User user = new User();
		user.setEmail(signUpRequestDTO.getEmail());
		user.setPassword(passwordEncoder.encode(signUpRequestDTO.getPassword()));
		user.setRole(Role.PRINCIPAL);
		return this.userRepository.save(user);
	}

	public JwtAuthenticationResponseDTO refreshToken(final RefreshTokenRequestDTO refreshTokenRequestDTO) {
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
