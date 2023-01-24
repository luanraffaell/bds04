package com.lrcs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lrcs.models.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

}
