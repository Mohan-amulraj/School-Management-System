package com.schoolmanagementsystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.schoolmanagementsystem.dto.ResponseDTO;
import com.schoolmanagementsystem.util.Constants;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

	@GetMapping
	public ResponseDTO userLogin() {
		return new ResponseDTO(Constants.USERLOGIN,200,"Welcome to user page",null);
	}

}
