package com.cg.response;

import org.springframework.http.HttpStatus;

//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

	private T data;
	private String message;
	private HttpStatus httpStatus;
	private Integer httpStatusValue;

	public ApiResponse() {
		// TODO Auto-generated constructor stub
	}

	public ApiResponse(String msg) {
		this.message = msg;
	}

	public ApiResponse(String message, HttpStatus httpStatus, Integer httpStatusValue) {
		super();
		this.message = message;
		this.httpStatus = httpStatus;
		this.httpStatusValue = httpStatusValue;
	}

	public ApiResponse(T data, String message, HttpStatus httpStatus, Integer httpStatusValue) {
		super();
		this.data = data;
		this.message = message;
		this.httpStatus = httpStatus;
		this.httpStatusValue = httpStatusValue;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
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
