package com.lrcs.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lrcs.models.City;
import com.lrcs.models.Event;
import com.lrcs.models.DTO.EventDTO;
import com.lrcs.repositories.CityRepository;
import com.lrcs.repositories.EventRepository;
import com.lrcs.services.exceptions.EntityNotFoundException;

@Service
public class EventService {

	private static final String CITY_NOT_FOUND = "City ​​with id %s not found";
	
	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private CityRepository cityRepository;
	
	public List<EventDTO> findAll(){
		return eventRepository.findAll().stream().map(x -> new EventDTO(x)).collect(Collectors.toList());
	}
	
	@Transactional
	public EventDTO insert(EventDTO eventDTO) {
		Event event = dtoToEvent(eventDTO);
		
		return new EventDTO(eventRepository.save(event));
	}
	
	private Event dtoToEvent(EventDTO eventDTO) {
		City city = findCity(eventDTO.getCityId());
		Event event = new Event();
		event.setName(eventDTO.getName());
		event.setDate(eventDTO.getDate());
		event.setCity(city);
		event.setUrl(eventDTO.getUrl());
		
		return event;
	}
	
	private City findCity(Long id) {
		return cityRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format(CITY_NOT_FOUND,
				id)));
	}
}
