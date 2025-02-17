package com.schoolmanagementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schoolmanagementsystem.dto.ResponseDTO;
import com.schoolmanagementsystem.entity.Teacher;
import com.schoolmanagementsystem.service.TeacherService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class TeacherControllerTest {

    @InjectMocks
    private TeacherController teacherController;

    @Mock
    private TeacherService teacherService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(teacherController).build();
    }

    @Test
    void testCreateTeacher() throws Exception {

        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setName("Mohan");
        teacher.setAddress("Madurai");
        when(teacherService.createTeacher(teacher)).thenReturn(teacher);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/api/teacher/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(teacher)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(Constants.CREATED))
                .andExpect(jsonPath("$.message").value("Teacher created successfully"))
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.name").value("Mohan"))
                .andExpect(jsonPath("$.data.address").value("Madurai"));
        verify(teacherService,times(1)).createTeacher(teacher);
    }

    @Test
    void testTeacherRetrieveALl() throws Exception {
        Teacher teacher1 = new Teacher();
        teacher1.setId(1L);
        teacher1.setName("Mohan");
        teacher1.setAddress("Madurai");
        Teacher teacher2 = new Teacher();
        teacher2.setId(2L);
        teacher2.setName("Guru");
        teacher2.setAddress("Salem");
        List<Teacher> teacherList = Arrays.asList(teacher1, teacher2);
        when(teacherService.getAllTeacher()).thenReturn(teacherList);

        mockMvc.perform(get("/api/teacher/retrieve"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(Constants.RETRIEVED))
                .andExpect(jsonPath("$.message").value("Teacher retrieved successfully"))
                .andExpect(jsonPath("$.data[0].id").value(1L))
                .andExpect(jsonPath("$.data[1].id").value(2L));
        verify(teacherService,times(1)).getAllTeacher();
    }

    @Test
    void testGetTeacherById() throws Exception {
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setName("Mohan");
        teacher.setAddress("Madurai");

        when(teacherService.getTeacherById(1L)).thenReturn(teacher);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/teacher/retrieve/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(Constants.RETRIEVED))
                .andExpect(jsonPath("$.message").value("Teacher id retrieved successfully"))
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.name").value("Mohan"));
        verify(teacherService,times(1)).getTeacherById(1L);
    }

    @Test
    void testUpdateTeacher() throws Exception {
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setName("Mohan");
        teacher.setAddress("Madurai");

        when(teacherService.updateTeacher(1L, teacher)).thenReturn(teacher);

        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(put("/api/teacher/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(teacher)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(Constants.UPDATED))
                .andExpect(jsonPath("$.message").value("Teacher updated successfully"))
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.name").value("Mohan"));
        verify(teacherService,times(1)).updateTeacher(1L,teacher);
    }
    @Test
    void testDeleteTeacher() throws Exception {
        Long teacherId = 1L;
        lenient().doNothing().when(teacherService).deleteTeacher(teacherId);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/teacher/remove/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(Constants.DELETED))
                .andExpect(jsonPath("$.message").value("Teacher deleted successfully"))
                .andExpect(jsonPath("$.data").value(teacherId));
    }
    @Test
    void testCountStudentBySchool() throws Exception {
        Long schoolId = 1L;
        Long expectedCount = 10L;
        when(teacherService.countTeacherBySchool(schoolId)).thenReturn(expectedCount);
        mockMvc.perform(get("/api/teacher/count/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(10));
        verify(teacherService, times(1)).countTeacherBySchool(1L);
    }
    @Test
    void testGetTeacherPage() {

        int index = 0;
        int size = 10;
        String field = "name";
        Page<Teacher> mockPage = new PageImpl<>(Collections.emptyList());
        when(teacherService.getTeacherPage(index, size, field)).thenReturn(mockPage);
        ResponseDTO response = teacherController.getTeacherPage(index, size, field);
        assertNotNull(response);
        assertEquals(200, response.getStatusCode());
        assertEquals("Teacher pagination successfully", response.getMessage());
        assertEquals(mockPage, response.getData());

        verify(teacherService, times(1)).getTeacherPage(index, size, field);
    }

    @AfterAll
    static void toEndTeacher() {
        System.out.println("Teacher ControllerTestcase execution finished");
    }
}

