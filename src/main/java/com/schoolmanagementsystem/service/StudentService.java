package com.schoolmanagementsystem.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.schoolmanagementsystem.entity.Student;
import com.schoolmanagementsystem.exception.UserNotFoundException;
import com.schoolmanagementsystem.repository.StudentRepository;

@Service
public class StudentService {
	
	@Autowired
	private StudentRepository studentRepository;
	
	public Student createStudent(final Student student) {
        return this.studentRepository.save(student);
    }
	
	public List<Student> getAllStudents(){
		return this.studentRepository.findAll();
	}
	
	public Student getStudentById(final Long id) {
	return this.studentRepository.findById(id)
			.orElseThrow(() -> new UserNotFoundException("Student not found for this id : " + id));
	}
	 
	public String updateStudent(final Long id,final Student studentDetails) {
		Student student = studentRepository.findById(id)
				 .orElseThrow(() -> new UserNotFoundException("Student not found for this id : " + id));
			studentDetails.setId(id);
			this.studentRepository.save(student);
		return "Student id:"+id+" "+"successfully updated";
	}
	
	public String deleteStudent(final Long id) {
		Student student = studentRepository.findById(id)
		      .orElseThrow(() -> new UserNotFoundException("Student not found for this id : " + id));
	     this.studentRepository.delete(student);
	return "Student id:"+id+" "+"successfully deleted";
	} 
	
	public Long countStudentBySchool(final Long id) {
       return this.studentRepository.countBySchoolId(id);
	}
	
	public Page <Student> getStudentPage(final int pageIndex,final int pageSize,final String field){
		 Sort sort = Sort.by(Sort.Direction.ASC,field);
		 Pageable pageable = PageRequest.of(pageIndex,pageSize,sort);
		 return this.studentRepository.findAll(pageable);
	 }
}
