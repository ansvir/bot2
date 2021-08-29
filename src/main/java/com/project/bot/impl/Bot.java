package com.project.bot.impl;

import com.project.bot.command.Command;
import com.project.bot.command.CommandFlag;
import com.project.bot.command.CommandFullOperation;
import com.project.bot.command.CommandType;
import com.project.bot.config.BotConfig;
import com.project.bot.util.Parser;
import java.util.Map;
import java.util.Set;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Slf4j
public class Bot extends TelegramLongPollingBot {

  private final BotConfig botConfig;

  @Autowired
  private Parser parser;

  public Bot(BotConfig botConfig) {
    this.botConfig = botConfig;
  }

  @Override
  public void onUpdateReceived(Update update) {
    SendMessage.SendMessageBuilder messageBuilder = SendMessage.builder();
    String messageText;
    String chatId;
    if (update.getMessage() != null) {
      chatId = update.getMessage().getChatId().toString();
      messageBuilder.chatId(chatId);
      messageText = update.getMessage().getText();
      messageText = messageText.strip();
      if (!messageText.startsWith(botConfig.getCommandDelimiter())) {
        return;
      }
      Command command = parser.defineCommand(messageText);
      try {
        messageBuilder.text(command.execute(update, messageBuilder));
      } catch (Exception e) {
        messageBuilder.text("\uD83D\uDD34 При обработке запроса возникла ошибка на моей стороне \uD83D\uDE14. "
            + "Попробуйте позже!");
        e.printStackTrace();
      }

      try {
        execute(messageBuilder.build());
      } catch (TelegramApiException e) {
        e.printStackTrace();
      }
    }
  }

  public String getBotUsername() {
    return botConfig.getBotUserName();
  }

  public String getBotToken() {
    return botConfig.getToken();
  }
}