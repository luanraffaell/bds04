package com.lrcs.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lrcs.models.City;
import com.lrcs.models.Event;
import com.lrcs.models.DTO.CityDTO;
import com.lrcs.repositories.CityRepository;

@Service
public class CityService {

	@Autowired
	private CityRepository cityRepository;
	
	public List<CityDTO> findAll(){
		return cityRepository.findAll().stream().map(x -> new CityDTO(x)).collect(Collectors.toList());
	}
	
	@Transactional
	public CityDTO insert(CityDTO cityDTO) {
		City city = dtoToCity(cityDTO);
		
		return new CityDTO(cityRepository.save(city));
	}
	
	private City dtoToCity(CityDTO cityDTO) {
		City city = new City();
		city.setName(cityDTO.getName());
		
		if(cityDTO.getEvents() != null) {
			cityDTO.getEvents().forEach(x -> city.addEvents(new Event(x.getId(), x.getName(), x.getDate(), x.getUrl(), city)));
		}
		
		return city;
	}
}
