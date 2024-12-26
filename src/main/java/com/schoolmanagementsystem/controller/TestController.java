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
@RequestMapping("/api/test")
public class TestController {

	@Autowired
	private TestService testService;

	@PostMapping("/create")
	public Test createTest(@RequestBody final Test test) {
		return this.testService.createTest(test);
	}

	@GetMapping("/retrieve")
	public List<Test> getAllTest() {
		return this.testService.getAllTest();
	}

	@GetMapping("/retrieve/{id}")
	public Test getTestById(@PathVariable final Long id) {
		return this.testService.getTestById(id);
	}

	@PutMapping("/update/{id}")
	public String updateTest(@PathVariable final Long id, @RequestBody final Test testDetails) {
		return this.testService.updateTest(id, testDetails);
	}

	@DeleteMapping("/remove/{id}")
	public void deleteTest(@PathVariable final Long id) {
		this.testService.deleteTest(id);
	}
}
