package com.project.bot.initializer;

import com.project.bot.model.event.Event;
import com.project.bot.model.event.Participant;
import com.project.bot.model.event.WillingDate;
import com.project.bot.model.event.WillingDatePK;
import com.project.bot.service.EventService;
import com.project.bot.service.ParticipantService;
import com.project.bot.service.WillingDateService;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabasePopulator implements ApplicationRunner {

  @Autowired
  private EventService eventService;

  @Autowired
  private ParticipantService participantService;

  @Autowired
  private WillingDateService willingDateService;

  public void run(ApplicationArguments args) {

    Participant participant = participantService.create(
        new Participant(
        -1L, "Default participant", null
    ));

    Event event = eventService.save(
        new Event(-1L, "Default event", null, null, null, Set.of(participant))
    );

    willingDateService.save(
        new WillingDate(
            new WillingDatePK(
                event.getId(),
                participant.getId()
            ),
            event,
            participant,
            "Даты нет")
    );
  }
}
