package com.schoolmanagementsystem.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.schoolmanagementsystem.entity.Test;
import com.schoolmanagementsystem.exception.UserNotFoundException;
import com.schoolmanagementsystem.repository.TestRepository;

@Service
public class TestService {

	@Autowired
	private TestRepository testRepository;

	public Test createTest(final Test test) {
		return this.testRepository.save(test);
	}

	public List<Test> getAllTest() {
		return this.testRepository.findAll();
	}

	public Test getTestById(final Long id) {
		return this.testRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("Test not found for this id : " + id));
	}

	public String updateTest(final Long id, final Test testDetails) {
		Test test = testRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("Test not found for this id : " + id));
		testDetails.setId(id);
		this.testRepository.save(test);
		return "Test id:" + id + " " + "successfully updated";
	}

	public String deleteTest(final Long id) {
		Test test = testRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("Test not found for this id : " + id));
		this.testRepository.delete(test);
		return "Test id:" + id + " " + "successfully deleted";
	}
}