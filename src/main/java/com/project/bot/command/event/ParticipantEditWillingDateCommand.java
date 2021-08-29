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
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage.SendMessageBuilder;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class ParticipantEditWillingDateCommand implements Command {

  @Autowired
  private EventService eventService;

  @Autowired
  private ParticipantService participantService;

  @Autowired
  private WillingDateService willingDateService;

  @Autowired
  private Parser parser;

  @Override
  public String execute(Update update, SendMessageBuilder messageBuilder) {
    List<String> parameters = parser
        .parsePlaceholders(CommandFullOperation.PARTICIPANT_EDIT_WILLING_DATE, update.getMessage().getText());
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

    Participant participant = participantService.getByTelegramId(update.getMessage().getFrom().getId())
        .orElse(null);
    if (participant == null) {
      return "\uD83D\uDFE0 Вы еще не состоите ни в одно событии!!!";
    }

    if (eventService
        .getById(eventId)
        .getParticipants()
        .stream()
        .filter(
            it -> it
                .getTelegramId()
                .equals(participant.getTelegramId())
        )
        .findFirst()
        .isEmpty()
    ) {
      return "\uD83D\uDFE0 Вы не участвуете в данном мероприятии!";
    }

    String willingDate = parameters.get(1);

    WillingDate founOptionalWillingDate = willingDateService
        .getByEventIdAndParticipantId(eventId, participant.getId())
        .orElse(
            willingDateService.save(
                new WillingDate(
                    new WillingDatePK(
                        eventId,
                        participant.getId()
                    ),
                    eventService.getById(eventId),
                    participant,
                    willingDate
                )
            ));

    willingDateService.save(founOptionalWillingDate);

    return "\uD83D\uDD35 Теперь вы готовы появиться на событии \""
        + event.getName()
        + "\" в это время - \""
        + willingDate + "\".";
  }
}
