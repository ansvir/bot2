package com.project.bot.command.event;

import com.project.bot.command.Command;
import com.project.bot.command.CommandFullOperation;
import com.project.bot.model.event.Event;
import com.project.bot.model.event.Participant;
import com.project.bot.service.EventService;
import com.project.bot.service.ParticipantService;
import com.project.bot.service.WillingDateService;
import com.project.bot.util.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class EventRemoveMeCommand implements Command {

  @Autowired
  private Parser parser;

  @Autowired
  private EventService eventService;

  @Autowired
  private ParticipantService participantService;

  @Autowired
  private WillingDateService willingDateService;

  @Override
  public String execute(Update update, SendMessage.SendMessageBuilder messageBuilder) {
    Long eventId;
    try {
      eventId = Long.parseLong(parser.parsePlaceholders(CommandFullOperation.EVENT_REMOVE_ME, update.getMessage().getText())
          .stream()
          .findFirst()
          .orElse("Not found"));
    } catch (NumberFormatException e) {
      return "\uD83D\uDFE0 Некорректный номер события. Пожалуйста, введите заново.";
    }

    Event event = eventService.getById(eventId);

    if (event == null) {
      return "\uD83D\uDFE0 Некорректный номер события. Пожалуйста, введите заново.";
    }

    Participant participant = participantService.getByTelegramId(
        update.getMessage().getFrom().getId()
    ).orElse(null);

    if (participant == null) {
      return "\uD83D\uDFE0 Вы не включены ни в одно событие!!!";
    }

    event
        .getParticipants()
        .removeIf(
            it -> it.getId().equals(participant.getId()
            )
        );

    Event saved = eventService.save(event);

    willingDateService.deleteByEventIdAndParticipantId(
        eventId,
        participant.getId()
    );

    return "\uD83D\uDD34 Вы удалены из события " + saved.getName() + "!";
  }
}
