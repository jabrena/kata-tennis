package com.hatim.nexeo.katatennis.domain.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@EqualsAndHashCode
@Document(collection = "game_set")
public class GameSet {

  @Id
  @Getter
  private String id;

  @Getter
  @Setter
  private int gameSetPlayer1;

  @Getter
  @Setter
  private int gameSetPlayer2;

}
