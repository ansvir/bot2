package com.project.bot.service;

import com.project.bot.model.event.WillingDate;
import com.project.bot.model.event.WillingDatePK;
import com.project.bot.repository.WillingDateRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WillingDateService {

  @Autowired
  private WillingDateRepository willingDateRepository;

  public WillingDate save(WillingDate willingDate) {
    return willingDateRepository.save(willingDate);
  }

  public List<WillingDate> getAll() {
    return willingDateRepository.findAll();
  }

  public WillingDate getById(Long id) {
    return willingDateRepository.getById(id);
  }

  public Optional<WillingDate> getByEventIdAndParticipantId(Long eventId, Long participantId) {
    return willingDateRepository.getByEventIdAndParticipantId(eventId, participantId);
  }

  public List<WillingDate> getAllByEventId(Long id) {
    return willingDateRepository.getByEventId(id);
  }

  public void delete(WillingDate willingDate) {
    willingDateRepository.delete(willingDate);
  }

  public void deleteByEventIdAndParticipantId(Long eventId, Long participantId) {
    willingDateRepository.deleteByEventIdAndParticipantId(eventId, participantId);
  }

}
