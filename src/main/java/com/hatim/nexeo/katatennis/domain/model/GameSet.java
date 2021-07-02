package com.hatim.nexeo.katatennis.domain.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "game_set")
@Data
@AllArgsConstructor
public class GameSet {

  @Id
  private String id;
  private int gameSetPlayer1;
  private int gameSetPlayer2;
}
