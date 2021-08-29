package com.project.bot.command;

public enum Placeholder {

  NAME("[имя]"),
  NAME_DOUBLE_QUOTES("[\"имя\"]"),
  NUMBER("[номер]"),
  DATE("[дата]"),
  DATE_DOUBLE_QUOTES("[\"дата\"]"),
  DESC("[\"описание\"]"),
  PLACE_DOUBLE_QUOTES("[\"место\"]");

  String value;

  Placeholder(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
