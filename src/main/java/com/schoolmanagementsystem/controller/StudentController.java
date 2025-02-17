package com.schoolmanagementsystem.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.schoolmanagementsystem.dto.ResponseDTO;
import com.schoolmanagementsystem.entity.Student;
import com.schoolmanagementsystem.service.StudentService;
import com.schoolmanagementsystem.util.Constants;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/create")
    public ResponseDTO createStudent(@RequestBody final Student student) {
        return new ResponseDTO(Constants.CREATED,200,"Student created successfully",this.studentService.createStudent(student));
    }
    
    @GetMapping("/retrieve")
    public ResponseDTO getAllStudents() {
        return new ResponseDTO(Constants.RETRIEVED,200,"Student retrieved successfully",this.studentService.getAllStudents());
    }

    @GetMapping("/retrieve/{id}")
    public ResponseDTO getStudentById(@PathVariable final Long id) {
        return new ResponseDTO(Constants.RETRIEVED,200,"Student id retrieved successfully",this.studentService.getStudentById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseDTO updateStudent(@PathVariable final Long id, @RequestBody final Student studentDetails) {
        return new ResponseDTO(Constants.UPDATED,200,"Student updated successfully",this.studentService.updateStudent(id, studentDetails));
    }

    @DeleteMapping("/remove/{id}")
    public ResponseDTO deleteStudent(@PathVariable final Long id) {
       this.studentService.deleteStudent(id);
       return new ResponseDTO(Constants.DELETED,200,"Student deleted successfully",id);
    }
    
    @GetMapping("/count/{id}")
    public ResponseDTO retrieveStudentCountBySchool(@PathVariable final Long id) {
        return new ResponseDTO(Constants.COUNT,200,"Student Count successfully",this.studentService.countStudentBySchool(id));
    }

    @GetMapping("/pagination")
    public ResponseDTO getStudentPage(@RequestParam final int index, @RequestParam final int size,
            @RequestParam final String field ) {
        return new ResponseDTO(Constants.PAGINATION,200,"Student pagination successfully",this.studentService.getStudentPage(index,size,field));
    }
}
