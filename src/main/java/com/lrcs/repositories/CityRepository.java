package com.lrcs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lrcs.models.City;

public interface CityRepository extends JpaRepository<City, Long> {

}
