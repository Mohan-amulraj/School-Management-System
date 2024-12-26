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
import com.schoolmanagementsystem.entity.Question;
import com.schoolmanagementsystem.service.QuestionService;

import java.util.List;

@RestController
@RequestMapping("/api/question")
public class QuestionController {

	@Autowired
	private QuestionService questionService;

	@PostMapping("/create")
	public Question createQuestion(@RequestBody final Question question) {
		return this.questionService.createQuestion(question);
	}

	@GetMapping("/retrieve")
	public List<Question> getAllQuestion() {
		return this.questionService.getAllQuestion();
	}

	@GetMapping("/retrieve/{id}")
	public Question getQuestionById(@PathVariable final Long id) {
		return this.questionService.getQuestionById(id);
	}

	@PutMapping("/update/{id}")
	public String updateQuestion(@PathVariable final Long id, @RequestBody final Question questionDetails) {
		return this.questionService.updateQuestion(id, questionDetails);
	}

	@DeleteMapping("/remove/{id}")
	public void deleteQuestion(@PathVariable final Long id) {
		this.questionService.deleteQuestion(id);
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
