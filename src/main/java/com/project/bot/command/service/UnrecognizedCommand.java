package com.project.bot.command.service;

import com.project.bot.command.Command;
import com.project.bot.command.CommandFullService;
import com.project.bot.config.BotConfig;
import java.util.Random;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class UnrecognizedCommand implements Command {

  private final BotConfig botConfig;

  public UnrecognizedCommand(BotConfig botConfig) {
    this.botConfig = botConfig;
  }

  @Override
  public String execute(Update update, SendMessage.SendMessageBuilder messageBuilder) {
    String helpCommand = botConfig.getCommandDelimiter() + CommandFullService.HELP.getName();
    String[] responses = new String[]{
        "Я не понял команды, попробуй посмотреть '" + helpCommand + "'.",
        "Что? Плиз, посмотри инструкцию, ты ошибься - '" + helpCommand + "'.",
        "Сори, я не понял того, что ты написал, плиз чекни '" + helpCommand + "'.",
        "У меня нет таких команд :( . Ты где-то ошибься, перепроверь себя, тебе поможет - '" + helpCommand + "'.",
        "Извини, команда " + update.getMessage().getText() + " невалидна, тебе нужно быть внимательней :). Чекни - '"
            + helpCommand
            + "'."
    };
    return responses[new Random().nextInt(4)];
  }
}
