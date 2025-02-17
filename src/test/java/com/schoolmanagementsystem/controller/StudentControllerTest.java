package com.schoolmanagementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schoolmanagementsystem.dto.ResponseDTO;
import com.schoolmanagementsystem.entity.Student;
import com.schoolmanagementsystem.service.StudentService;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class StudentControllerTest {

    @InjectMocks
    private StudentController studentController;

    @Mock
    private StudentService studentService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
    }

    @Test
    void testCreateStudent() throws Exception {

        Student student = new Student();
        student.setId(1L);
        student.setName("Mohan");
        student.setAddress("Madurai");
        when(studentService.createStudent(student)).thenReturn(student);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/api/student/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(Constants.CREATED))
                .andExpect(jsonPath("$.message").value("Student created successfully"))
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.name").value("Mohan"))
                .andExpect(jsonPath("$.data.address").value("Madurai"));
        verify(studentService,times(1)).createStudent(student);
    }

    @Test
    void testGetAllStudent() throws Exception {

        Student student1 = new Student();
        student1.setId(1L);
        student1.setName("Mohan");
        student1.setAddress("Madurai");
        Student student2 = new Student();
        student2.setId(2L);
        student2.setName("Guru");
        student2.setAddress("Salem");
        List<Student> studentList = Arrays.asList(student1, student2);
        when(studentService.getAllStudents()).thenReturn(studentList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/student/retrieve"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(Constants.RETRIEVED))
                .andExpect(jsonPath("$.message").value("Student retrieved successfully"))
                .andExpect(jsonPath("$.data[0].id").value(1L))
                .andExpect(jsonPath("$.data[1].id").value(2L));
        verify(studentService,times(1)).getAllStudents();
    }

    @Test
    void testGetStudentById() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setName("Mohan");
        student.setAddress("Madurai");
        when(studentService.getStudentById(1L)).thenReturn(student);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/student/retrieve/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(Constants.RETRIEVED))
                .andExpect(jsonPath("$.message").value("Student id retrieved successfully"))
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.name").value("Mohan"));
        verify(studentService,times(1)).getStudentById(1L);
    }

    @Test
    void testUpdateStudent() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setName("Mohan");
        student.setAddress("Madurai");
        when(studentService.updateStudent(1L,student)).thenReturn(student);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(put("/api/student/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(Constants.UPDATED))
                .andExpect(jsonPath("$.message").value("Student updated successfully"))
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.name").value("Mohan"));
            verify(studentService, times(1)).updateStudent(1L,student);
    }

    @Test
    void testDeleteStudent() throws Exception {
        Long studentId = 1L;
        doNothing().when(studentService).deleteStudent(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/student/remove/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(Constants.DELETED))
                .andExpect(jsonPath("$.message").value("Student deleted successfully"))
                .andExpect(jsonPath("$.data").value(studentId));
        verify(studentService, times(1)).deleteStudent(1L);
    }
    @Test
    public void testRetrieveStudentCountBySchool() throws Exception {
        Long schoolId = 1L;
        Long studentCount = 10L;

        when(studentService.countStudentBySchool(schoolId)).thenReturn(studentCount);

        mockMvc.perform(get("/api/student/count/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(Constants.COUNT))
                .andExpect(jsonPath("$.message").value("Student Count successfully"))
                .andExpect(jsonPath("$.data").value(studentCount));
        verify(studentService, times(1)).countStudentBySchool(1L);
    }

    @Test
    void testGetStudentPage() {
        int index = 0;
        int size = 10;
        String field = "name";
        Page<Student> mockPage = new PageImpl<>(Collections.emptyList());
        when(studentService.getStudentPage(index, size, field)).thenReturn(mockPage);
        ResponseDTO response = studentController.getStudentPage(index, size, field);
        assertNotNull(response);
        assertEquals(200, response.getStatusCode());
        assertEquals("Student pagination successfully", response.getMessage());
        assertEquals(mockPage, response.getData());

        verify(studentService, times(1)).getStudentPage(index, size, field);
    }

    @AfterAll
    static void toEndStudent(){
        System.out.println("Student ControllerTestcase execution finished");
    }
}
