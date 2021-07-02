package com.hatim.nexeo.katatennis.application.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.NonNull;
import lombok.Value;

@JsonRootName("Game")
@Value
public class GameDto {

  @NonNull
  private String player1;
  @NonNull
  private String player2;
  private MatchDto match;
}
