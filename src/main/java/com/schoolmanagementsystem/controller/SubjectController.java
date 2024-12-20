package com.schoolmanagementsystem.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.schoolmanagementsystem.entity.Subject;
import com.schoolmanagementsystem.service.SubjectService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SubjectController {

	@Autowired
	private SubjectService subjectService;

	@PostMapping("/subject")
	public Subject createSubject(@RequestBody final Subject subject) {
		return subjectService.createSubject(subject);
	}

	@GetMapping("/subject")
	public List<Subject> getAllSubject() {
		return subjectService.getAllSubject();
	}

	@GetMapping("/subject/{id}")
	public Subject getSubjectById(@PathVariable final Long id) {
		return subjectService.getSubjectById(id);
	}

	@PutMapping("/subject/{id}")
	public String updateSubject(@PathVariable final Long id, @RequestBody final Subject subjectDetails) {
		return subjectService.updateSubject(id, subjectDetails);
	}

	@DeleteMapping("/subject/{id}")
	public void deleteSubject(@PathVariable final Long id) {
		subjectService.deleteSubject(id);
	}
}
