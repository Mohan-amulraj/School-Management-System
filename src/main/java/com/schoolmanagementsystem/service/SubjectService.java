package com.schoolmanagementsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.schoolmanagementsystem.entity.Subject;
import com.schoolmanagementsystem.exception.UserNotFoundException;
import com.schoolmanagementsystem.repository.SubjectRepository;

@Service
public class SubjectService {

	@Autowired
	private SubjectRepository subjectRepository;

	public Subject createSubject(final Subject subject) {
		return this.subjectRepository.save(subject);
	}

	public List<Subject> getAllSubject() {
		return this.subjectRepository.findAll();		
	}

	public Subject getSubjectById(final Long id) {
		return this.subjectRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("Subject not found for this id : " + id));
	}

	public Subject updateSubject(final Long id, final Subject subjectDetails) {
		final Subject subject = subjectRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("Subject not found for this id : " + id));
		subjectDetails.setId(id);
		return this.subjectRepository.save(subject);
	}

	public void deleteSubject(final Long id) {
		final Subject subject = subjectRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("Subject not found for this id : " + id));
		this.subjectRepository.delete(subject);
	}
}