package com.schoolmanagementsystem.service;

import com.schoolmanagementsystem.entity.Test;
import com.schoolmanagementsystem.repository.TestRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestServiceTest {


    @Mock
    private TestRepository testRepository;

    @InjectMocks
    private TestService testService;

    @BeforeEach
    void setUp() {
        Test test = new Test();
        test.setId(1L);
        test.setTest("Java");
    }

    @org.junit.jupiter.api.Test
    void testCreateTest() {
        Test test = new Test();
        test.setId(1L);
        test.setTest("Java");
        when(testRepository.save(any(Test.class))).thenReturn(test);
        Test createTest = testService.createTest(test);
        assertNotNull(createTest);
        assertEquals(1L, createTest.getId());
        assertEquals("Java", createTest.getTest());
        verify(testRepository, times(1)).save(test);
    }

    @org.junit.jupiter.api.Test
    void testRetrieveTest(){
        Test test = new Test();
        test.setId(1L);
        test.setTest("Java");
        List<Test> testList = Collections.singletonList(test);
        when(testRepository.findAll()).thenReturn(testList);
        List<Test> retrieveTest = testService.getAllTest();
        assertNotNull(retrieveTest);
        assertEquals(1, retrieveTest.size());
        assertEquals("Java", retrieveTest.get(0).getTest());
        verify(testRepository,times(1)).findAll();
    }
    @org.junit.jupiter.api.Test
    void testTestRetrieveById(){
        Test test = new Test();
        test.setId(1L);
        test.setTest("Java");
        when(testRepository.findById(1L)).thenReturn(Optional.of(test));
        Test retrievedTest = testService.getTestById(1L);
        assertNotNull(retrievedTest);
        assertEquals(1L, retrievedTest.getId());
        assertEquals("Java", retrievedTest.getTest());
        verify(testRepository, times(1)).findById(1L);
    }

    @org.junit.jupiter.api.Test
    void testUpdateTest(){
        Test test = new Test();
        test.setId(1L);
        test.setTest("Java");
        when(testRepository.findById(1L)).thenReturn(Optional.of(test));
        when(testRepository.save(test)).thenReturn(test);
        Test updateTest = testService.updateTest(1L,test);
        assertNotNull(updateTest);
        assertEquals(1L, updateTest.getId());
        assertEquals("Java", updateTest.getTest());
        verify(testRepository, times(1)).findById(1L);
        verify(testRepository, times(1)).save(test);
    }

    @org.junit.jupiter.api.Test
    void testRemoveTest(){
        Test test = new Test();
        test.setId(1L);
        when(testRepository.findById(1L)).thenReturn(Optional.of(test));
        doNothing().when(testRepository).delete(test);
        testService.deleteTest(1L);
        verify(testRepository, times(1)).findById(1L);
        verify(testRepository, times(1)).delete(test);
    }
    @AfterAll
    static void toEndTest(){
        System.out.println("Test ServiceTestcase execution finished");
    }
}
