package com.project.bot.repository;

import com.project.bot.model.event.Event;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
  List<Event> findAllByChatId(Long id);
}
