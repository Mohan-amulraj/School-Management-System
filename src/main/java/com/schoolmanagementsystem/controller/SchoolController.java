package com.schoolmanagementsystem.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.schoolmanagementsystem.entity.School;
import com.schoolmanagementsystem.service.SchoolService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SchoolController {

	@Autowired
	private SchoolService schoolService;

	@PostMapping("/school")
	public School createSchool(@RequestBody final School school) {
		return this.schoolService.createSchool(school);
	}

	@GetMapping("/school")
	public List<School> getAllSchool() {
		return schoolService.getAllSchool();
	}

	@GetMapping("/school/{id}")
	public School getSchoolById(@PathVariable final Long id) {
		return this.schoolService.getSchoolById(id);
	}

	@PutMapping("/school/{id}")
	public String updateStudent(@PathVariable final Long id, @RequestBody final School schoolDetails) {
		return this.schoolService.updateSchool(id, schoolDetails);
	}

	@DeleteMapping("/school/{id}")
	public void deleteSchool(@PathVariable final Long id) {
		this.schoolService.deleteSchool(id);
	}

	@GetMapping("/school/count")
	public long getCountSchool() {
		return schoolService.countSchool();
	}

	@GetMapping("/school/pagination")
	public Page<School> getSchoolpage(@RequestParam int pageIndex, @RequestParam int pageSize,
			@RequestParam String field) {
		return schoolService.getSchoolPage(pageIndex, pageSize, field);
	}
}