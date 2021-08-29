package com.project.bot.command.event;

import com.project.bot.command.Command;
import com.project.bot.command.CommandFullOperation;
import com.project.bot.model.event.Event;
import com.project.bot.model.event.Participant;
import com.project.bot.service.EventService;
import com.project.bot.service.ParticipantService;
import com.project.bot.util.Parser;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class EventEditDateCommand implements Command {

  @Autowired
  private EventService eventService;

  @Autowired
  private Parser parser;

  @Override
  public String execute(Update update, SendMessage.SendMessageBuilder messageBuilder) {
    List<String> parameters = parser.parsePlaceholders(CommandFullOperation.EVENT_EDIT_DATE, update.getMessage().getText());
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

    try {
      event.setDate(LocalDate
          .parse(
              parameters.get(1).replaceAll("/", "-"),
              DateTimeFormatter.ofPattern("dd-MM-yyyy")
          )
      );
    } catch (Exception e) {
      return "Формат даты некорректен. Используйте:\n\tКонкретная дата: dd/MM/yyyy"
          + "\n\tДиапазон: dd/MM/yyyy - dd/MM/yyyy. Первая дата должна быть не позднее второй."
          + "\n\t Если не знаете, просто вместо даты впишите \"незнаю\""
          + "\n\t Если вам все равно, так и впишите \"всеравно\"";
    }

    Event saved = eventService.save(event);

    return "Дата события " + saved.getName() + " обновлена на " + event.getDate().toString() + ".";
  }
}
