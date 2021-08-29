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
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

@Component
public class EventAddMeCommand implements Command {

  @Autowired
  private EventService eventService;

  @Autowired
  private ParticipantService participantService;

  @Autowired
  private Parser parser;

  @Override
  public String execute(Update update, SendMessage.SendMessageBuilder messageBuilder) {

    Participant part = participantService.getByTelegramId(update.getMessage().getFrom().getId())
        .orElse(null);

    if (part == null) {
      return "Вы уже заявились на данное событие!";
    }

    List<String> parameters = parser
        .parsePlaceholders(CommandFullOperation.EVENT_ADD_ME, update.getMessage().getText());
    Long eventId;
    String willingDate;
    try {
      eventId = Long.parseLong(parameters.get(0));
      willingDate = parameters.get(1);
    } catch (NumberFormatException e) {
      return "Неправильный номер события. Попробуйте заново";
    } catch (DateTimeParseException e) {
      return "Неправильный формат даты. Он следующий: \"dd-MM-yyyy\", где \"dd\" - день, \"MM\" - "
          + "месяц and \"yyyy\" - год.";
    }

    Event event = eventService.getById(eventId);

    if (event == null) {
      return "Неправильный номер события. Внимательней, плиз";
    }

    User from = update.getMessage().getFrom();

    String parsedWillingDate = parser.parseWillingDate(willingDate);

    if (parsedWillingDate == null) {
      return "Формат даты некорректен. Используйте:\n\tКонкретная дата: dd/MM/yyyy"
          + "\n\tДиапазон: dd/MM/yyyy - dd/MM/yyyy. Первая дата должна быть не позднее второй."
          + "\n\t Если не знаете, просто вместо даты впишите \"незнаю\""
          + "\n\t Если вам все равно, так и впишите \"всеравно\"";
    }

    Participant participant = participantService
        .getByTelegramId(update.getMessage().getFrom().getId())
        .orElseGet(() -> participantService.create(
            new Participant(
                update.getMessage().getFrom().getId(),
                from.getFirstName()
                    + " "
                    + (from.getLastName() == null ? "" : from.getLastName()),
                from.getUserName(),
                parsedWillingDate
            )
        ));

    try {
      event.getParticipants()
          .add(participant);
    } catch (EntityNotFoundException e) {
      return "Неправильный номер события. Внимательней, плиз";
    }

    eventService.save(event);

    return "Участник "
        + participant.getName()
        + ", который хочет поучаствовать в "
        + event.getName()
        + participant.getWillingDate()
        + ", добавлен.";
  }
}
