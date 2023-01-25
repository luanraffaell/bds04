package com.lrcs.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lrcs.models.DTO.EventDTO;
import com.lrcs.services.EventService;

@RestController
@RequestMapping("/events")
public class EventController {
	
	@Autowired
	private EventService eventService;
	
	@GetMapping
	public ResponseEntity<List<EventDTO>> findAll(){
		List<EventDTO> cities = eventService.findAll();
		return ResponseEntity.ok().body(cities);
	}
	
	@PostMapping
	public ResponseEntity<EventDTO> insert(@RequestBody @Valid EventDTO event){
		event = eventService.insert(event);
		return ResponseEntity.status(HttpStatus.CREATED).body(event);
	}
}
