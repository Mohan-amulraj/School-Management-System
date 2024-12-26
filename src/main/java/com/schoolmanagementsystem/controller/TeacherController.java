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

import com.schoolmanagementsystem.entity.Teacher;
import com.schoolmanagementsystem.service.TeacherService;

import java.util.List;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {

	@Autowired
	private TeacherService teacherService;

	@PostMapping("/create")
	public Teacher createTeacher(@RequestBody final Teacher teacher) {
		return this.teacherService.createTeacher(teacher);
	}

	@GetMapping("/retrieve")
	public List<Teacher> getAllTeacher() {
		return this.teacherService.getAllTeacher();
	}

	@GetMapping("/retrieve/{id}")
	public Teacher getTeacherById(@PathVariable final Long id) {
		return this.teacherService.getTeacherById(id);
	}

	@PutMapping("update/{id}")
	public String updateTeacher(@PathVariable final Long id, @RequestBody final Teacher teacherDetails) {
		return this.teacherService.updateTeacher(id, teacherDetails);
	}

	@DeleteMapping("/remove/{id}")
	public void deleteTeacher(@PathVariable final Long id) {
		this.teacherService.deleteTeacher(id);
	}

	@GetMapping("/count/{id}")
	public Long retrieveTeacherCountBySchool(@PathVariable final Long id) {
		return this.teacherService.countTeacherBySchool(id);
	}
}
