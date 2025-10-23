package com.cg.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cg.response.ApiErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<ApiErrorResponse> handleUserExists(UserAlreadyExistsException ex) {
		String message = ex.getMessage();
		ApiErrorResponse response = new ApiErrorResponse();
		response.setMessage(message);
		response.setHttpStatus(HttpStatus.CONFLICT);
		response.setHttpStatusValue(HttpStatus.CONFLICT.value());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(response);

	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiErrorResponse> handleNotFound(ResourceNotFoundException ex) {
		String message = ex.getMessage();
		ApiErrorResponse response = new ApiErrorResponse();
		response.setMessage(message);
		response.setHttpStatus(HttpStatus.NOT_FOUND);
		response.setHttpStatusValue(HttpStatus.NOT_FOUND.value());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}

	@ExceptionHandler(InsufficientBalanceException.class)
	public ResponseEntity<ApiErrorResponse> handleInsufficient(InsufficientBalanceException ex) {
		String message = ex.getMessage();
		ApiErrorResponse response = new ApiErrorResponse();
		response.setMessage(message);
		response.setHttpStatus(HttpStatus.BAD_REQUEST);
		response.setHttpStatusValue(HttpStatus.BAD_REQUEST.value());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidation(MethodArgumentNotValidException ex) {
        String error = ex.getBindingResult().getFieldError().getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }


}
