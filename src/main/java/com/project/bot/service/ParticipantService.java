package com.project.bot.service;

import com.project.bot.model.event.Participant;
import com.project.bot.repository.ParticipantRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParticipantService {

  @Autowired
  private ParticipantRepository participantRepository;

  public Participant create(Participant participant) {
    return participantRepository.save(participant);
  }

  public Participant getById(Long id) {
    return participantRepository.getById(id);
  }

  public Optional<Participant> getByTelegramId(Long id) {
    return participantRepository.getByTelegramId(id);
  }

  public void delete(Participant participant) {
    participantRepository.delete(participant);
  }

}
