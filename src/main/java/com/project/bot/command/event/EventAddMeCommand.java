package com.project.bot.command.event;

import com.project.bot.command.Command;
import com.project.bot.command.CommandFullOperation;
import com.project.bot.model.event.Event;
import com.project.bot.model.event.Participant;
import com.project.bot.model.event.WillingDate;
import com.project.bot.model.event.WillingDatePK;
import com.project.bot.service.EventService;
import com.project.bot.service.ParticipantService;
import com.project.bot.service.WillingDateService;
import com.project.bot.util.Parser;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
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
  private WillingDateService willingDateService;

  @Autowired
  private Parser parser;

  @Override
  public String execute(Update update, SendMessage.SendMessageBuilder messageBuilder) {

    User from = update.getMessage().getFrom();

    List<String> parameters = parser
        .parsePlaceholders(CommandFullOperation.EVENT_ADD_ME, update.getMessage().getText());
    Long eventId;
    String willingDate;
    try {
      eventId = Long.parseLong(parameters.get(0));
      willingDate = parameters.get(1);
    } catch (NumberFormatException e) {
      return "\uD83D\uDFE0 Неправильный номер события. Попробуйте заново";
    } catch (DateTimeParseException e) {
      return "\uD83D\uDFE0 Неправильный формат даты. Он следующий: \"dd/MM/yyyy\", где \"dd\" - день, \"MM\" - "
          + "месяц and \"yyyy\" - год.";
    }

    Event event = eventService.getById(eventId);

    if (event.getParticipants()
        .stream()
        .anyMatch(it -> it.getTelegramId().equals(from.getId()))) {
      return "\uD83D\uDFE0 Вы уже добавлены на данное событие!!!";
    }

    if (event == null) {
      return "\uD83D\uDFE0 Неправильный номер события. Внимательней, плиз";
    }

    String parsedWillingDate = parser.parseWillingDate(willingDate);

    if (parsedWillingDate == null) {
      return "\uD83D\uDFE0 Формат даты некорректен. Используйте:\n\tКонкретная дата: dd/MM/yyyy"
          + "\n\tДиапазон: dd/MM/yyyy - dd/MM/yyyy. Первая дата должна быть не позднее второй."
          + "\n\t Если не знаете, просто вместо даты впишите \"незнаю\""
          + "\n\t Если вам все равно, так и впишите \"всеравно\"";
    }

    Participant participant = participantService
        .getByTelegramId(from.getId())
        .orElseGet(() -> participantService.create(
            new Participant(
                from.getId(),
                from.getFirstName()
                    + " "
                    + (from.getLastName() == null ? "" : from.getLastName())
                    .strip(),
                from.getUserName()
            )
        ));

    WillingDate savedWillingDate = willingDateService.save(
        new WillingDate(
            new WillingDatePK(
                eventId,
                participantService.getById(from.getId()).getId()
            ),
            event,
            participant,
            parsedWillingDate
        )
    );

    try {
      event.getParticipants()
          .add(participant);
    } catch (EntityNotFoundException e) {
      return "\uD83D\uDFE0 Неправильный номер события. Внимательней, плиз";
    }

    eventService.save(event);

    return "\uD83D\uDFE2 Участник "
        + participant.getName()
        + ", который хочет поучаствовать в "
        + event.getName()
        + " ("
        + savedWillingDate.getValue()
        + "), добавлен.";
  }
}
