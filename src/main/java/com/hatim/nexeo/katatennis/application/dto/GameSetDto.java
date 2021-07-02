package com.hatim.nexeo.katatennis.application.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Value;

@JsonRootName("Set")
@Value
public class GameSetDto {

  private int gameSetPlayer1;
  private int gameSetPlayer2;
}
