package com.lrcs.controllers.handler;

import java.util.ArrayList;
import java.util.List;

public class StandardFieldErrors extends StandardError {
	private static final long serialVersionUID = 1L;
	
	private List<FieldError> errors = new ArrayList<>();
	
	public StandardFieldErrors(Integer status, String error, String message, String path) {
		super(status,error,message,path);
	}
	
	static class FieldError{
		private String field;
		private String message;
		
		public FieldError() {		
		}

		public FieldError(String field, String message) {
			super();
			this.field = field;
			this.message = message;
		}

		public String getField() {
			return field;
		}

		public void setField(String field) {
			this.field = field;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
		
		
	}

	public List<FieldError> getErrors() {
		return errors;
	}

	public void setErrors(List<FieldError> errors) {
		this.errors = errors;
	}
	
	
//	public void addError(String field,String message) {
//		errors.add(new FieldError(field, message));
//	}

}
