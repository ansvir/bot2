package com.project.bot.command.event;

import com.project.bot.command.Command;
import com.project.bot.command.CommandFullOperation;
import com.project.bot.model.event.Event;
import com.project.bot.model.event.Participant;
import com.project.bot.service.EventService;
import com.project.bot.service.ParticipantService;
import com.project.bot.util.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage.SendMessageBuilder;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class EventRemoveMeCommand implements Command {

  @Autowired
  private Parser parser;

  @Autowired
  private EventService eventService;

  @Autowired
  private ParticipantService participantService;

  @Override
  public String execute(Update update, SendMessage.SendMessageBuilder messageBuilder) {
    Long eventId;
    try {
      eventId = Long.parseLong(parser.parsePlaceholders(CommandFullOperation.EVENT_NEW, update.getMessage().getText())
          .stream()
          .findFirst()
          .orElse("Not found"));
    } catch (NumberFormatException e) {
      return "Некорректный номер события. Пожалуйста, введите заново.";
    }

    Event event = eventService.getById(eventId);

    if (event == null) {
      return "Некорректный номер события. Пожалуйста, введите заново.";
    }

    Participant participant = participantService.getByTelegramId(
        update.getMessage().getFrom().getId()
    )
        .orElse(null);

    if (participant == null) {
      return "Вы не включены в данное событие!!!";
    }

    event.getParticipants().remove(participant);

    Event saved = eventService.save(event);

    return "Вы удалены из события " + saved.getName() + "!";
  }
}
