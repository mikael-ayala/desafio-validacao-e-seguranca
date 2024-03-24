package com.devsuperior.demo.services;

import com.devsuperior.demo.dto.EventDTO;
import com.devsuperior.demo.entities.City;
import com.devsuperior.demo.entities.Event;
import com.devsuperior.demo.repositories.CityRepository;
import com.devsuperior.demo.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private CityRepository cityRepository;

    @Transactional(readOnly = true)
    public Page<EventDTO> findAll(Pageable pageable) {
        Page<Event> events = eventRepository.findAll(pageable);
        return events.map(EventDTO::new);
    }

    @Transactional
    public EventDTO insert(EventDTO eventDTO) {
        City city = cityRepository.getReferenceById(eventDTO.getCityId());
        Event event = new Event(null, eventDTO.getName(), eventDTO.getDate(), eventDTO.getUrl(), city);
        event = eventRepository.save(event);
        return new EventDTO(event);
    }
}
