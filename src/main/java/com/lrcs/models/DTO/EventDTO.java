package com.lrcs.models.DTO;

import java.time.LocalDate;

import com.lrcs.models.Event;

public class EventDTO {

	private Long id;
	private String name;
	private LocalDate date;
	private String url;
	
	public EventDTO(Event event) {
		id = event.getId();
		name = event.getName();
		date = event.getDate();
		url = event.getUrl();
	}
	
	public EventDTO(Long id, String name, LocalDate date, String url) {
		super();
		this.id = id;
		this.name = name;
		this.date = date;
		this.url = url;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
}
