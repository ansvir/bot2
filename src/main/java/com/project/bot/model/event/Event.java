package com.project.bot.model.event;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Event {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long chatId;
  private String name;
  private String description;
  private String place;
  private LocalDate date;
  @OneToMany(fetch = FetchType.EAGER)
  private Set<Participant> participants;

  public Event(
      Long chatId,
      String name,
      String description,
      String place,
      LocalDate date,
      Set<Participant> participants
  ) {
    this.chatId = chatId;
    this.name = name;
    this.description = description;
    this.place = place;
    this.date = date;
    this.participants = participants;
  }

  public Event() {}
}
