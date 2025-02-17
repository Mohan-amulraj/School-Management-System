package com.schoolmanagementsystem.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.schoolmanagementsystem.dto.ResponseDTO;
import com.schoolmanagementsystem.entity.Teacher;
import com.schoolmanagementsystem.service.TeacherService;
import com.schoolmanagementsystem.util.Constants;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {

	@Autowired
	private TeacherService teacherService;

	@PostMapping("/create")
	public ResponseDTO createTeacher(@RequestBody final Teacher teacher) {
		return new ResponseDTO(Constants.CREATED,200,"Teacher created successfully",this.teacherService.createTeacher(teacher));
	}

	@GetMapping("/retrieve")
	public ResponseDTO getAllTeacher() {
		return new ResponseDTO(Constants.RETRIEVED,200,"Teacher retrieved successfully",this.teacherService.getAllTeacher());
	}

	@GetMapping("/retrieve/{id}")
	public ResponseDTO getTeacherById(@PathVariable final Long id) {
		return new ResponseDTO(Constants.RETRIEVED,200,"Teacher id retrieved successfully",this.teacherService.getTeacherById(id));
	}

	@PutMapping("update/{id}")
	public ResponseDTO updateTeacher(@PathVariable final Long id, @RequestBody final Teacher teacherDetails) {
		return new ResponseDTO(Constants.UPDATED,200,"Teacher updated successfully",this.teacherService.updateTeacher(id, teacherDetails));
	}

	@DeleteMapping("/remove/{id}")
	public ResponseDTO deleteTeacher(@PathVariable final Long id) {
		return new ResponseDTO(Constants.DELETED,200,"Teacher deleted successfully",id);
	}

	@GetMapping("/count/{id}")
	public ResponseDTO retrieveTeacherCountBySchool(@PathVariable final Long id) {
		return new ResponseDTO(Constants.COUNT,200,"Teacher count successfully",this.teacherService.countTeacherBySchool(id));
	}
	
	@GetMapping("/pagination")
    public ResponseDTO getTeacherPage(@RequestParam final int index, @RequestParam final int size,
            @RequestParam final String field ) {
        return new ResponseDTO(Constants.PAGINATION,200,"Teacher pagination successfully",this.teacherService.getTeacherPage(index,size,field));
    }
}
