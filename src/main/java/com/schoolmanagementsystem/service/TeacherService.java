package com.schoolmanagementsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
	 
	public Teacher updateTeacher(final Long id,final Teacher teacherDetails) {
		final Teacher teacher = this.teacherRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("Teacher not found for this id : " + id));
			teacherDetails.setId(id);
			return this.teacherRepository.save(teacher);	
	}
	
	public void deleteTeacher(final Long id) {
		final Teacher teacher = this.teacherRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("Teacher not found for this id : " + id));
	     this.teacherRepository.delete(teacher);
	} 
	
	public Long countTeacherBySchool(final Long id) {
		 return this.teacherRepository.countBySchoolId(id);
	}
	
	public Page<Teacher> getTeacherPage(final int index, final int size, final String field) {
		final Sort sort = Sort.by(Sort.Direction.ASC, field);
		final Pageable pageable = PageRequest.of(index, size, sort);
		return this.teacherRepository.findAll(pageable);
	}
}