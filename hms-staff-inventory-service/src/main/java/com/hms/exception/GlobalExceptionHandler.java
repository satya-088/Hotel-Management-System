package com.hms.exception;


import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
	private Logger log=LoggerFactory.getLogger(GlobalExceptionHandler.class);
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		log.error("Exception occurred: {}", ex.getMessage(), ex);
		  ErrorResponse stResponse=new ErrorResponse(ex.getMessage(),
				   null,
				   		 "500 Internal server Error");
	 
	   ResponseEntity<Object> responseEntity=new ResponseEntity<>(stResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	    return responseEntity;
	}
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		  log.warn("Validation error occurred: {}", ex.getMessage());
		BindingResult b=ex.getBindingResult();
		List<FieldError> fieldErrors=b.getFieldErrors();		
		Stream<FieldError> stream=fieldErrors.stream();
		Function<FieldError, String> fun=((errors)->errors.getField()+":"+errors.getDefaultMessage());
		Stream<String> map=stream.map(fun);
		List<String> list=map.toList();
		ErrorResponse response=new ErrorResponse("validation Error",list.toString(),"HttpStatus.INTERNAL_SERVER_ERROR");
		return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
	} 

			

}
