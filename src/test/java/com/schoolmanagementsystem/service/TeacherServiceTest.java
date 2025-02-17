package com.schoolmanagementsystem.service;

import com.schoolmanagementsystem.entity.Student;
import com.schoolmanagementsystem.entity.Teacher;
import com.schoolmanagementsystem.repository.TeacherRepository;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class TeacherServiceTest {

    @Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    private TeacherService teacherService;
    private Teacher teacher;

    @BeforeEach
    void setUp() {
        teacher = new Teacher();
        teacher.setId(1L);
        teacher.setName("Mohan");
        teacher.setAddress("Madurai");
    }

    @Test
    void testCreateTeacher() {
        when(teacherRepository.save(any(Teacher.class))).thenReturn(teacher);
        Teacher createTeacher = teacherService.createTeacher(teacher);
        assertNotNull(createTeacher);
        assertEquals(1L, createTeacher.getId());
        assertEquals("Mohan", createTeacher.getName());
        assertEquals("Madurai",createTeacher.getAddress());
        verify(teacherRepository, times(1)).save(teacher);
    }

    @Test
    void testRetrieveTeacher(){
        List<Teacher> teacherList = Collections.singletonList(teacher);
        when(teacherRepository.findAll()).thenReturn(teacherList);
        List<Teacher> retrieveTeacher = teacherService.getAllTeacher();
        assertNotNull(retrieveTeacher);
        assertEquals(1, retrieveTeacher.size());
        assertEquals("Mohan", retrieveTeacher.get(0).getName());
        assertEquals("Madurai", retrieveTeacher.get(0).getAddress());
        verify(teacherRepository,times(1)).findAll();
    }
    @Test
    void testTeacherRetrieveById(){
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        Teacher retrieveTeacher = teacherService.getTeacherById(1L);
        assertNotNull(retrieveTeacher);
        assertEquals(1L, retrieveTeacher.getId());
        assertEquals("Mohan", retrieveTeacher.getName());
        assertEquals("Madurai", retrieveTeacher.getAddress());
        verify(teacherRepository,times(1)).findById(1L);
    }
    @Test
    void testUpdateTeacher(){
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(teacherRepository.save(teacher)).thenReturn(teacher);
        Teacher updateTeacher = teacherService.updateTeacher(1L, teacher);
        assertNotNull(updateTeacher);
        assertEquals(1L, updateTeacher.getId());
        assertEquals("Mohan", updateTeacher.getName());
        assertEquals("Madurai", updateTeacher.getAddress());
        verify(teacherRepository, times(1)).findById(1L);
        verify(teacherRepository, times(1)).save(teacher);
    }
    @Test
    void testRemoveTeacher(){
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        doNothing().when(teacherRepository).delete(teacher);
        teacherService.deleteTeacher(1L);
        verify(teacherRepository, times(1)).findById(1L);
        verify(teacherRepository, times(1)).delete(teacher);
    }
    @Test
    void testCountTeacherBySchool() {
        Long teacherId = 1L;
        when(teacherRepository.countBySchoolId(teacherId)).thenReturn(10L);
        Long count = teacherService.countTeacherBySchool(teacherId);
        assertEquals(10L, count);
        verify(teacherRepository, times(1)).countBySchoolId(teacherId);
    }
    @Test
    public void testGetTeacherPage() {
        int index = 0;
        int size = 10;
        String field = "name";
        List<Teacher> teacherList = Collections.singletonList(new Teacher());
        Pageable pageable = PageRequest.of(index, size, Sort.by(Sort.Direction.ASC, field));
        PageImpl<Teacher> expectedPage = new PageImpl<>(teacherList, pageable, teacherList.size());
        when(teacherRepository.findAll(pageable)).thenReturn(expectedPage);
        Page<Teacher> actualPage = teacherService.getTeacherPage(index, size, field);
        assertEquals(expectedPage, actualPage);
    }
    @AfterAll
    static void toEndTeacher(){
        System.out.println("Teacher ServiceTestcase execution finished");
    }
}
