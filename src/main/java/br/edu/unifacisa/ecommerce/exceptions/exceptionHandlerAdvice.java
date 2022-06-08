package br.edu.unifacisa.ecommerce.exceptions;

import javax.validation.ValidationException;

import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import br.edu.unifacisa.ecommerce.comnon.ApiResponse;


@ControllerAdvice
public class exceptionHandlerAdvice {
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ApiResponse> conflict(DataIntegrityViolationException ex){
		String message = getMostSpecificMessage(ex);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse(false, message), HttpStatus.CONFLICT);
	}
	
	
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<ApiResponse> validationException(ValidationException ex){
		String message = ex.getMessage();
		
		return new ResponseEntity<ApiResponse>(new ApiResponse(false, message), HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ApiResponse> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex){
		ex.printStackTrace();
		
		String message = ex.getMessage();
		return new ResponseEntity<ApiResponse>(new ApiResponse(false, message), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse> unhandledExceptions(Exception ex){
		String message = NestedExceptionUtils.getMostSpecificCause(ex).getMessage();
		
		ex.printStackTrace();
		
		return new ResponseEntity<ApiResponse>(new ApiResponse(false, message), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	private String getMostSpecificMessage(DataIntegrityViolationException ex) {
		String message = NestedExceptionUtils.getMostSpecificCause(ex).getMessage();
		
		if(message.contains("Detail:")) {
			message = message.substring(message.indexOf("Detail:")+"Detail:".length());
		}
		
		return message;
	}
}
