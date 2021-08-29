package com.project.bot.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

  /**
   * Only dd/MM/yyyy as parameter string supported!
   * @param date
   * @return Built date
   */
  public static String buildDate(String date) {

    StringBuilder result = new StringBuilder();

    Matcher yearMatcher = Pattern
        .compile("\\d{2}/\\d{2}/(\\d{4})")
        .matcher(date);

    while (yearMatcher.find()) {
      result.append(yearMatcher.group(1));
    }

    Matcher monthMatcher = Pattern
        .compile("\\d{2}(/\\d{2}/)\\d{4}")
        .matcher(date);

    while (monthMatcher.find()) {
      result.append(monthMatcher.group(1));
    }

    Matcher dayMatcher = Pattern
        .compile("(\\d{2})/\\d{2}/\\d{4}")
        .matcher(date);

    while (dayMatcher.find()) {
      result.append(dayMatcher.group(1));
    }

    return result.toString();
  }

}
