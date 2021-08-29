package com.project.bot.command;

import com.project.bot.builder.date.WillingDateBuilder;
import com.project.bot.builder.date.WillingDateConcreteBuilder;
import com.project.bot.builder.date.WillingDateDiapasonBuilder;
import com.project.bot.builder.date.WillingDateSingleWordBuilder;

public enum WillingDateEnum {
  CONCRETE(
      "\\d{2}/\\d{2}/\\d{4}",
      new WillingDateConcreteBuilder()
  ),
  UNKNOWN(
      "незнаю",
      new WillingDateSingleWordBuilder()
  ),
  ANY(
      "всеравно",
      new WillingDateSingleWordBuilder()
  ),
  DIAPASON(
      "\\d{2}/\\d{2}/\\d{4} - \\d{2}/\\d{2}/\\d{4}",
      new WillingDateDiapasonBuilder()
  );

  String pattern;
  WillingDateBuilder builder;

  WillingDateEnum(String pattern, WillingDateBuilder builder) {
    this.pattern = pattern;
    this.builder = builder;
  }

  public String getPattern() {
    return pattern;
  }

  public WillingDateBuilder getBuilder() {
    return builder;
  }
}
