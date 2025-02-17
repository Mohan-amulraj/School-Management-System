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

import com.schoolmanagementsystem.dto.ResponseDTO;
import com.schoolmanagementsystem.entity.Subject;
import com.schoolmanagementsystem.service.SubjectService;
import com.schoolmanagementsystem.util.Constants;

@RestController
@RequestMapping("/api/subject")
public class SubjectController {

	@Autowired
	private SubjectService subjectService;

	@PostMapping("/create")
	public ResponseDTO createSubject(@RequestBody final Subject subject) {
		return new ResponseDTO(Constants.CREATED,200,"Subject created successfully",this.subjectService.createSubject(subject));
	}

	@GetMapping("/retrieve")
	public ResponseDTO getAllSubject() {
		return new ResponseDTO(Constants.RETRIEVED,200,"Subject retrieved successfully",this.subjectService.getAllSubject());
	}

	@GetMapping("/retrieve/{id}")
	public ResponseDTO getSubjectById(@PathVariable final Long id) {
		return new ResponseDTO(Constants.RETRIEVED,200,"Subject retrieved successfully", this.subjectService.getSubjectById(id));
	}

	@PutMapping("/update/{id}")
	public ResponseDTO updateSubject(@PathVariable final Long id, @RequestBody final Subject subjectDetails) {
		return new ResponseDTO(Constants.UPDATED,200,"Subject updated successfully",this.subjectService.updateSubject(id, subjectDetails));
	}

	@DeleteMapping("/remove/{id}")
	public ResponseDTO deleteSubject(@PathVariable final Long id) {
		this.subjectService.deleteSubject(id);
		return new ResponseDTO(Constants.DELETED,200,"Subject deleted successfully",id);
	}
}
