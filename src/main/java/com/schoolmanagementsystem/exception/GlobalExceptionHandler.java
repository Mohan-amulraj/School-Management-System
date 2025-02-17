package com.schoolmanagementsystem.exception;

import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import com.schoolmanagementsystem.dto.MessageResponse;
import com.schoolmanagementsystem.dto.ResponseDTO;
import com.schoolmanagementsystem.util.Constants;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(UserNotFoundException.class)//specific exception handling
    public ResponseEntity<MessageResponse> handleCustomException(final UserNotFoundException ex, WebRequest request) {
		final MessageResponse messageResponse = new MessageResponse(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
    }

	@ExceptionHandler(Exception.class)//All other exception handling
    public ResponseEntity<MessageResponse> handleGlobalException(final Exception ex, WebRequest request) {
		final MessageResponse messageResponse = new MessageResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), 
        		new Date(),
        		ex.getMessage(), 
        		request.getDescription(false));
        return new ResponseEntity<>(messageResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
	// Token generate exception handling
	@ExceptionHandler({ IllegalArgumentException.class })
	public ResponseEntity<ResponseDTO> handleAuthenticationException(Exception e,WebRequest request) {
		ResponseDTO dto=new ResponseDTO(Constants.NOTFOUND,HttpStatus.UNAUTHORIZED.value(),e.getMessage(),null);
		return new ResponseEntity<>(dto,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler({ BadCredentialsException.class })
	public ResponseEntity<ResponseDTO> handleAuthenticationException(BadCredentialsException ex,WebRequest request) {
		ResponseDTO response=new ResponseDTO(Constants.FAILURE,HttpStatus.BAD_REQUEST.value(),ex.getMessage(),null);
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler({ UsernameNotFoundException.class })
	public ResponseEntity<ResponseDTO> handleAuthenticationException( UsernameNotFoundException ex,WebRequest request) {
		ResponseDTO response=new ResponseDTO(Constants.FAILURE,HttpStatus.BAD_REQUEST.value(),ex.getMessage(),null);
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
}
