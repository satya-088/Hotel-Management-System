package com.hms.exception;

import java.time.LocalDateTime;

public class ErrorResponse {
	
	private LocalDateTime date;
	private String error;
    private String message;
    private String details;
    

	public ErrorResponse(LocalDateTime date,String error, String message,String details) {
		super();
		this.date = date;
		this.error = error;
		this.message = message;
		this.details = details;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
    
	
	
    

}
