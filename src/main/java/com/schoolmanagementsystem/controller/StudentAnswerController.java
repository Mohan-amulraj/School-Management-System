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
@RequestMapping("/api")
public class StudentAnswerController {

	@Autowired
	private StudentAnswerService studentAnswerService;
	
	@PostMapping("/student-answer")
	public StudentAnswer createStudentAnswer(@RequestBody final StudentAnswer studentanswer) {
		return this.studentAnswerService.createStudentAnswer(studentanswer);
	}
	
	@GetMapping("/student-answer")
	public List<StudentAnswer> getAllStudentAnswer(){
		return this.studentAnswerService.getAllStudentAnswer();
	}
	
	@GetMapping("student-answer/{id}")
	public StudentAnswer getStudentAnswerById(@PathVariable final Long id) {
		return this.studentAnswerService.getStudentAnswerById(id);
	}
	
	@PutMapping("/student-answer/{id}")
	public String updateStudentAnswer(@PathVariable final Long id,@RequestBody final StudentAnswer studentAnswerDetails ) {
		return this.studentAnswerService.updateStudentAnswer(id,studentAnswerDetails);
	}
	
	@DeleteMapping("/student-answer/{id}")
	public void deleteStudentAnswer(@PathVariable final Long id) {
		this.studentAnswerService.deleteStudentAnswer(id);
	}
	
	@GetMapping("/students/marks")
	public Map<Long,String> studentMarks(){
		return this.studentAnswerService.allStudentMarks();
	}
	
	@GetMapping("/student/mark/{id}")
	public String studentMarks(@PathVariable final Long id) {
		return this.studentAnswerService.studentMarks(id, getAllStudentAnswer());
	}
}
