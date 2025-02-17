package com.schoolmanagementsystem.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.schoolmanagementsystem.dto.QuestionDTO;
import com.schoolmanagementsystem.entity.Question;
import com.schoolmanagementsystem.exception.UserNotFoundException;
import com.schoolmanagementsystem.repository.QuestionRepository;

@Service
public class QuestionService {

	@Autowired
	private QuestionRepository questionRepository;

	public Question createQuestion(final Question question) {
		return this.questionRepository.save(question);
	}

	public List<Question> getAllQuestion() {
		return this.questionRepository.findAll();
	}

	public Question getQuestionById(final Long id) {
		return this.questionRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("Question not found for this id : " + id));
	}

	public Question updateQuestion(final Long id, final Question questionDetails) {
		final Question question = questionRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("Question not found for this id : " + id));
		questionDetails.setId(id);
		return this.questionRepository.save(question);
	}

	public void deleteQuestion(final Long id) {
		final Question question = questionRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("Question not found for this id : " + id));
		this.questionRepository.delete(question);		
	}

	public List<QuestionDTO> retrieveQuestions() {
		final List<Question> question = this.questionRepository.findAll();
		return question.stream().map(this::convert).collect(Collectors.toList());
	}

	public QuestionDTO convert(final Question question) {
		return QuestionDTO.builder().id(question.getId()).question(question.getQuestion())
				.choice1(question.getChoice1()).choice2(question.getChoice2()).choice3(question.getChoice3())
				.choice4(question.getChoice4()).build();
	}

	public QuestionDTO getQuestionDTO(final Long id) {
		Optional<Question> question = questionRepository.findById(id);
		if (question.isEmpty()) {
			throw new UserNotFoundException("Question not found with id: " + id);
		}
		return response(question.get());
	}

	public QuestionDTO response(final Question question) {
		return QuestionDTO.builder()

				.id(question.getId()).question(question.getQuestion()).choice1(question.getChoice1())
				.choice2(question.getChoice2()).choice3(question.getChoice3()).choice4(question.getChoice4()).build();
	}
}