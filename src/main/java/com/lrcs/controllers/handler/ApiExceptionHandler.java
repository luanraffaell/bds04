package com.lrcs.controllers.handler;

import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.lrcs.services.exceptions.BusinessException;
import com.lrcs.services.exceptions.EntityNotFoundException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private MessageSource messageSource;
	

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Throwable cause = ex.getCause();
		if(cause instanceof PropertyBindingException) {
			return handlePropertyBindingException((PropertyBindingException)cause, headers, status, request);
		}else if(cause instanceof InvalidFormatException) {
			return handleInvalidFormatException((InvalidFormatException)cause, headers, status, request);
		}
		ProblemType problem = ProblemType.UNRECOGNIZABLE_MESSAGE;
		String path = getUri(request);
		String message ="Invalid request body, please correct the error and try again";
		StandardError error = new StandardError(status.value(), problem.getTitle(), message, path);
		return handleExceptionInternal(ex, error, headers, status, request);
	}
	
	
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ProblemType problem = ProblemType.INVALID_DATA;
		String path = getUri(request);
		String detail = "One or more fields are invalid. Fill in correctly and try again.";
		StandardFieldErrors errors = new StandardFieldErrors(status.value(), problem.getTitle(), detail, path);
		BindingResult bindingResult = ex.getBindingResult();
		List<StandardFieldErrors.FieldError> fieldErrors = bindingResult.getFieldErrors()
				.stream().map(fildErrors -> {
					String message = messageSource.getMessage(fildErrors, Locale.US);
					return new StandardFieldErrors.FieldError(fildErrors.getField(), message);
				}).collect(Collectors.toList());
		errors.setErrors(fieldErrors);
		return handleExceptionInternal(ex, errors, headers, status, request);
	}



	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		String path = getUri(request);
		ProblemType problem = ProblemType.RESOURCE_NOT_FOUND;
		String message = String.format("The resource '%s', which you tried to access, does not exist.", path);
		StandardError error = new StandardError(status.value(), problem.getTitle(), message, path);
		return handleExceptionInternal(ex, error, headers, status, request);
	}



	private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String path = getUri(request);
		String fieldError = ex.getPath().stream().map(x -> x.getFieldName()).collect(Collectors.joining("."));
		ProblemType problem = ProblemType.UNRECOGNIZABLE_MESSAGE;
		String message = String.format("Property '%s' does not exist. Correct or remove this property and try again.", fieldError);
		StandardError error = new StandardError(status.value(), problem.getTitle(), message, path);
		
		return handleExceptionInternal(ex, error, headers, status, request);
	}
	
	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String path = getUri(request);
		String fieldError = ex.getPath().stream().map(x -> x.getFieldName()).collect(Collectors.joining("."));
		ProblemType problem = ProblemType.UNRECOGNIZABLE_MESSAGE;
		String message = String.format("Property '%s' received value '%s', which is of an invalid type."
				+ " Correct and enter a value compatible with type '%s'.", fieldError,ex.getValue(),ex.getTargetType().getSimpleName());
		StandardError error = new StandardError(status.value(), problem.getTitle(), message, path);
		
		return handleExceptionInternal(ex, error, headers, status, request);
	}
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request){
		HttpStatus status = HttpStatus.NOT_FOUND;
		ProblemType problem = ProblemType.NOT_FOUND;
		String requestURI = ((ServletWebRequest)request).getRequest().getRequestURI();
		StandardError error = new StandardError(status.value(), problem.getTitle(), ex.getMessage(), requestURI);
		return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
		
	}
	
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<?> handleBusinessException(BusinessException ex, WebRequest request){
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ProblemType problem = ProblemType.BAD_REQUEST;
		String requestURI = ((ServletWebRequest)request).getRequest().getRequestURI();
		StandardError error = new StandardError(status.value(), problem.getTitle(), ex.getMessage(), requestURI);
		return handleExceptionInternal(ex, error, new HttpHeaders(), status, request);
		
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		if(body == null) {
			StandardError error = new StandardError();
			String requestURI = ((ServletWebRequest)request).getRequest().getRequestURI();
			error.setError(status.getReasonPhrase());
			error.setStatus(status.value());
			error.setPath(requestURI);
			error.setMessage(ex.getMessage());
			error.setTimeStamp(Instant.now());
			body = error;
		}
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
	private String getUri(WebRequest request) {
		return ((ServletWebRequest)request).getRequest().getRequestURI();
	}
}
