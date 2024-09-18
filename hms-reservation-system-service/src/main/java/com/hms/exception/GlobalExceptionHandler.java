package com.hms.exception;

import java.time.LocalDateTime;
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
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	// extends ResponseEntityExceptionHandler

	Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> ExceptionHandlerException(Exception e, WebRequest request) {// add
																												// one
																												// parameter
																												// WebRequest
																												// request

		log.error(e.getMessage());
		ErrorResponse response = new ErrorResponse(LocalDateTime.now(), "Not Found", e.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<ErrorResponse>(response, HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		// TODO Auto-generated method stub

		BindingResult bs = ex.getBindingResult();

		// List<String> errors =
		// bs.getFieldErrors().stream().map(n->n.getField()+":"+n.getDefaultMessage()).collect(Collectors.toList());

		List<FieldError> fieldErrors = bs.getFieldErrors();
		Stream<FieldError> stream = fieldErrors.stream();

		Function<FieldError, String> fun = ((n -> n.getField() + ":" + n.getDefaultMessage()));

		Stream<String> map = stream.map(fun);

		List<String> list = map.toList();

		ErrorResponse response = new ErrorResponse(LocalDateTime.now(), "Validation Error", list.toString(),
				request.getDescription(false));

		// return super.handleMethodArgumentNotValid(ex, headers, status, request);

		return new ResponseEntity<Object>(response, HttpStatus.NOT_ACCEPTABLE);
	}
}
