package com.project.bot.builder.date;

import com.project.bot.util.Util;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WillingDateConcreteBuilder implements WillingDateBuilder {

  @Override
  public String build(String date) {
    return Util.buildDate(date);
  }
}
