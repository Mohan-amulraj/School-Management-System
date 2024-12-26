package com.schoolmanagementsystem.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.schoolmanagementsystem.entity.Student;
import com.schoolmanagementsystem.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/create")
    public Student createStudent(@RequestBody final Student student) {
        return this.studentService.createStudent(student);
    }
    
    @GetMapping("/retrieve")
    public List<Student> getAllStudents() {
        return this.studentService.getAllStudents();
    }

    @GetMapping("/retrieve/{id}")
    public Student getStudentById(@PathVariable final Long id) {
        return this.studentService.getStudentById(id);
    }

    @PutMapping("/update/{id}")
    public String updateStudent(@PathVariable final Long id, @RequestBody final Student studentDetails) {
        return this.studentService.updateStudent(id, studentDetails);
    }

    @DeleteMapping("/remove/{id}")
    public String deleteStudent(@PathVariable final Long id) {
       return this.studentService.deleteStudent(id);
    }
    
    @GetMapping("/count/{id}")
    public Long retrieveStudentCountBySchool(@PathVariable final Long id) {
        return this.studentService.countStudentBySchool(id);
    }
    
    @GetMapping("/pagination")
    public Page<Student> getStudentpage(@RequestParam final int pageIndex, @RequestParam final int pageSize,
            @RequestParam final String field ) {
        return this.studentService.getStudentPage(pageIndex,pageSize,field);
    }
}
