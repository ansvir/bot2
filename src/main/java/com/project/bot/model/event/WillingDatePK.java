package com.project.bot.model.event;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class WillingDatePK implements Serializable {

  @Column(name = "event_id")
  private Long eventId;
  @Column(name = "participant_id")
  private Long participantId;

  public WillingDatePK(Long eventId, Long participantId) {
    this.eventId = eventId;
    this.participantId = participantId;
  }

  public WillingDatePK() {
  }
}
