package com.project.bot.builder.date;

import com.project.bot.util.Util;
import java.time.LocalDate;

public class WillingDateDiapasonBuilder implements WillingDateBuilder {

  @Override
  public String build(String date) {
    String[] datesArray = date.split(" - ");
    LocalDate startDate = null;
    LocalDate endDate = null;

    String startDateString = Util.buildDate(datesArray[0]);
    if (startDateString == null) {
      return null;
    }

    String endDateString = Util.buildDate(datesArray[1]);

    if (endDateString == null) {
      return null;
    }

    try {
      startDate = LocalDate.parse(
          startDateString.replace("/", "-")
      );
      endDate = LocalDate.parse(
          endDateString.replace("/", "-")
      );
    } catch (Exception e) {
      return null;
    }

    if (startDate == null || endDate == null) {
      return null;
    }

    if (startDate.isAfter(endDate)) {
      return null;
    }

    return startDateString + " - " + endDateString;
  }
}
