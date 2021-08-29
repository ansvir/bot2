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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Participant {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long telegramId;
  private String name;
  private String username;

  public Participant(Long telegramId, String name, String username) {
    this.telegramId = telegramId;
    this.name = name;
    this.username = username;
  }

  public Participant() {}
}
