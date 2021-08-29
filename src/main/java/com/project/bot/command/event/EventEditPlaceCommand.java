package com.project.bot.command.event;

import com.project.bot.command.Command;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage.SendMessageBuilder;
import org.telegram.telegrambots.meta.api.objects.Update;

import com.project.bot.command.Command;
import com.project.bot.command.CommandFullOperation;
import com.project.bot.model.event.Event;
import com.project.bot.service.EventService;
import com.project.bot.util.Parser;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class EventEditPlaceCommand implements Command {

  @Autowired
  private EventService eventService;

  @Autowired
  private Parser parser;

  @Override
  public String execute(Update update, SendMessage.SendMessageBuilder messageBuilder) {
    List<String> parameters = parser
        .parsePlaceholders(CommandFullOperation.EVENT_EDIT_PLACE, update.getMessage().getText());
    Long eventId;
    try {
      eventId = Long.parseLong(parameters.get(0));
    } catch (NumberFormatException e) {
      return "\uD83D\uDFE0 Неправильный номер события. Внимательней, плиз";
    }

    Event event = eventService.getById(eventId);

    if (event == null) {
      return "\uD83D\uDFE0 Неправильный номер события. Внимательней, плиз";
    }

    event.setPlace(parameters.get(1));

    Event saved = eventService.save(event);

    return "\uD83D\uDD35 Место события изменено на \""
        + saved.getPlace() +"\".";
  }
}
