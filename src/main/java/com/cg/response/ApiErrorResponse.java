package com.cg.response;

import org.springframework.http.HttpStatus;

public class ApiErrorResponse {

	private String message;
	private HttpStatus httpStatus;
	private Integer httpStatusValue;

	public ApiErrorResponse() {
		// TODO Auto-generated constructor stub
	}

	public ApiErrorResponse(String message, HttpStatus httpStatus, Integer httpStatusValue) {
		super();
		this.message = message;
		this.httpStatus = httpStatus;
		this.httpStatusValue = httpStatusValue;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public Integer getHttpStatusValue() {
		return httpStatusValue;
	}

	public void setHttpStatusValue(Integer httpStatusValue) {
		this.httpStatusValue = httpStatusValue;
	}

}
