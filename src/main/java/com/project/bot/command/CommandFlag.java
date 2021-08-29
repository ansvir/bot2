package com.project.bot.command;

public enum CommandFlag {

  TODAY("today"),
  T("t"),
  MINSK("minsk"),
  M("m"),
  WARSAW("warsaw"),
  W("w"),
  DOLLAR("dollar"),
  D("d"),
  BELARUS("belarus"),
  B("b"),
  POLAND("poland"),
  P("p"),
  NEW("создать"),
  DESCRIPTION("описание"),
  LIST("список"),
  ADD("добавить"),
  PARTICIPANT("part"),
  ME("меня"),
  EDIT("меняем"),
  DATE("дату"),
  DELETE("удалить"),
  NAME("имя"),
  REMOVE("удалить"),
  WILLING_DATE("желаемую дату"),
  PLACE("место");

  private String name;

  CommandFlag(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
