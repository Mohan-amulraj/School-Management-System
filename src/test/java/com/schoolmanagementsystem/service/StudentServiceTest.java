package com.schoolmanagementsystem.service;

import com.schoolmanagementsystem.entity.Student;
import com.schoolmanagementsystem.repository.StudentRepository;
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

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;
    private Student student;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setId(1L);
        student.setName("Mohan");
        student.setAddress("Madurai");
    }

    @Test
    void testCreateStudent() {
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        Student saveStudent = studentService.createStudent(student);
        assertNotNull(saveStudent);
        assertEquals(1L, saveStudent.getId());
        assertEquals("Mohan", saveStudent.getName());
        assertEquals("Madurai",saveStudent.getAddress());
        verify(studentRepository, times(1)).save(student);
    }
    @Test
    void testRetrieveStudent(){
        List<Student> studentList = Collections.singletonList(student);
        when(studentRepository.findAll()).thenReturn(studentList);
        List<Student> retrieveStudent = studentService.getAllStudents();
        assertNotNull(retrieveStudent);
        assertEquals(1, retrieveStudent.size());
        assertEquals("Mohan",retrieveStudent.get(0).getName());
        assertEquals("Madurai",retrieveStudent.get(0).getAddress());
        verify(studentRepository,times(1)).findAll();

    }
    @Test
    void testStudentRetrieveById(){
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        Student retrieveStudent = studentService.getStudentById(1L);
        assertNotNull(retrieveStudent);
        assertEquals(1L,retrieveStudent.getId());
        assertEquals("Mohan",retrieveStudent.getName());
        assertEquals("Madurai",retrieveStudent.getAddress());
        verify(studentRepository,times(1)).findById(1L);
    }
    @Test
    void testUpdateStudent(){
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentRepository.save(student)).thenReturn(student);
        Student updateStudent = studentService.updateStudent(1L, student);
        assertNotNull(updateStudent);
        assertEquals(1L, updateStudent.getId());
        assertEquals("Mohan", updateStudent.getName());
        assertEquals("Madurai", updateStudent.getAddress());
        verify(studentRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).save(student);
    }
    @Test
    void testRemoveStudent(){
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        doNothing().when(studentRepository).delete(student);
        studentService.deleteStudent(1L);
        verify(studentRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).delete(student);
    }
    @Test
    void testCountStudentBySchool() {
        Long schoolId = 1L;
        when(studentRepository.countBySchoolId(schoolId)).thenReturn(10L);
        Long count = studentService.countStudentBySchool(schoolId);
        assertEquals(10L, count);
        verify(studentRepository, times(1)).countBySchoolId(schoolId);
    }
    @Test
    public void testGetStudentPage() {
        int index = 0;
        int size = 10;
        String field = "name";
        List<Student> studentList = Collections.singletonList(new Student());
        Pageable pageable = PageRequest.of(index, size, Sort.by(Sort.Direction.ASC, field));
        PageImpl<Student> expectedPage = new PageImpl<>(studentList, pageable, studentList.size());
        when(studentRepository.findAll(pageable)).thenReturn(expectedPage);
        Page<Student> actualPage = studentService.getStudentPage(index, size, field);
        assertEquals(expectedPage, actualPage);
    }

    @AfterAll
    static void toEndStudent(){
        System.out.println("Student ServiceTestcase execution finished");
    }
}
