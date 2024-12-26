package com.schoolmanagementsystem.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.schoolmanagementsystem.entity.Question;
import com.schoolmanagementsystem.entity.Student;
import com.schoolmanagementsystem.entity.StudentAnswer;
import com.schoolmanagementsystem.exception.UserNotFoundException;
import com.schoolmanagementsystem.repository.StudentAnswerRepository;

@Service
public class StudentAnswerService {
	
	@Autowired
	private StudentAnswerRepository studentAnswerRepository;
		
	public StudentAnswer createStudentAnswer(final StudentAnswer studentAnswer) {
        return this.studentAnswerRepository.save(studentAnswer);
    }
	
	public List<StudentAnswer> getAllStudentAnswer(){
		return this.studentAnswerRepository.findAll();
	}
	
	public StudentAnswer getStudentAnswerById(final Long id) {
	return this.studentAnswerRepository.findById(id)
			.orElseThrow(() -> new UserNotFoundException("StudentAnswer not found for this id : " + id));
	}
	 
	public String updateStudentAnswer(final Long id,final StudentAnswer studentAnswerDetails) {
		StudentAnswer studentAnswer = studentAnswerRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("StudentAnswer not found for this id : " + id));
			studentAnswerDetails.setId(id);
			this.studentAnswerRepository.save(studentAnswer);
		return "StudentAnswer id:"+id+" "+"successfully updated";
	}
	
	public String deleteStudentAnswer(final Long id) {
		StudentAnswer studentAnswer = studentAnswerRepository.findById(id)
		      .orElseThrow(() -> new UserNotFoundException("StudentAnswer not found for this id : " + id));
	     this.studentAnswerRepository.delete(studentAnswer);
	return "StudentAnswer id:"+id+" "+"successfully deleted";
	} 
	

	public int studentMarks(List<StudentAnswer> answers) {
		int mark = 0;
		for(StudentAnswer studentAnswer : answers) {
			Question question = studentAnswer.getQuestion();
			if(question!=null && question.getCrt_answer()!=null && studentAnswer.getAnswer()!=null) {
				if(studentAnswer.getAnswer().equals(question.getCrt_answer())) {
					mark++;
				}
			}
		}
		return mark;
	}
	
	public Map<Long, String> allStudentMarks(){
		List<StudentAnswer> studentAnswers = studentAnswerRepository.findAll();
		List<Long> studentIds = studentAnswers.stream().map(StudentAnswer::getStudent).map(Student::getId).toList();
	  Map<Long,String> allStudents = new HashMap<>();
	   for(Long studentId : studentIds) {
		   String mark = studentMark(studentId,studentAnswers);
		   allStudents.put(studentId,mark); 
	   }
	   return allStudents;
	}
	
	public String studentMark(final Long id,final List<StudentAnswer>answers) {
		int mark = studentMarks(answers.stream().filter(stud -> Objects.equals(stud.getStudent().getId(), id)).toList());
		return "Student Mark : " + mark;
	}
}
