package com.schoolmanagementsystem.service;

import com.schoolmanagementsystem.entity.Subject;
import com.schoolmanagementsystem.repository.SubjectRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class SubjectServiceTest {

    @Mock
    private SubjectRepository subjectRepository;

    @InjectMocks
    private SubjectService subjectService;
    private Subject subject;

    @BeforeEach
    void setUp() {
        subject = new Subject();
        subject.setId(1L);
        subject.setName("Java");
    }

    @Test
    void testCreateSubject() {
        when(subjectRepository.save(any(Subject.class))).thenReturn(subject);
        Subject createSubject = subjectService.createSubject(subject);
        assertNotNull(createSubject);
        assertEquals(1L, createSubject.getId());
        assertEquals("Java", createSubject.getName());
        verify(subjectRepository, times(1)).save(subject);
    }

    @Test
    void testRetrieveSubject(){
        List<Subject> subjectList = Collections.singletonList(subject);
        when(subjectRepository.findAll()).thenReturn(subjectList);
        List<Subject> retrieveSubject = subjectService.getAllSubject();
        assertNotNull(retrieveSubject);
        assertEquals(1, retrieveSubject.size());
        assertEquals("Java", retrieveSubject.get(0).getName());
        verify(subjectRepository,times(1)).findAll();
    }
@Test
void testSubjectRetrieveById(){
    when(subjectRepository.findById(1L)).thenReturn(Optional.of(subject));
    Subject retrievedSubject = subjectService.getSubjectById(1L);
    assertNotNull(retrievedSubject);
    assertEquals(1L, retrievedSubject.getId());
    assertEquals("Java", retrievedSubject.getName());
    verify(subjectRepository, times(1)).findById(1L);
}

    @Test
    void testUpdateSubject(){
        when(subjectRepository.findById(1L)).thenReturn(Optional.of(subject));
        when(subjectRepository.save(subject)).thenReturn(subject);
        Subject updateSubject = subjectService.updateSubject(1L,subject);
        assertNotNull(updateSubject);
        assertEquals(1L, updateSubject.getId());
        assertEquals("Java", updateSubject.getName());
        verify(subjectRepository, times(1)).findById(1L);
        verify(subjectRepository, times(1)).save(subject);
    }
    @Test
    void testRemoveSubject(){
        when(subjectRepository.findById(1L)).thenReturn(Optional.of(subject));
        doNothing().when(subjectRepository).delete(subject);
        subjectService.deleteSubject(1L);
        verify(subjectRepository, times(1)).findById(1L);
        verify(subjectRepository, times(1)).delete(subject);
    }
    @AfterAll
    static void toEndSubject(){
        System.out.println("Subject ServiceTestcase execution finished");
    }
}
