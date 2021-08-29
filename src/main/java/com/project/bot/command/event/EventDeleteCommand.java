package com.project.bot.command.event;

import com.project.bot.command.Command;
import com.project.bot.command.CommandFullOperation;
import com.project.bot.model.event.Event;
import com.project.bot.model.event.WillingDate;
import com.project.bot.service.EventService;
import com.project.bot.service.ParticipantService;
import com.project.bot.service.WillingDateService;
import com.project.bot.util.Parser;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage.SendMessageBuilder;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class EventDeleteCommand implements Command {

  @Autowired
  private Parser parser;

  @Autowired
  private EventService service;

  @Autowired
  private WillingDateService willingDateService;

  @Autowired
  private ParticipantService participantService;

  @Override
  public String execute(Update update, SendMessage.SendMessageBuilder messageBuilder) {
    Long eventId;
    try {
      eventId = Long.parseLong(parser.parsePlaceholders(CommandFullOperation.EVENT_DEL, update.getMessage().getText())
          .stream()
          .findFirst()
          .orElse("Not found"));
    } catch (NumberFormatException e) {
      return "\uD83D\uDFE0 Некорректный номер события. Пожалуйста, введите заново.";
    }

    Event toBeDeleted = service.getById(eventId);

    if (toBeDeleted == null) {
      return "\uD83D\uDFE0 Некорректный номер события. Пожалуйста, введите заново.";
    }

    if (toBeDeleted.getChatId().equals(update.getMessage().getChatId())) {
      return "\uD83D\uDFE0 Некорректный номер события. Пожалуйста, введите заново.";
    }

    service.delete(toBeDeleted);

    List<WillingDate> willingDates = willingDateService.getAllByEventId(eventId);

    if (!willingDates.isEmpty()) {
      willingDates.forEach(it -> willingDateService.delete(it));
    }

    return "\uD83D\uDD34 Событие " + toBeDeleted.getName() + " удалено :(((( .";
  }
}
