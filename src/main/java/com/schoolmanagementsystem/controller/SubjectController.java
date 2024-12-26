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
@RequestMapping("/api/subject")
public class SubjectController {

	@Autowired
	private SubjectService subjectService;

	@PostMapping("/create")
	public Subject createSubject(@RequestBody final Subject subject) {
		return this.subjectService.createSubject(subject);
	}

	@GetMapping("/retrieve")
	public List<Subject> getAllSubject() {
		return this.subjectService.getAllSubject();
	}

	@GetMapping("/retrieve/{id}")
	public Subject getSubjectById(@PathVariable final Long id) {
		return this.subjectService.getSubjectById(id);
	}

	@PutMapping("/update/{id}")
	public String updateSubject(@PathVariable final Long id, @RequestBody final Subject subjectDetails) {
		return this.subjectService.updateSubject(id, subjectDetails);
	}

	@DeleteMapping("/remove/{id}")
	public void deleteSubject(@PathVariable final Long id) {
		this.subjectService.deleteSubject(id);
	}
}
