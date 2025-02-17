package com.schoolmanagementsystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.schoolmanagementsystem.dto.ResponseDTO;
import com.schoolmanagementsystem.util.Constants;

@RestController
@RequestMapping("api/v1/admin")
public class AdminController {

	@GetMapping
	public ResponseDTO adminLogin() {
		return new ResponseDTO(Constants.ADMINLOGIN,200,"Welcome to Admin page",null);
	}
	
}
