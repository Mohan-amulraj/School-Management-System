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
	 
	public Student updateStudent(final Long id,final Student studentDetails) {
		final Student student = studentRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("Student not found for this id : " + id));
			studentDetails.setId(id);
			return this.studentRepository.save(student);
	}
	
	public void deleteStudent(final Long id) {
		final Student student = studentRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("Student not found for this id : " + id));
	     this.studentRepository.delete(student);
	} 
	
	public Long countStudentBySchool(final Long id) {
       return this.studentRepository.countBySchoolId(id);
	}
	
	public Page<Student> getStudentPage(final int index,final int size,final String field){
		final Sort sort = Sort.by(Sort.Direction.ASC,field);
		 final Pageable pageable = PageRequest.of(index,size,sort);
		 return this.studentRepository.findAll(pageable);
	 }
}
