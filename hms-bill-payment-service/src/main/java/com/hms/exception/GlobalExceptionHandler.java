package com.hms.exception;

import java.time.LocalDateTime;
import java.util.List;
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
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	 @ExceptionHandler(UserException.class)
	    public ResponseEntity<?> handleIllegalArgumentException(UserException ex,WebRequest request) {
		 	
		 log.error(ex.getMessage());
		 	
	        ErrorResponse err = new ErrorResponse(ex.getMessage(),request.getDescription(false),"500 Internal Server Error", LocalDateTime.now());
	        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
	    }
	 
	 @Override
	 protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
				HttpHeaders headers, HttpStatusCode status, WebRequest request) {
			BindingResult bindingResult = ex.getBindingResult();
		    Stream<FieldError> stream = bindingResult.getFieldErrors().stream();
		    Stream<String> map = stream.map(error -> error.getField() + ": " +error.getDefaultMessage());
		    List<String> list = map.toList();	            
		    ErrorResponse exceptionResponse = new ErrorResponse( "Validation Failed",list.toString(), "400 Bad Request",LocalDateTime.now());
		    return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	   
     }
	 
	 
}
