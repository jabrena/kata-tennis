package com.hatim.nexeo.katatennis.application.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

@Builder
@EqualsAndHashCode
@JsonRootName("Game")
public class GameDto {

  @NonNull
  @Getter private String player1;

  @NonNull
  @Getter private String player2;

  @Getter private MatchDto match;


}
