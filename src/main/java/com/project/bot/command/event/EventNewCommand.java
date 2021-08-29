package com.project.bot.command.event;

import com.project.bot.command.Command;
import com.project.bot.command.CommandFullOperation;
import com.project.bot.model.event.Event;
import com.project.bot.service.EventService;
import com.project.bot.util.Parser;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage.SendMessageBuilder;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

@Component
public class EventNewCommand implements Command {

  @Autowired
  private Parser parser;

  @Autowired
  private EventService service;

  @Override
  public String execute(Update update, SendMessage.SendMessageBuilder messageBuilder) {
    String name = parser.parsePlaceholders(CommandFullOperation.EVENT_NEW, update.getMessage().getText())
        .stream()
        .skip(1)
        .findFirst()
        .orElse("Пусто");
    Event created = service.save(
        new Event(
            update.getMessage().getChatId(),
            name,
            "Описания пока нет",
            "Где - неизвестно",
            LocalDate.now(),
            Set.of()
        )
    );

        return "\uD83D\uDFE2 Событие \"" + created.getName() + "\" создано!";
  }
}
