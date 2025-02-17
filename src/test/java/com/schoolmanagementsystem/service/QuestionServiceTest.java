package com.schoolmanagementsystem.service;

import com.schoolmanagementsystem.dto.QuestionDTO;
import com.schoolmanagementsystem.entity.Question;
import com.schoolmanagementsystem.exception.UserNotFoundException;
import com.schoolmanagementsystem.repository.QuestionRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class QuestionServiceTest {

    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private QuestionService questionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Question question1 =new Question();
        question1.setId(1L);
        question1.setQuestion("What is Java?");
        question1.setChoice1("A");
        question1.setChoice1("B");
        question1.setChoice1("C");
        question1.setChoice1("D");
        Question question2 =new Question();
        question2.setId(2L);
        question2.setQuestion("What is Spring Boot?");
        question2.setChoice1("A");
        question2.setChoice1("B");
        question2.setChoice1("C");
        question2.setChoice1("D");
        }

    @Test
    void testCreateQuestion() {
        Question question = new Question();
        question.setId(1L);
        question.setQuestion("What is Java?");
        question.setChoice1("(a)Object Oriented Language");
        question.setChoice2("(b)High Level Language");
        question.setChoice3("(c)Programming Language");
        question.setChoice4("(d)Platform Independent");
        when(questionRepository.save(any(Question.class))).thenReturn(question);
        Question saveQuestion = questionService.createQuestion(question);
        assertNotNull(saveQuestion);
        assertEquals(1L, saveQuestion.getId());
        assertEquals("What is Java?", saveQuestion.getQuestion());
        assertEquals("(a)Object Oriented Language",saveQuestion.getChoice1());
        assertEquals("(b)High Level Language",saveQuestion.getChoice2());
        assertEquals("(c)Programming Language",saveQuestion.getChoice3());
        assertEquals("(d)Platform Independent",saveQuestion.getChoice4());
        verify(questionRepository, times(1)).save(question);
    }
    @Test
    void testRetrieveQuestion(){
        Question question = new Question();
        question.setId(1L);
        question.setQuestion("What is Java?");
        question.setChoice1("(a)Object Oriented Language");
        question.setChoice2("(b)High Level Language");
        question.setChoice3("(c)Programming Language");
        question.setChoice4("(d)Platform Independent");
        List<Question> questionList = Collections.singletonList(question);
        when(questionRepository.findAll()).thenReturn(questionList);
        List<Question> retrieveQuestion = questionService.getAllQuestion();
        assertNotNull(retrieveQuestion);
        assertEquals(1L, retrieveQuestion.size());
        assertEquals("What is Java?", retrieveQuestion.get(0).getQuestion());
        assertEquals("(a)Object Oriented Language",retrieveQuestion.get(0).getChoice1());
        assertEquals("(b)High Level Language",retrieveQuestion.get(0).getChoice2());
        assertEquals("(c)Programming Language",retrieveQuestion.get(0).getChoice3());
        assertEquals("(d)Platform Independent",retrieveQuestion.get(0).getChoice4());
        verify(questionRepository,times(1)).findAll();

    }
    @Test
    void testQuestionRetrieveById(){
        Question question = new Question();
        question.setId(1L);
        when(questionRepository.findById(1L)).thenReturn(Optional.of(question));
        Question retrieveQuestion = questionService.getQuestionById(1L);
        assertNotNull(retrieveQuestion);
        assertEquals(1L,retrieveQuestion.getId());
        verify(questionRepository,times(1)).findById(1L);
    }
    @Test
    void testUpdateQuestion(){
        Question question = new Question();
        question.setId(1L);
        question.setQuestion("What is Java?");
        question.setChoice1("(a)Object Oriented Language");
        question.setChoice2("(b)High Level Language");
        question.setChoice3("(c)Programming Language");
        question.setChoice4("(d)Platform Independent");
        when(questionRepository.findById(1L)).thenReturn(Optional.of(question));
        when(questionRepository.save(question)).thenReturn(question);
        Question updateQuestion = questionService.updateQuestion(1L ,question);
        assertNotNull(updateQuestion);
        assertEquals(1L, updateQuestion.getId());
        verify(questionRepository, times(1)).findById(1L);
        verify(questionRepository, times(1)).save(question);
    }
    @Test
    void testRemoveQuestion(){
        Question question = new Question();
        question.setId(1L);
        when(questionRepository.findById(1L)).thenReturn(Optional.of(question));
        doNothing().when(questionRepository).delete(question);
        questionService.deleteQuestion(1L);
        verify(questionRepository, times(1)).findById(1L);
        verify(questionRepository, times(1)).delete(question);
    }
    @Test
    void testRetrieveQuestionsDTO() {
        Question question1 =new Question();
        question1.setId(1L);
        question1.setQuestion("What is Java?");
        question1.setChoice1("A");
        question1.setChoice1("B");
        question1.setChoice1("C");
        question1.setChoice1("D");
        Question question2 =new Question();
        question2.setId(2L);
        question2.setQuestion("What is Spring Boot?");
        question2.setChoice1("A");
        question2.setChoice1("B");
        question2.setChoice1("C");
        question2.setChoice1("D");
        when(questionRepository.findAll()).thenReturn(Arrays.asList(question1, question2));

        List<QuestionDTO> result = questionService.retrieveQuestions();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("What is Java?", result.get(0).getQuestion());
        assertEquals("What is Spring Boot?", result.get(1).getQuestion());
    }

    @Test
    void testGetQuestionDTO_Found() {
        Question question1 =new Question();
        question1.setId(1L);
        question1.setQuestion("What is Java?");
        question1.setChoice1("A");
        question1.setChoice1("B");
        question1.setChoice1("C");
        question1.setChoice1("D");
        when(questionRepository.findById(1L)).thenReturn(Optional.of(question1));

        QuestionDTO result = questionService.getQuestionDTO(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("What is Java?", result.getQuestion());
    }

    @Test
    void testGetQuestionDTO_NotFound() {
        when(questionRepository.findById(3L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(UserNotFoundException.class, () -> questionService.getQuestionDTO(3L));

        assertEquals("Question not found with id: 3", exception.getMessage());
    }

    @Test
    void testConvert() {
        Question question1 =new Question();
        question1.setId(1L);
        question1.setQuestion("What is Java?");
        question1.setChoice1("A");
        question1.setChoice1("B");
        question1.setChoice1("C");
        question1.setChoice1("D");
        QuestionDTO dto = questionService.convert(question1);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("What is Java?", dto.getQuestion());
    }

    @Test
    void testResponse() {
        Question question2 =new Question();
        question2.setId(2L);
        question2.setQuestion("What is Spring Boot?");
        question2.setChoice1("A");
        question2.setChoice1("B");
        question2.setChoice1("C");
        question2.setChoice1("D");
        QuestionDTO dto = questionService.response(question2);

        assertNotNull(dto);
        assertEquals(2L, dto.getId());
        assertEquals("What is Spring Boot?", dto.getQuestion());
    }
    @AfterAll
    static void toEndQuestion(){
        System.out.println("Question ServiceTestcase execution finished");
    }
}
