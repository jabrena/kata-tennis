package com.hatim.nexeo.katatennis.application.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@EqualsAndHashCode
@JsonRootName("Set")
@Getter
public class GameSetDto {

  private int gameSetPlayer1;
  private int gameSetPlayer2;
}
