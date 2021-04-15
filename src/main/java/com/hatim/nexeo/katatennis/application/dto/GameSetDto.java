package com.hatim.nexeo.katatennis.application.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@EqualsAndHashCode
@JsonRootName("Set")
public class GameSetDto {
  @Getter private int gameSetPlayer1;

  @Getter private int gameSetPlayer2;
}
