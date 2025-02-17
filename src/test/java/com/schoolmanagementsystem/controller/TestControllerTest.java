package com.schoolmanagementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schoolmanagementsystem.entity.Test;
import com.schoolmanagementsystem.service.TestService;
import com.schoolmanagementsystem.util.Constants;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
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
public class TestControllerTest {

        @InjectMocks
        private TestController testController;

        @Mock
        private TestService testService;

        private MockMvc mockMvc;

        @BeforeEach
        public void setup() {
            MockitoAnnotations.openMocks(this);
            mockMvc = MockMvcBuilders.standaloneSetup(testController).build();
        }

        @org.junit.jupiter.api.Test
        void testCreateTest() throws Exception {
            Test test = new Test();
            test.setId(1L);
            test.setTest("Java");
            when(testService.createTest(test)).thenReturn(test);
            ObjectMapper objectMapper = new ObjectMapper();
            mockMvc.perform(post("/api/test/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(test)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value(Constants.CREATED))
                    .andExpect(jsonPath("$.message").value("Test created successfully"))
                    .andExpect(jsonPath("$.data.id").value(1L))
                    .andExpect(jsonPath("$.data.test").value("Java"));
            verify(testService,times(1)).createTest(test);
        }
        @org.junit.jupiter.api.Test
        void testRetrieveTestALl() throws Exception{
            Test test1 = new Test();
            test1.setId(1L);
            test1.setTest("Java");
            Test test2 = new Test();
            test2.setId(2L);
            test2.setTest("Sql");
            List<Test> testList = Arrays.asList(test1,test2);
            when(testService.getAllTest()).thenReturn(testList);

            mockMvc.perform(get("/api/test/retrieve"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value(Constants.RETRIEVED))
                    .andExpect(jsonPath("$.message").value("Test retrieved successfully"))
                    .andExpect(jsonPath("$.data[0].id").value(1L))
                    .andExpect(jsonPath("$.data[1].id").value(2L));
            verify(testService,times(1)).getAllTest();
        }

        @org.junit.jupiter.api.Test
        void testGetTestById() throws Exception {
            Test test = new Test();
            test.setId(1L);
            test.setTest("Java");
            when(testService.getTestById(1L)).thenReturn(test);

            mockMvc.perform(MockMvcRequestBuilders.get("/api/test/retrieve/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value(Constants.RETRIEVED))
                    .andExpect(jsonPath("$.message").value("Test retrieved successfully"))
                    .andExpect(jsonPath("$.data.id").value(1L))
                    .andExpect(jsonPath("$.data.test").value("Java"));
            verify(testService,times(1)).getTestById(1L);
        }

        @org.junit.jupiter.api.Test
        void testUpdateTest() throws Exception {
            Test test = new Test();
            test.setId(1L);
            test.setTest("Java");
            when(testService.updateTest(1L,test)).thenReturn(test);
            ObjectMapper objectMapper = new ObjectMapper();
            mockMvc.perform(put("/api/test/update/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(test)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value(Constants.UPDATED))
                    .andExpect(jsonPath("$.message").value("Test updated successfully"))
                    .andExpect(jsonPath("$.data.id").value(1L))
                    .andExpect(jsonPath("$.data.test").value("Java"));
            verify(testService,times(1)).updateTest(1L,test);
        }


        @org.junit.jupiter.api.Test
        void testDeleteTest() throws Exception {
            Long testId = 1L;
            doNothing().when(testService).deleteTest(1L);
            mockMvc.perform(MockMvcRequestBuilders.delete("/api/test/remove/1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value(Constants.DELETED))
                    .andExpect(jsonPath("$.message").value("Test deleted successfully"))
                    .andExpect(jsonPath("$.data").value(testId));
            verify(testService, times(1)).deleteTest(1L);
        }
        @AfterAll
        static void toEndTest(){
            System.out.println("Test ControllerTestcase execution finished");
        }
}
