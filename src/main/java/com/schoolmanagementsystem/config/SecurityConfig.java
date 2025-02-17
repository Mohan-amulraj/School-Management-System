package com.schoolmanagementsystem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.schoolmanagementsystem.enums.Role;
import com.schoolmanagementsystem.service.UserServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


	@Autowired
	JwtAuthenticationFilter jwtAuthenticationFilter;

	@Autowired
	UserServiceImpl userService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(request -> request.requestMatchers("/api/v1/auth/**").permitAll()
						.requestMatchers("api/v1/admin/**").hasAnyAuthority(Role.PRINCIPAL.name())
						.requestMatchers("api/v1/user/**").permitAll()
						.requestMatchers("api/school/**").hasAnyAuthority(Role.PRINCIPAL.name())
						.requestMatchers("api/student/**").permitAll()
						.requestMatchers("api/teacher/**").hasAnyAuthority(Role.PRINCIPAL.name(),Role.TEACHER.name())
						.requestMatchers("api/subject/**").permitAll()
						.requestMatchers("api/test/**").permitAll()
						.requestMatchers("api/question/**").hasAnyAuthority(Role.PRINCIPAL.name(),Role.TEACHER.name())
						.requestMatchers("api/question/retrieve/dto/**").hasAnyAuthority(Role.STUDENT.name())
						.requestMatchers("api/student-answer/mark/**").hasAnyAuthority(Role.STUDENT.name())
						.requestMatchers("api/student-answer/**").hasAnyAuthority(Role.PRINCIPAL.name(),Role.TEACHER.name())
						.anyRequest().authenticated())
				.sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}