package com.project.bot.command.event;

import com.project.bot.command.Command;
import com.project.bot.command.CommandFullOperation;
import com.project.bot.config.BotConfig;
import com.project.bot.model.event.Event;
import com.project.bot.model.event.Participant;
import com.project.bot.service.EventService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class EventListCommand implements Command {

  @Autowired
  private EventService service;

  @Autowired
  private BotConfig botConfig;

  @Override
  public String execute(Update update, SendMessage.SendMessageBuilder messageBuilder) {

    List<Event> events = service.getAll();
    if (events.isEmpty()) {
      return "Событий пока нет. Попробуйте \""
          + botConfig.getCommandDelimiter()
          + CommandFullOperation.EVENT_NEW.getName()
          + "\".";
    }

    StringBuilder result = new StringBuilder("События:");
    for (Event event : events) {
      result.append(
          "\n"
              + event.getId()
              + ". " + event.getName()
              + "\n\t" + event.getDescription()
              + "\n\t" + event.getDate().toString()
              + "\n\tУчастники: ");
      if (event.getParticipants().isEmpty()) {
        result.append("пока нет.");
      } else {
        for (Participant part : event.getParticipants()) {
          result.append(
              part.getName()
                  +" ("
                  + part.getUsername()
                  + ") - "
                  + part.getWillingDate().toString()
                  + ", "
          );
        }
        result = new StringBuilder(result.substring(0, result.length() - 2));
        result.append(".");
      }
    }
    return result.toString();
  }
}
