package com.schoolmanagementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schoolmanagementsystem.entity.Subject;
import com.schoolmanagementsystem.service.SubjectService;
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
public class SubjectControllerTest {

    @InjectMocks
    private SubjectController subjectController;

    @Mock
    private SubjectService subjectService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(subjectController).build();
    }

    @Test
    void testCreateSubject() throws Exception {
        Subject subject = new Subject();
        subject.setId(1L);
        subject.setName("Java");
        when(subjectService.createSubject(subject)).thenReturn(subject);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/api/subject/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(subject)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(Constants.CREATED))
                .andExpect(jsonPath("$.message").value("Subject created successfully"))
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.name").value("Java"));
        verify(subjectService,times(1)).createSubject(subject);
    }
    @Test
    void testSubjectRetrieveALl() throws Exception{
        Subject subject1 = new Subject();
        subject1.setId(1L);
        subject1.setName("Java");
        Subject subject2 = new Subject();
        subject2.setId(2L);
        subject2.setName("Sql");
        List<Subject> subjectList = Arrays.asList(subject1,subject2);
        when(subjectService.getAllSubject()).thenReturn(subjectList);

        mockMvc.perform(get("/api/subject/retrieve"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(Constants.RETRIEVED))
                .andExpect(jsonPath("$.message").value("Subject retrieved successfully"))
                .andExpect(jsonPath("$.data[0].id").value(1L))
                .andExpect(jsonPath("$.data[1].id").value(2L));
        verify(subjectService,times(1)).getAllSubject();
    }

    @Test
    void testGetSubjectById() throws Exception {
        Subject subject = new Subject();
        subject.setId(1L);
        subject.setName("Java");
        when(subjectService.getSubjectById(1L)).thenReturn(subject);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/subject/retrieve/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(Constants.RETRIEVED))
                .andExpect(jsonPath("$.message").value("Subject retrieved successfully"))
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.name").value("Java"));
        verify(subjectService,times(1)).getSubjectById(1L);
    }

    @Test
    void testUpdateSubject() throws Exception {
        Subject subject = new Subject();
        subject.setId(1L);
        subject.setName("Java");

        when(subjectService.updateSubject(1L,subject)).thenReturn(subject);
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(put("/api/subject/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(subject)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(Constants.UPDATED))
                .andExpect(jsonPath("$.message").value("Subject updated successfully"))
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.name").value("Java"));
        verify(subjectService,times(1)).updateSubject(1L,subject);
    }


    @Test
    void testDeleteSubject() throws Exception {
        Long subjectId = 1L;
        doNothing().when(subjectService).deleteSubject(1L);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/subject/remove/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(Constants.DELETED))
                .andExpect(jsonPath("$.message").value("Subject deleted successfully"))
                .andExpect(jsonPath("$.data").value(subjectId));
        verify(subjectService, times(1)).deleteSubject(1L);
    }
    @AfterAll
    static void toEndSubject(){
        System.out.println("Subject ControllerTestcase execution finished");
    }
}
