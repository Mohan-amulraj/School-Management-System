package com.schoolmanagementsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.schoolmanagementsystem.entity.Test;
import com.schoolmanagementsystem.service.TestService;

@RestController
@RequestMapping("/api")
public class TestController {

	@Autowired
	private TestService testService;

	@PostMapping("/test")
	public Test createTest(@RequestBody final Test test) {
		return this.testService.createTest(test);
	}

	@GetMapping("/test")
	public List<Test> getAllTest() {
		return this.testService.getAllTest();
	}

	@GetMapping("/test/{id}")
	public Test getTestById(@PathVariable final Long id) {
		return this.testService.getTestById(id);
	}

	@PutMapping("/test/{id}")
	public String updateTest(@PathVariable final Long id, @RequestBody final Test testDetails) {
		return this.testService.updateTest(id, testDetails);
	}

	@DeleteMapping("/test/{id}")
	public void deleteTest(@PathVariable final Long id) {
		this.testService.deleteTest(id);
	}
}
