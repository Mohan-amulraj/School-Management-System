package com.schoolmanagementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schoolmanagementsystem.dto.ResponseDTO;
import com.schoolmanagementsystem.entity.School;
import com.schoolmanagementsystem.service.SchoolService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class SchoolControllerTest {

    @InjectMocks
    private SchoolController schoolController;

    @Mock
    private SchoolService schoolService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(schoolController).build();
    }

    @Test
    public void testCreateSchool() throws Exception {

        School school = new School();
        school.setId(1L);
        school.setName("Mohan");
        school.setAddress("Madurai");
        when(schoolService.createSchool(school)).thenReturn(school);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/api/school/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(school)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(Constants.CREATED))
                .andExpect(jsonPath("$.message").value("School created successfully"))
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.name").value("Mohan"))
                .andExpect(jsonPath("$.data.address").value("Madurai"));
        verify(schoolService,times(1)).createSchool(school);
    }

    @Test
    void testGetAllSchools() throws Exception {

        School school1 = new School();
        school1.setId(1L);
        school1.setName("Mohan");
        school1.setAddress("Madurai");
        School school2 = new School();
        school2.setId(2L);
        school2.setName("Guru");
        school2.setAddress("Salem");
        List<School> schoolList = Arrays.asList(school1, school2);
        when(schoolService.getAllSchool()).thenReturn(schoolList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/school/retrieve"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(Constants.RETRIEVED))
                .andExpect(jsonPath("$.message").value("School retrieved successfully"))
                .andExpect(jsonPath("$.data[0].id").value(1L))
                .andExpect(jsonPath("$.data[1].id").value(2L));
        verify(schoolService,times(1)).getAllSchool();
    }

    @Test
    void testGetSchoolById() throws Exception {
        School school = new School();
        school.setId(1L);
        school.setName("Mohan");
        school.setAddress("Madurai");

        when(schoolService.getSchoolById(1L)).thenReturn(school);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/school/retrieve/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(Constants.RETRIEVED))
                .andExpect(jsonPath("$.message").value("School id retrieved successfully"))
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.name").value("Mohan"));
        verify(schoolService,times(1)).getSchoolById(1L);
    }

    @Test
    void testUpdateSchool() throws Exception {
        School school = new School();
        school.setId(1L);
        school.setName("Mohan");
        school.setAddress("Madurai");

        when(schoolService.updateSchool(1L, school)).thenReturn(school);

        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(put("/api/school/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(school)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(Constants.UPDATED))
                .andExpect(jsonPath("$.message").value("School updated successfully"))
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.name").value("Mohan"));
        verify(schoolService,times(1)).updateSchool(1L,school);
    }

    @Test
    void testDeleteSchool() throws Exception {
        Long schoolId = 1L;
        doNothing().when(schoolService).deleteSchool(schoolId);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/school/remove/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(Constants.DELETED))
                .andExpect(jsonPath("$.message").value("School deleted successfully"))
                .andExpect(jsonPath("$.data").value(schoolId));
        verify(schoolService, times(1)).deleteSchool(schoolId);
    }

        @Test
        void testGetSchoolPage() {

            int index = 0;
            int size = 10;
            String field = "name";
            Page<School> mockPage = new PageImpl<>(Collections.emptyList());
            when(schoolService.getSchoolPage(index, size, field)).thenReturn(mockPage);
            ResponseDTO response = schoolController.getSchoolPage(index, size, field);
            assertNotNull(response);
            assertEquals(200, response.getStatusCode());
            assertEquals("School pagination successfully", response.getMessage());
            assertEquals(mockPage, response.getData());

            verify(schoolService, times(1)).getSchoolPage(index, size, field);
        }

    @AfterAll
    static void toEndSchool(){
        System.out.println("School ControllerTestcase execution finished");
    }
}

