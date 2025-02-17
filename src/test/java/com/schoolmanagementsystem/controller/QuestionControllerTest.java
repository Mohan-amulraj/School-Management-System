package com.schoolmanagementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schoolmanagementsystem.entity.Question;
import com.schoolmanagementsystem.service.QuestionService;
import com.schoolmanagementsystem.util.Constants;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class QuestionControllerTest {

        @InjectMocks
        private QuestionController questionController;

        @Mock
        private QuestionService questionService;

        private MockMvc mockMvc;

        @BeforeEach
        public void setup() {
            MockitoAnnotations.openMocks(this);
            mockMvc = MockMvcBuilders.standaloneSetup(questionController).build();
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
        void testCreateQuestion() throws Exception {
            Question question = new Question();
            question.setId(1L);
            question.setQuestion("What is Java?");
            question.setChoice1("(a)Object Oriented Language");
            question.setChoice2("(b)High Level Language");
            question.setChoice3("(c)Programming Language");
            question.setChoice4("(d)Platform Independent");
            when(questionService.createQuestion(question)).thenReturn(question);
            ObjectMapper objectMapper = new ObjectMapper();
            mockMvc.perform(post("/api/question/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(question)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value(Constants.CREATED))
                    .andExpect(jsonPath("$.message").value("Question created successfully"))
                    .andExpect(jsonPath("$.data.id").value(1L));
            verify(questionService,times(1)).createQuestion(question);

        }
    @Test
    void testQuestionRetrieveALl() throws Exception{
        Question question = new Question();
        question.setId(1L);
        question.setQuestion("What is Java?");
        question.setChoice1("(a)Object Oriented Language");
        question.setChoice2("(b)High Level Language");
        question.setChoice3("(c)Programming Language");
        question.setChoice4("(d)Platform Independent");
        Question question1 = new Question();
        question.setId(2L);
        question.setQuestion("What is Java?");
        question1.setChoice1("(a)Object Oriented Language");
        question1.setChoice2("(b)High Level Language");
        question1.setChoice3("(c)Programming Language");
        question1.setChoice4("(d)Platform Independent");
        List<Question> questionList = Arrays.asList(question,question1);
        when(questionService.getAllQuestion()).thenReturn(questionList);

        mockMvc.perform(get("/api/question/retrieve"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(Constants.RETRIEVED))
                .andExpect(jsonPath("$.message").value("Question retrieved successfully"));
        verify(questionService, times(1)).getAllQuestion();
    }


        @Test
        void testQuestionRetrieveById() throws Exception {
            Question question = new Question();
            question.setId(1L);
            question.setQuestion("What is Java?");
            question.setChoice1("(a)Object Oriented Language");
            question.setChoice2("(b)High Level Language");
            question.setChoice3("(c)Programming Language");
            question.setChoice4("(d)Platform Independent");
            when(questionService.getQuestionById(1L)).thenReturn(question);

            mockMvc.perform(MockMvcRequestBuilders.get("/api/question/retrieve/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value(Constants.RETRIEVED))
                    .andExpect(jsonPath("$.message").value("Question id retrieved successfully"))
                    .andExpect(jsonPath("$.data.id").value(1L));
            verify(questionService,times(1)).getQuestionById(1L);
        }


        @Test
        void testUpdateQuestion() throws Exception {
            Question question = new Question();
            question.setId(1L);
            question.setQuestion("What is Java?");
            question.setChoice1("(a)Object Oriented Language");
            question.setChoice2("(b)High Level Language");
            question.setChoice3("(c)Programming Language");
            question.setChoice4("(d)Platform Independent");
            when(questionService.updateQuestion(1L ,question)).thenReturn(question);
            ObjectMapper objectMapper = new ObjectMapper();
            mockMvc.perform(put("/api/question/update/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(question)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value(Constants.UPDATED))
                    .andExpect(jsonPath("$.message").value("Question updated successfully"))
                    .andExpect(jsonPath("$.data.id").value(1L));
            verify(questionService,times(1)).updateQuestion(1L,question);
        }

        @Test
        void testDeleteQuestion() throws Exception {
            Long questionId = 1L;
            doNothing().when(questionService).deleteQuestion(1L);
            mockMvc.perform(MockMvcRequestBuilders.delete("/api/question/remove/1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value(Constants.DELETED))
                    .andExpect(jsonPath("$.message").value("Question deleted successfully"))
                    .andExpect(jsonPath("$.data").value(questionId));
            verify(questionService, times(1)).deleteQuestion(1L);
        }
        @AfterAll
        static void toEndQuestion(){
            System.out.println("Question ControllerTestcase execution finished");
        }
}
