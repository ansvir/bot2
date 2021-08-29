package com.project.bot.repository;

import com.project.bot.model.event.WillingDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WillingDateRepository extends JpaRepository<WillingDate, Long> {
  Optional<WillingDate> getByEventIdAndParticipantId(Long eventId, Long participantId);
  List<WillingDate> getByEventId(Long id);
  void deleteByEventIdAndParticipantId(Long eventId, Long participantId);
}
