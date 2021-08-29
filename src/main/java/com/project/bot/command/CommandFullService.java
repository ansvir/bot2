package com.project.bot.command;

import com.project.bot.command.service.HelpCommand;
import com.project.bot.command.service.UnrecognizedCommand;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public enum CommandFullService {
  HELP(CommandType.HELP.getName()),
  UNRECOGNIZED(CommandType.UNRECOGNIZED.getName());

  @Component
  public static class CommandFullInjector {

    @Autowired
    HelpCommand helpCommand;

    @Autowired
    UnrecognizedCommand unrecognizedCommand;

    @PostConstruct
    public void postConstruct() {
      CommandFullService.HELP.setCommand(helpCommand);
      CommandFullService.UNRECOGNIZED.setCommand(unrecognizedCommand);
    }
  }

  private String name;
  private Command command;

  CommandFullService(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public Command getCommand() {
    return command;
  }

  public void setCommand(Command command) {
    this.command = command;
  }
}
