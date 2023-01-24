package com.lrcs.models.DTO;

import java.util.ArrayList;
import java.util.List;

import com.lrcs.models.City;

public class CityDTO {
	
	private Long id;
	private String name;
	private List<EventDTO> events = new ArrayList<>();
	
	public CityDTO(City city) {
		id = city.getId();
		name = city.getName();
		city.getEnvents().forEach(x -> events.add(new EventDTO(x)));
	}
	
	public CityDTO(Long id, String name, List<EventDTO> events) {
		this.id = id;
		this.name = name;
		this.events = events;
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

	public List<EventDTO> getEvents() {
		return events;
	}

	
	
}
