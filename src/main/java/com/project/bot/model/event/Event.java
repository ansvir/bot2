package com.project.bot.model.event;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Event {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String description;
  private String place;
  private LocalDate date;
  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private Set<Participant> participants;

  public Event(String name, String description, String place, LocalDate date) {
    this.name = name;
    this.description = description;
    this.place = place;
    this.date = date;
  }

  public Event() {

  }
}
