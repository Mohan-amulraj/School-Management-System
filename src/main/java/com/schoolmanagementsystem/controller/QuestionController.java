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
import com.schoolmanagementsystem.dto.QuestionDTO;
import com.schoolmanagementsystem.dto.ResponseDTO;
import com.schoolmanagementsystem.entity.Question;
import com.schoolmanagementsystem.service.QuestionService;
import com.schoolmanagementsystem.util.Constants;

import java.util.List;

@RestController
@RequestMapping("/api/question")
public class QuestionController {

	@Autowired
	private QuestionService questionService;

	@PostMapping("/create")
	public ResponseDTO createQuestion(@RequestBody final Question question) {
		return new ResponseDTO(Constants.CREATED,200,"Question created successfully",this.questionService.createQuestion(question));
	}

	@GetMapping("/retrieve")
	public ResponseDTO getAllQuestion() {
		return new ResponseDTO(Constants.RETRIEVED,200,"Question retrieved successfully",this.questionService.getAllQuestion());
	}

	@GetMapping("/retrieve/{id}")
	public ResponseDTO getQuestionById(@PathVariable final Long id) {
		return new ResponseDTO(Constants.RETRIEVED,200,"Question id retrieved successfully",this.questionService.getQuestionById(id));
	}

	@PutMapping("/update/{id}")
	public ResponseDTO updateQuestion(@PathVariable final Long id, @RequestBody final Question questionDetails) {
		return new ResponseDTO(Constants.UPDATED,200,"Question updated successfully",this.questionService.updateQuestion(id, questionDetails));
	}

	@DeleteMapping("/remove/{id}")
	public ResponseDTO deleteQuestion(@PathVariable final Long id) {
		this.questionService.deleteQuestion(id);
		return new ResponseDTO(Constants.DELETED,200,"Question deleted successfully",id);
	}

	@GetMapping("/retrieve/dto")
	public List<QuestionDTO> getAllQuestionDTO() {
		return this.questionService.retrieveQuestions();
	}

	@GetMapping("/retrieve/dto/{id}")
	public QuestionDTO getQuestionDTO(@PathVariable final Long id) {
		return this.questionService.getQuestionDTO(id);
	}
}
