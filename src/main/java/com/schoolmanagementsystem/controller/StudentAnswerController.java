package com.schoolmanagementsystem.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.schoolmanagementsystem.dto.ResponseDTO;
import com.schoolmanagementsystem.entity.StudentAnswer;
import com.schoolmanagementsystem.service.StudentAnswerService;
import com.schoolmanagementsystem.util.Constants;

@RestController
@RequestMapping("/api/student-answer")
public class StudentAnswerController {

	@Autowired
	private StudentAnswerService studentAnswerService;
	
	@PostMapping("/create")
	public ResponseDTO createStudentAnswer(@RequestBody final StudentAnswer studentanswer) {
		return new ResponseDTO(Constants.CREATED,200,"Answer created successfully",this.studentAnswerService.createStudentAnswer(studentanswer));
	}
	
	@GetMapping("/retrieve")
	public List<StudentAnswer> getAllStudentAnswer(){
		return this.studentAnswerService.getAllStudentAnswer();
	}
	
	@GetMapping("retrieve/{id}")
	public ResponseDTO getStudentAnswerById(@PathVariable final Long id) {
		return new ResponseDTO(Constants.RETRIEVED,200,"Answer retrieved successfully",this.studentAnswerService.getStudentAnswerById(id));
	}
	
	@PutMapping("/update/{id}")
	public ResponseDTO updateStudentAnswer(@PathVariable final Long id,@RequestBody final StudentAnswer studentAnswerDetails ) {
		return new ResponseDTO(Constants.UPDATED,200,"Answer updated successfully",this.studentAnswerService.updateStudentAnswer(id,studentAnswerDetails));
	}
	
	@DeleteMapping("/remove/{id}")
	public ResponseDTO deleteStudentAnswer(@PathVariable final Long id) {
		 this.studentAnswerService.deleteStudentAnswer(id);
		 return new ResponseDTO(Constants.DELETED,200,"Answer deleted successfully",id);
	}
	
	@GetMapping("/mark")
	public Map<Long,String> studentMarks(){
		return this.studentAnswerService.allStudentMarks();
	}
	
	@GetMapping("/mark/{id}")
	public String studentMark(@PathVariable final Long id) {
		return this.studentAnswerService.studentMark(id, getAllStudentAnswer());
	}
}
