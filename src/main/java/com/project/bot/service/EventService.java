package com.project.bot.service;

import com.project.bot.model.event.Event;
import com.project.bot.model.event.Participant;
import com.project.bot.repository.EventRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventService {

  @Autowired
  private EventRepository eventRepository;

  public Event save(Event event) {
    return eventRepository.save(event);
  }

  public List<Event> getAll() {
    return eventRepository.findAll();
  }

  public Event getById(Long id) {
    return eventRepository.getById(id);
  }

  public void delete(Event event) {
    eventRepository.delete(event);
  }

}
