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
@RequestMapping("/api")
public class TeacherController {

	@Autowired
	private TeacherService teacherService;

	@PostMapping("/teacher")
	public Teacher createTeacher(@RequestBody final Teacher teacher) {
		return teacherService.createTeacher(teacher);
	}

	@GetMapping("/teacher")
	public List<Teacher> getAllTeacher() {
		return teacherService.getAllTeacher();
	}

	@GetMapping("/teacher/{id}")
	public Teacher getTeacherById(@PathVariable final Long id) {
		return teacherService.getTeacherById(id);
	}

	@PutMapping("update/{id}")
	public String updateTeacher(@PathVariable final Long id, @RequestBody final Teacher teacherDetails) {
		return teacherService.updateTeacher(id, teacherDetails);
	}

	@DeleteMapping("/teacher/{id}")
	public void deleteTeacher(@PathVariable final Long id) {
		teacherService.deleteTeacher(id);
	}

	@GetMapping("/teacher/count/{id}")
	public Long retrieveTeacherCountBySchool(@PathVariable final Long id) {
		return this.teacherService.countTeacherBySchool(id);
	}
}
