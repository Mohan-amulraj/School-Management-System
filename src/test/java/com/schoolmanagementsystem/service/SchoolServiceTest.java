package com.schoolmanagementsystem.service;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.schoolmanagementsystem.entity.School;
import com.schoolmanagementsystem.repository.SchoolRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class SchoolServiceTest {

    @Mock
    private SchoolRepository schoolRepository;

    @InjectMocks
    private SchoolService schoolService;

    private School school;

    @BeforeEach
    void setUp() {
        school = new School();
        school.setId(1L);
        school.setName("Mohan");
        school.setAddress("Madurai");
    }


    @Test
    void testCreateSchool() {
        when(schoolRepository.save(any(School.class))).thenReturn(school);
        School saveSchool = schoolService.createSchool(school);
        assertNotNull(saveSchool);
        assertEquals(1L, saveSchool.getId());
        assertEquals("Mohan", saveSchool.getName());
        assertEquals("Madurai",saveSchool.getAddress());
        verify(schoolRepository, times(1)).save(school);
    }
    @Test
    void testRetrieveSchool() {
        List<School> school1 = Collections.singletonList(school);
        when(schoolRepository.findAll()).thenReturn(school1);
        List<School> retrievedSchool = schoolService.getAllSchool();
        assertNotNull(retrievedSchool);
        assertEquals(1, retrievedSchool.size());
        assertEquals("Mohan", retrievedSchool.get(0).getName());
        assertEquals("Madurai",retrievedSchool.get(0).getAddress());
        verify(schoolRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveSchoolById() {
        when(schoolRepository.findById(1L)).thenReturn(Optional.of(school));
        School retrievedSchool = schoolService.getSchoolById(1L);
        assertNotNull(retrievedSchool);
        assertEquals(1L, retrievedSchool.getId());
        assertEquals("Mohan", retrievedSchool.getName());
        assertEquals("Madurai",retrievedSchool.getAddress());
        verify(schoolRepository, times(1)).findById(1L);
    }
    @Test
    void testUpdateSchool() {
        when(schoolRepository.findById(1L)).thenReturn(Optional.of(school));
        when(schoolRepository.save(school)).thenReturn(school);
        School updatedSchool = schoolService.updateSchool(1L, school);
        assertNotNull(updatedSchool);
        assertEquals(1L, updatedSchool.getId());
        assertEquals("Mohan", updatedSchool.getName());
        assertEquals("Madurai", updatedSchool.getAddress());
        verify(schoolRepository, times(1)).findById(1L);
        verify(schoolRepository, times(1)).save(school);
    }
    @Test
    void testRemoveSchool() {
        when(schoolRepository.findById(1L)).thenReturn(Optional.of(school));
        doNothing().when(schoolRepository).delete(school);
        schoolService.deleteSchool(1L);
        verify(schoolRepository, times(1)).findById(1L);
        verify(schoolRepository, times(1)).delete(school);
    }

    @Test
     void testCountSchool() {
        when(schoolRepository.count()).thenReturn(10L);
        Long count = schoolService.countSchool();
        assertEquals(10L, count);
    }
    @Test
    public void testGetSchoolPage() {
        int index = 0;
        int size = 10;
        String field = "name";
        List<School> schools = Collections.singletonList(new School());
        Pageable pageable = PageRequest.of(index, size, Sort.by(Sort.Direction.ASC, field));
        Page<School> expectedPage = new PageImpl<>(schools, pageable, schools.size());
        when(schoolRepository.findAll(pageable)).thenReturn(expectedPage);
        Page<School> actualPage = schoolService.getSchoolPage(index, size, field);
        assertEquals(expectedPage, actualPage);
    }

    @AfterAll
    static void toEndSchool(){
        System.out.println("School ServiceTestcase execution finished");
    }
}
