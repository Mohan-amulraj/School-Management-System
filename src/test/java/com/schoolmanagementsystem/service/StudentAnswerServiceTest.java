package com.schoolmanagementsystem.service;

import com.schoolmanagementsystem.entity.StudentAnswer;
import com.schoolmanagementsystem.repository.StudentAnswerRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class StudentAnswerServiceTest {

        @Mock
        private StudentAnswerRepository studentAnswerRepository;

        @InjectMocks
        private StudentAnswerService studentAnswerService;
        private StudentAnswer studentAnswer;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        void testCreateStudentAnswer() {
            studentAnswer = new StudentAnswer();
            studentAnswer.setId(1L);
            studentAnswer.setAnswer("(a)");
            when(studentAnswerRepository.save(any(StudentAnswer.class))).thenReturn(studentAnswer);
            StudentAnswer saveStudent = studentAnswerService.createStudentAnswer(studentAnswer);
            assertNotNull(saveStudent);
            assertEquals(1L, saveStudent.getId());
            assertEquals("(a)", saveStudent.getAnswer());
            verify(studentAnswerRepository, times(1)).save(studentAnswer);
        }
        @Test
        void testRetrieveStudentAnswer(){
            studentAnswer = new StudentAnswer();
            studentAnswer.setId(1L);
            studentAnswer.setAnswer("(a)");
            List<StudentAnswer> studentAnswerList = Collections.singletonList(studentAnswer);
            when(studentAnswerRepository.findAll()).thenReturn(studentAnswerList);
            List<StudentAnswer> retrieveStudent = studentAnswerService.getAllStudentAnswer();
            assertNotNull(retrieveStudent);
            assertEquals(1, retrieveStudent.size());
            assertEquals("(a)",retrieveStudent.get(0).getAnswer());
            verify(studentAnswerRepository,times(1)).findAll();
        }
        @Test
        void testStudentAnswerRetrieveById(){
            studentAnswer = new StudentAnswer();
            studentAnswer.setId(1L);
            studentAnswer.setAnswer("(a)");
            when(studentAnswerRepository.findById(1L)).thenReturn(Optional.of(studentAnswer));
            StudentAnswer retrieveStudentAnswer = studentAnswerService.getStudentAnswerById(1L);
            assertNotNull(retrieveStudentAnswer);
            assertEquals(1L, retrieveStudentAnswer.getId());
            assertEquals("(a)", retrieveStudentAnswer.getAnswer());
            verify(studentAnswerRepository,times(1)).findById(1L);
        }
        @Test
        void testUpdateStudentAnswer(){
            studentAnswer = new StudentAnswer();
            studentAnswer.setId(1L);
            studentAnswer.setAnswer("(a)");
            when(studentAnswerRepository.findById(1L)).thenReturn(Optional.of(studentAnswer));
            when(studentAnswerRepository.save(studentAnswer)).thenReturn(studentAnswer);
            StudentAnswer updateStudentAnswer = studentAnswerService.updateStudentAnswer(1L,studentAnswer);
            assertNotNull(updateStudentAnswer);
            assertEquals(1L, updateStudentAnswer.getId());
            assertEquals("(a)", updateStudentAnswer.getAnswer());
            verify(studentAnswerRepository, times(1)).findById(1L);
            verify(studentAnswerRepository, times(1)).save(studentAnswer);
        }
        @Test
        void testRemoveStudentAnswer(){
            StudentAnswer studentAnswer =new StudentAnswer();
            studentAnswer.setId(1L);
            when(studentAnswerRepository.findById(1L)).thenReturn(Optional.of(studentAnswer));
            doNothing().when(studentAnswerRepository).delete(studentAnswer);
            studentAnswerService.deleteStudentAnswer(1L);
            verify(studentAnswerRepository, times(1)).findById(1L);
            verify(studentAnswerRepository, times(1)).delete(studentAnswer);
        }
        @AfterAll
        static void toEndStudentAnswer(){
            System.out.println("StudentAnswer ServiceTestcase execution finished");
        }
}
