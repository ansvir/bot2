package com.project.bot.model.event;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.servlet.http.Part;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class WillingDate {

  @EmbeddedId
  private WillingDatePK id;

  @ManyToOne(fetch = FetchType.EAGER)
  @MapsId("eventId")
  @JoinColumn(name = "event_id")
  private Event event;

  @ManyToOne(fetch = FetchType.EAGER)
  @MapsId("participantId")
  @JoinColumn(name = "participant_id")
  private Participant participant;

  private String value;

  public WillingDate(WillingDatePK id, Event event, Participant participant, String value) {
    this.id = id;
    this.event = event;
    this.participant = participant;
    this.value = value;
  }

  public WillingDate() {
  }
}
