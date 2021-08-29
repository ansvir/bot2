package com.project.bot.command.service;

import com.project.bot.command.Command;
import com.project.bot.command.CommandFullOperation;
import com.project.bot.config.BotConfig;
import com.project.bot.impl.Bot;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

@Component
public class HelpCommand implements Command {

  private final BotConfig botConfig;

  public HelpCommand(BotConfig botConfig) {
    this.botConfig = botConfig;
  }

  @Override
  public String execute(Update update, SendMessage.SendMessageBuilder messageBuilder) {
    User currentUser = update.getMessage().getFrom();
    String userFirstName = currentUser.getFirstName();
    StringBuilder message = new StringBuilder();
    message.append(detectGoodPartOfADayString() + " ");
    message.append(detectPartOfADayString());
    message.append(", ");
    message.append(userFirstName);
    message.append("!\n\n");
    message.append("Меня зовут Бот школы №10 и я здесь, чтобы упростить жизнь школьников и выпускников! Для тебя "
        + "есть следующие команды:\n");
    message.append("\n");
    for (CommandFullOperation commandFullOperation : CommandFullOperation.values()) {
      message.append(botConfig.getCommandDelimiter() + commandFullOperation.getName()
          + " - "
          + (commandFullOperation.getDescription() == null
          ? "Описания пока нет"
          : commandFullOperation.getDescription())
              + "\n\n"
      );
    }
    message.append("С чего начнем?");
    return message.toString();
  }

  private String detectGoodPartOfADayString() {
    switch (detectPartOfADayString()) {
      case "ночи": {
        return "Доброй";
      }
      case "утра":
      case "дня": {
        return "Доброго";
      }
      default:
        return "Доброго";
    }
  }

  private String detectPartOfADayString() {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH");
    LocalDateTime now = LocalDateTime.now();
    short hours = Short.parseShort(dtf.format(now));
    if (hours >= 0 && hours <= 5) {
      return "ночи";
    } else if (hours >= 6 && hours <= 12) {
      return "утра";
    } else if (hours >= 13 && hours <= 18) {
      return "дня";
    } else {
      return "вечера";
    }
  }
}
