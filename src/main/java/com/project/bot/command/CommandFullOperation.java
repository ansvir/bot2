package com.project.bot.command;

import com.project.bot.command.event.EventAddMeCommand;
import com.project.bot.command.event.EventDeleteCommand;
import com.project.bot.command.event.EventEditDateCommand;
import com.project.bot.command.event.EventEditDescriptionCommand;
import com.project.bot.command.event.EventEditNameCommand;
import com.project.bot.command.event.EventListCommand;
import com.project.bot.command.event.EventNewCommand;
import com.project.bot.command.event.EventRemoveMeCommand;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public enum CommandFullOperation {

  EVENT_LIST(
      CommandType.EVENT.getName()
          + " "
          + CommandFlag.LIST.getName(),
      "Перечислить события и участников.",
      CommandType.EVENT,
      List.of(
          CommandFlag.LIST
      ),
      0
  ),
  EVENT_NEW(
      CommandType.EVENT.getName()
          + " "
          + CommandFlag.NEW.getName(),
      "Создать новое событие. Юзаем: !событие создать \"имя_события\"",
      CommandType.EVENT,
      List.of(
          CommandFlag.NEW
      ),
      1
  ),
  EVENT_ADD_ME(
      CommandType.EVENT.getName()
          + " "
          + CommandFlag.ADD.getName()
          + " "
          + CommandFlag.ME.getName(),
      "Добавить меня на событие. Как юзать: !событие добавить меня [номер] [дата]. "
          + "Дата - когда я, приблизительно, могу быть на данном событии. Может быть следующих форматов:\n"
      + "1) Конкретная, например: 20/04/2014\n"
      + "2) В диапазоне, например \"20/04/2014 - 25/04/2014\"\n"
      + "3) Неизвестная, просто оставьте незнаю\n"
      + "4) Любая, поставьте всеравно.",
      CommandType.EVENT,
      List.of(
          CommandFlag.ADD,
          CommandFlag.ME
      ),
      2),
  EVENT_REMOVE_ME(
      CommandType.EVENT.getName()
          + " "
          + CommandFlag.REMOVE.getName()
          + " "
          + CommandFlag.ME.getName(),
      "Удалить меня из события. Юзаем: !событие удалить меня [номер]",
      CommandType.EVENT,
      List.of(
          CommandFlag.REMOVE,
          CommandFlag.ME
      ),
      1),
  EVENT_EDIT_DESC(
      CommandType.EVENT.getName()
          + " "
          + CommandFlag.EDIT.getName()
          + " "
          + CommandFlag.DESCRIPTION.getName(),
      "Изменить описание события. Как юзать: !событие меняем описание [номер] \"Здесь длииииииинное описание события\""
          + ". Не забудь двойные кавычки!!!",
      CommandType.EVENT,
      List.of(
          CommandFlag.ADD,
          CommandFlag.EDIT,
          CommandFlag.DESCRIPTION
      ),
      2),
  EVENT_EDIT_DATE(
      CommandType.EVENT.getName()
          + " "
          + CommandFlag.EDIT.getName()
          + " "
          + CommandFlag.DATE.getName(),
      "Изменить дату события. Как юзать: !событие меняем дату [number] [dd/MM/yyyy]. "
          + " будь внимательней, формат даты должен быть такой: 20/04/2021.",
      CommandType.EVENT,
      List.of(
          CommandFlag.ADD,
          CommandFlag.EDIT,
          CommandFlag.DATE
      ),
      2),
  EVENT_DEL(
      CommandType.EVENT.getName()
          + " "
          + CommandFlag.DELETE.getName(),
      "Удалить событие. Ты точно уверен? Не вернуть. Юзаем: !событие удалить [номер].",
      CommandType.EVENT,
      List.of(
          CommandFlag.DELETE
      ),
      1),
  EVENT_EDIT_NAME(
      CommandType.EVENT.getName()
          + " "
          + CommandFlag.EDIT.getName()
          + " "
          + CommandFlag.NAME.getName(),
      "Удалить событие. Ты точно уверен? Не вернуть. Юзаем: !событие удалить [номер].",
      CommandType.EVENT,
      List.of(
          CommandFlag.EDIT,
          CommandFlag.NAME
      ),
      2);
//  EVENT_DESC(
//      CommandType.EVENT.getName()
//          + " "
//          + CommandFlag.DESCRIPTION.getName(),
//      "View event description. Usage: !event desc [number]\nNumber - number of event in list",
//      CommandType.EVENT,
//      List.of(
//          CommandFlag.DESCRIPTION
//      ),
//      1
//  ),
//
//  EVENT_ADD_PART(
//      CommandType.EVENT.getName()
//      + " "
//      + CommandFlag.ADD.getName()
//      + " "
//      + CommandFlag.PARTICIPANT.getName(),
//      "Add new Participant to event. Usage: !event part [number] [username] [date]\nNumber - number of event"
//          + ".\nUsername - format @username.\nDate format: "
//          + "dd-MM-yyyy.",
//      CommandType.EVENT,
//      List.of(
//          CommandFlag.ADD,
//          CommandFlag.PARTICIPANT
//      ),
//      3
//  );

  private String name;
  private String description;
  private Command command;
  private CommandType commandType;
  private List<CommandFlag> flags;
  private Integer placeholdersAmount;

  CommandFullOperation(
      String name,
      String description,
      CommandType command,
      List<CommandFlag> flags,
      Integer placeholdersAmount
  ) {
    this.name = name;
    this.description = description;
    this.commandType = command;
    this.flags = flags;
    this.placeholdersAmount = placeholdersAmount;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public CommandType getCommandType() {
    return commandType;
  }

  public List<CommandFlag> getFlags() {
    return flags;
  }

  public Integer getPlaceholdersAmount() {
    return placeholdersAmount;
  }

  public Command getCommand() {
    return command;
  }

  public void setCommand(Command command) {
    this.command = command;
  }

  @Component
  public static class CommandFullInjector {

    @Autowired
    private EventListCommand eventListCommand;
    @Autowired
    private EventNewCommand eventNewCommand;
    @Autowired
    private EventAddMeCommand eventAddMeCommand;
    @Autowired
    private EventEditDescriptionCommand eventEditDescriptionCommand;
    @Autowired
    private EventEditDateCommand eventEditDateCommand;
    @Autowired
    private EventDeleteCommand eventDeleteCommand;
    @Autowired
    private EventEditNameCommand eventEditNameCommand;
    @Autowired
    private EventRemoveMeCommand eventRemoveMeCommand;

    @PostConstruct
    public void postConstruct() {
      CommandFullOperation.EVENT_LIST.setCommand(eventListCommand);
      CommandFullOperation.EVENT_NEW.setCommand(eventNewCommand);
      CommandFullOperation.EVENT_ADD_ME.setCommand(eventAddMeCommand);
      CommandFullOperation.EVENT_EDIT_DESC.setCommand(eventEditDescriptionCommand);
      CommandFullOperation.EVENT_EDIT_DATE.setCommand(eventEditDateCommand);
      CommandFullOperation.EVENT_DEL.setCommand(eventDeleteCommand);
      CommandFullOperation.EVENT_EDIT_NAME.setCommand(eventEditNameCommand);
      CommandFullOperation.EVENT_REMOVE_ME.setCommand(eventRemoveMeCommand);
    }
  }
}
