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

import com.schoolmanagementsystem.entity.StudentAnswer;
import com.schoolmanagementsystem.service.StudentAnswerService;

@RestController
@RequestMapping("/api/student-answer")
public class StudentAnswerController {

	@Autowired
	private StudentAnswerService studentAnswerService;
	
	@PostMapping("/create")
	public StudentAnswer createStudentAnswer(@RequestBody final StudentAnswer studentanswer) {
		return this.studentAnswerService.createStudentAnswer(studentanswer);
	}
	
	@GetMapping("/retrieve")
	public List<StudentAnswer> getAllStudentAnswer(){
		return this.studentAnswerService.getAllStudentAnswer();
	}
	
	@GetMapping("retrieve/{id}")
	public StudentAnswer getStudentAnswerById(@PathVariable final Long id) {
		return this.studentAnswerService.getStudentAnswerById(id);
	}
	
	@PutMapping("/update/{id}")
	public String updateStudentAnswer(@PathVariable final Long id,@RequestBody final StudentAnswer studentAnswerDetails ) {
		return this.studentAnswerService.updateStudentAnswer(id,studentAnswerDetails);
	}
	
	@DeleteMapping("/remove/{id}")
	public void deleteStudentAnswer(@PathVariable final Long id) {
		this.studentAnswerService.deleteStudentAnswer(id);
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
