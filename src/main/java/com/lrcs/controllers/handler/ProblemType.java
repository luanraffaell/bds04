package com.lrcs.controllers.handler;

public enum ProblemType {
	NOT_FOUND("Not found"),
	BAD_REQUEST("Bad request"),
	UNRECOGNIZABLE_MESSAGE("Unrecognizable message"),
	RESOURCE_NOT_FOUND("Resource not found"),
	INVALID_DATA("Invalid data");
	
	private final String title;

	private ProblemType(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
	
	
}
