package com.nishan.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

	@ExceptionHandler(UserException.class)
	public ResponseEntity<ErrorResponse> userExceptionHandler(UserException exception){
		return new ResponseEntity<ErrorResponse>(new ErrorResponse(exception.getMessage(),false,HttpStatus.BAD_REQUEST),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException exception){
		return new ResponseEntity<ErrorResponse>(new ErrorResponse(exception.getMessage(),false,HttpStatus.BAD_REQUEST),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(CategoryException.class)
	public ResponseEntity<ErrorResponse> categoryExceptionHandler(CategoryException exception){
		return new ResponseEntity<ErrorResponse>(new ErrorResponse(exception.getMessage(),false,HttpStatus.BAD_REQUEST),HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ErrorResponse> customExceptionHandler(CustomException exception){
		return new ResponseEntity<ErrorResponse>(new ErrorResponse(exception.getMessage(),false,HttpStatus.BAD_REQUEST),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> otherExceptionHandler(Exception exception){
		return new ResponseEntity<ErrorResponse>(new ErrorResponse(exception.getMessage(),false,HttpStatus.BAD_REQUEST),HttpStatus.BAD_REQUEST);
	}
}
