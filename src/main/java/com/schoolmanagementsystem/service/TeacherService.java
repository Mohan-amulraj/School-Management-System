package com.schoolmanagementsystem.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.schoolmanagementsystem.entity.Teacher;
import com.schoolmanagementsystem.exception.UserNotFoundException;
import com.schoolmanagementsystem.repository.TeacherRepository;

@Service
public class TeacherService {
	
	@Autowired
	private TeacherRepository teacherRepository;
	
	public Teacher createTeacher(final Teacher teacher) {
        return this.teacherRepository.save(teacher);
    }
	
	public List<Teacher> getAllTeacher(){
		return this.teacherRepository.findAll();
	}
	
	public Teacher getTeacherById(final Long id) {
	return this.teacherRepository.findById(id)
			.orElseThrow(() -> new UserNotFoundException("Teacher not found for this id : " + id));
	}
	 
	public String updateTeacher(final Long id,final Teacher teacherDetails) {
		Teacher teacher = teacherRepository.findById(id)
				 .orElseThrow(() -> new UserNotFoundException("Teacher not found for this id : " + id));
			teacherDetails.setId(id);
			this.teacherRepository.save(teacher);
		return "Teacher id:"+id+" "+"successfully updated";
	}
	
	public String deleteTeacher(final Long id) {
		Teacher teacher = teacherRepository.findById(id)
		      .orElseThrow(() -> new UserNotFoundException("Teacher not found for this id : " + id));
	     this.teacherRepository.delete(teacher);
	return "Teacher id:"+id+" "+"successfully deleted";
	} 
	
	public Long countTeacherBySchool(final Long id) {
        return this.teacherRepository.countBySchoolId(id);
	}
}