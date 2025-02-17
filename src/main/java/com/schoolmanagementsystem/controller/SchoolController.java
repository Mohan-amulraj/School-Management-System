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
import com.schoolmanagementsystem.entity.School;
import com.schoolmanagementsystem.service.SchoolService;
import com.schoolmanagementsystem.util.Constants;

@RestController
@RequestMapping("api/school")
public class SchoolController {

	@Autowired
	private SchoolService schoolService;

	@PostMapping("/create")
	public ResponseDTO createSchool(@RequestBody final School school) {
		return new ResponseDTO(Constants.CREATED, 200, "School created successfully", this.schoolService.createSchool(school));
	}
	
	@GetMapping("/retrieve")
	public ResponseDTO getAllSchool() {
		return new ResponseDTO(Constants.RETRIEVED,200,"School retrieved successfully",this.schoolService.getAllSchool());
	}
	@GetMapping("/retrieve/{id}")
	public ResponseDTO getSchoolById(@PathVariable final Long id) {
		return new ResponseDTO(Constants.RETRIEVED,200,"School id retrieved successfully",this.schoolService.getSchoolById(id));
	}
 
	@PutMapping("/update/{id}")
	public ResponseDTO updateStudent(@PathVariable final Long id, @RequestBody final School schoolDetails) {
		return new ResponseDTO(Constants.UPDATED,200,"School updated successfully",this.schoolService.updateSchool(id, schoolDetails));
	}

	@DeleteMapping("/remove/{id}")
	public ResponseDTO deleteSchool(@PathVariable final Long id) {
		this.schoolService.deleteSchool(id);
		return new ResponseDTO(Constants.DELETED,200,"School deleted successfully",id);
	}

	@GetMapping("/count")
	public ResponseDTO getCountSchool() {
		return new ResponseDTO(Constants.COUNT,200,"School counted successfully",this.schoolService.countSchool());
	}

	@GetMapping("/pagination")
	public ResponseDTO getSchoolPage(@RequestParam final int index, @RequestParam final int size,
									 @RequestParam final String field) {
		return new ResponseDTO(Constants.PAGINATION,200,"School pagination successfully",this.schoolService.getSchoolPage(index, size, field));
	}
}