package com.project.bot.command.event;

import com.project.bot.command.Command;
import com.project.bot.command.CommandFullOperation;
import com.project.bot.model.event.Event;
import com.project.bot.model.event.Participant;
import com.project.bot.service.EventService;
import com.project.bot.service.ParticipantService;
import com.project.bot.util.Parser;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage.SendMessageBuilder;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class EventEditDescriptionCommand implements Command {

  @Autowired
  private EventService eventService;

  @Autowired
  private Parser parser;

  @Override
  public String execute(Update update, SendMessage.SendMessageBuilder messageBuilder) {
    List<String> parameters = parser.parsePlaceholders(CommandFullOperation.EVENT_EDIT_DESC, update.getMessage().getText());
    Long eventId;
    try {
      eventId = Long.parseLong(parameters.get(0));
    } catch (NumberFormatException e) {
      return "Неправильный номер события. Внимательней, плиз";
    }

    Event event = eventService.getById(eventId);

    if (event == null) {
      return "Неправильный номер события. Внимательней, плиз";
    }

    event.setDescription(parameters.get(1));

    Event saved = eventService.save(event);

    return "Описание события " + saved.getName() + " обновлено.";
  }
}
