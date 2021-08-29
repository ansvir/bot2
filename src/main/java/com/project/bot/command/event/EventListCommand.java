package com.project.bot.command.event;

import com.project.bot.command.Command;
import com.project.bot.command.CommandFullOperation;
import com.project.bot.config.BotConfig;
import com.project.bot.model.event.Event;
import com.project.bot.model.event.Participant;
import com.project.bot.model.event.WillingDate;
import com.project.bot.model.event.WillingDatePK;
import com.project.bot.service.EventService;
import com.project.bot.service.WillingDateService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class EventListCommand implements Command {

  @Autowired
  private EventService eventService;

  @Autowired
  private WillingDateService willingDateService;

  @Autowired
  private BotConfig botConfig;

  @Override
  public String execute(Update update, SendMessage.SendMessageBuilder messageBuilder) {

    List<Event> events = eventService.getAllByChatId(update.getMessage().getChatId());
    if (events.isEmpty()) {
      return "Событий пока нет. Попробуйте \""
          + botConfig.getCommandDelimiter()
          + CommandFullOperation.EVENT_NEW.getName()
          + "\".";
    }

    StringBuilder result = new StringBuilder("События:\n");
    for (Event event : events) {
      result.append(
          "\n"
              + event.getId()
              + ". " + event.getName()
              + "\n\t\uD83D\uDCD6 " + event.getDescription()
              + "\n\t\uD83C\uDFD8 " + event.getPlace()
              + "\n\t⌛ " + event.getDate().toString()
              + "\n\t\uD83E\uDDCD\u200D♂️\uD83E\uDDCD\u200D♀️️Участники: ");
      if (event.getParticipants().isEmpty()) {
        result.append("пока нет.");
      } else {
        for (Participant part : event.getParticipants()) {
          result.append(
              part.getName()
                  + " ("
                  + part.getUsername()
                  + ") - "
                  + willingDateService
                  .getByEventIdAndParticipantId(
                      event.getId(), part.getId()
                  )
                  .orElse(
                      willingDateService.getByEventIdAndParticipantId(1L, 1L)
                          .orElse(
                              new WillingDate(
                                  new WillingDatePK(
                                      -1L,
                                      -1L
                                  ),
                                  new Event(),
                                  new Participant(),
                                  "даты нет"
                              )
                          )
                  )
                  .getValue()
          );
        }
        result = new StringBuilder(result.substring(0, result.length()));
        result.append(".\n");
      }
    }
    return result.toString();
  }
}
