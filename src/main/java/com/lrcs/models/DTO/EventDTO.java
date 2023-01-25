package com.lrcs.models.DTO;

import java.time.LocalDate;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.lrcs.models.Event;

public class EventDTO {

	private Long id;
	@NotBlank
	private String name;
	@FutureOrPresent
	private LocalDate date;
	private String url;
	@NotNull
	private Long cityId;
	
	public EventDTO(Event event) {
		id = event.getId();
		name = event.getName();
		date = event.getDate();
		url = event.getUrl();
		cityId = event.getCity().getId();
	}
	
	public EventDTO(Long id, String name, LocalDate date, String url,Long cityId) {
		super();
		this.id = id;
		this.name = name;
		this.date = date;
		this.url = url;
		this.cityId = cityId;
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

	public Long getCityId() {
		return cityId;
	}

	public void setCityDTO(CityDTO cityDTO) {
		this.cityId = cityDTO.getId();
	}
	
	
	
}
