package com.schoolmanagementsystem.exception;

import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.schoolmanagementsystem.dto.MessageResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<MessageResponse> handleCustomException(UserNotFoundException ex, WebRequest request) {
		MessageResponse messageResponse = new MessageResponse(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
    }
}
