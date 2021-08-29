package com.project.bot.command;

public enum CommandType {
  HELP("помогите"),
  WEATHER("weather"),
  W("w"),
  UNRECOGNIZED("unrecognized"),
  RATE("rate"),
  R("r"),
  EVENT("событие"),
  PARTICIPANT("участник");

  private String name;

  CommandType(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
