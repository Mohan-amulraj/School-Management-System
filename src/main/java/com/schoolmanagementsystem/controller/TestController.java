package com.schoolmanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.schoolmanagementsystem.dto.ResponseDTO;
import com.schoolmanagementsystem.entity.Test;
import com.schoolmanagementsystem.service.TestService;
import com.schoolmanagementsystem.util.Constants;

@RestController
@RequestMapping("/api/test")
public class TestController {

	@Autowired
	private TestService testService;

	@PostMapping("/create")
	public ResponseDTO createTest(@RequestBody final Test test) {
		return new ResponseDTO(Constants.CREATED,200,"Test created successfully",this.testService.createTest(test));
	}

	@GetMapping("/retrieve")
	public ResponseDTO getAllTest() {
		return new ResponseDTO(Constants.RETRIEVED,200,"Test retrieved successfully",this.testService.getAllTest());
	}

	@GetMapping("/retrieve/{id}")
	public ResponseDTO getTestById(@PathVariable final Long id) {
		return new ResponseDTO(Constants.RETRIEVED,200,"Test retrieved successfully",this.testService.getTestById(id));
	}

	@PutMapping("/update/{id}")
	public ResponseDTO updateTest(@PathVariable final Long id, @RequestBody final Test testDetails) {
		return new ResponseDTO(Constants.UPDATED,200,"Test updated successfully",this.testService.updateTest(id, testDetails));
	}

	@DeleteMapping("/remove/{id}")
	public ResponseDTO deleteTest(@PathVariable final Long id) {
		 this.testService.deleteTest(id);
		 return new ResponseDTO(Constants.DELETED,200,"Test deleted successfully",id);
	}
}
