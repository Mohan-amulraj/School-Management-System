package com.schoolmanagementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schoolmanagementsystem.entity.StudentAnswer;
import com.schoolmanagementsystem.service.StudentAnswerService;
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
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class StudentAnswerControllerTest {


        @InjectMocks
        private StudentAnswerController studentAnswerController;

        @Mock
        private StudentAnswerService studentAnswerService;

        private MockMvc mockMvc;

        @BeforeEach
        public void setup() {
            MockitoAnnotations.openMocks(this);
            mockMvc = MockMvcBuilders.standaloneSetup(studentAnswerController).build();
        }

        @Test
        void testCreateStudentAnswer() throws Exception {
            StudentAnswer studentAnswer = new StudentAnswer();
            studentAnswer.setId(1L);
            studentAnswer.setAnswer("(a)");
            when(studentAnswerService.createStudentAnswer(studentAnswer)).thenReturn(studentAnswer);
            ObjectMapper objectMapper = new ObjectMapper();
            mockMvc.perform(post("/api/student-answer/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(studentAnswer)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value(Constants.CREATED))
                    .andExpect(jsonPath("$.message").value("Answer created successfully"))
                    .andExpect(jsonPath("$.data.id").value(1L))
                    .andExpect(jsonPath("$.data.answer").value("(a)"));
            verify(studentAnswerService,times(1)).createStudentAnswer(studentAnswer);
        }
    @Test
    void testStudentAnswerRetrieveAll() throws Exception {
        StudentAnswer studentAnswer1 = new StudentAnswer();
        studentAnswer1.setId(1L);
        studentAnswer1.setAnswer("(a)");

        StudentAnswer studentAnswer2 = new StudentAnswer();
        studentAnswer2.setId(2L);
        studentAnswer2.setAnswer("(b)");

        List<StudentAnswer> studentAnswerList = Arrays.asList(studentAnswer1, studentAnswer2);
        when(studentAnswerService.getAllStudentAnswer()).thenReturn(studentAnswerList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/student-answer/retrieve")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].answer").value("(a)"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].answer").value("(b)"));

        verify(studentAnswerService, times(1)).getAllStudentAnswer();

    }

    @Test
        void testGetStudentAnswerById() throws Exception {
            StudentAnswer studentAnswer = new StudentAnswer();
            studentAnswer.setId(1L);
            studentAnswer.setAnswer("(a)");
            when(studentAnswerService.getStudentAnswerById(1L)).thenReturn(studentAnswer);

            mockMvc.perform(MockMvcRequestBuilders.get("/api/student-answer/retrieve/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value(Constants.RETRIEVED))
                    .andExpect(jsonPath("$.message").value("Answer retrieved successfully"))
                    .andExpect(jsonPath("$.data.id").value(1L))
                    .andExpect(jsonPath("$.data.answer").value("(a)"));
            verify(studentAnswerService,times(1)).getStudentAnswerById(1L);
        }

        @Test
        void testUpdateStudentAnswer() throws Exception {
            StudentAnswer studentAnswer = new StudentAnswer();
            studentAnswer.setId(1L);
            studentAnswer.setAnswer("(a)");

            when(studentAnswerService.updateStudentAnswer(1L,studentAnswer)).thenReturn(studentAnswer);
            ObjectMapper objectMapper = new ObjectMapper();
            mockMvc.perform(put("/api/student-answer/update/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(studentAnswer)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value(Constants.UPDATED))
                    .andExpect(jsonPath("$.message").value("Answer updated successfully"))
                    .andExpect(jsonPath("$.data.id").value(1L))
                    .andExpect(jsonPath("$.data.answer").value("(a)"));
            verify(studentAnswerService,times(1)).updateStudentAnswer(1L,studentAnswer);
        }

        @Test
        void testDeleteSubject() throws Exception {
            Long studentAnswerId = 1L;
            doNothing().when(studentAnswerService).deleteStudentAnswer(1L);
            mockMvc.perform(MockMvcRequestBuilders.delete("/api/student-answer/remove/1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value(Constants.DELETED))
                    .andExpect(jsonPath("$.message").value("Answer deleted successfully"))
                    .andExpect(jsonPath("$.data").value(studentAnswerId));
            verify(studentAnswerService, times(1)).deleteStudentAnswer(1L);
        }
        @AfterAll
        static void toEndStudentAnswer(){
            System.out.println("Answer ControllerTestcase execution finished");
        }
}





