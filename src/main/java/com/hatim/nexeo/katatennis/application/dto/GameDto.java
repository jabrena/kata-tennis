package com.hatim.nexeo.katatennis.application.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

@Builder
@EqualsAndHashCode
@JsonRootName("Game")
@Getter
public class GameDto {

  @NonNull
  private String player1;
  @NonNull
  private String player2;
  private MatchDto match;
}
