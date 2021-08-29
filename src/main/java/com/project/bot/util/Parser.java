package com.project.bot.util;

import com.project.bot.command.Command;
import com.project.bot.command.CommandFullOperation;
import com.project.bot.command.CommandFullService;
import com.project.bot.command.CommandType;
import com.project.bot.command.WillingDateEnum;
import com.project.bot.config.BotConfig;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
@PropertySource("classpath:application.properties")
public class Parser {

  private BotConfig botConfig;
  private final String COMMAND_PATTERN;
  private final String DESC_PATTERN = "\".+\"";
  private final String ALLOWED_CHARACTERS_PATTERN = "[A-Za-zА-ЯЁа-яё\\-\",.:;/0-9]";
  public Parser(BotConfig botConfig) {
    this.botConfig = botConfig;
    COMMAND_PATTERN = "^"
        + botConfig.getCommandDelimiter()
        + ALLOWED_CHARACTERS_PATTERN
        + "+"
        + "(\\s"
        + ALLOWED_CHARACTERS_PATTERN
        + "+)"
        + "*$";
  }

  public Command defineCommand(String message) {
    message = message.strip();
    Command command = CommandFullService
        .valueOf(CommandType
            .UNRECOGNIZED
            .getName()
            .toUpperCase())
        .getCommand();
    if (message.matches(COMMAND_PATTERN)) {
      if (message.equals(botConfig.getCommandDelimiter() + CommandFullService.HELP.getName())) {
        return CommandFullService.HELP.getCommand();
      }
      for (CommandFullOperation commandFullOperation : CommandFullOperation.values()) {
        if (message.contains("\"")) {
          String tempMessage = message.substring(0, message.indexOf("\""));
          if (tempMessage.startsWith(
              botConfig.getCommandDelimiter() + commandFullOperation.getName())
              && Arrays.stream(
              tempMessage.substring(
                  botConfig.getCommandDelimiter().length()
                      + commandFullOperation.getName().length()
              )
                  .split(" "))
              .filter(it -> it.length() > 0)
              .count() + 1
              == commandFullOperation.getPlaceholdersAmount()) {
            command = commandFullOperation.getCommand();
            break;
          }
        }
        if (message.startsWith(
            botConfig.getCommandDelimiter() + commandFullOperation.getName())
            && Arrays.stream(
            message.substring(
                botConfig.getCommandDelimiter().length()
                    + commandFullOperation.getName().length()
            )
                .split(" "))
            .filter(it -> it.length() > 0)
            .count()
            == commandFullOperation.getPlaceholdersAmount()) {
          command = commandFullOperation.getCommand();
          break;
        }
      }
    }
    return command;
  }

  public List<String> parsePlaceholders(CommandFullOperation command, String message) {

    message = message.strip();

    String placeholders = message.substring(
        botConfig.getCommandDelimiter().length()
            + command.getName().length()
            + 1);

    List<String> descPlaceholders = new LinkedList<>();

    Matcher matcher = Pattern
        .compile(DESC_PATTERN)
        .matcher(placeholders);

    while (matcher.find()) {
     descPlaceholders.add(
         matcher.group().substring(1, matcher.group().length() - 1)
     );
     placeholders = placeholders.replace(matcher.group(), "");
    }

    return Stream.concat(
        Arrays.stream(placeholders.split(" ")),
        Stream.of(descPlaceholders.toArray())
    )
        .map(String.class::cast)
        .collect(Collectors.toList());
  }

  public String parseWillingDate(String date) {
    String result = null;
    date = date.strip();
    for (WillingDateEnum willingDate : WillingDateEnum.values()) {
      if (date.matches(willingDate.getPattern())) {
        result = willingDate.getBuilder().build(date);
      }
    }
    return result;
  }
}
