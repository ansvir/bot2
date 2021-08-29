package com.project.bot.repository;

import com.project.bot.model.event.Participant;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
  Optional<Participant> getByTelegramId(Long id);
}
