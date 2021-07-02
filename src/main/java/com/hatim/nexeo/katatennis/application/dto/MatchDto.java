package com.hatim.nexeo.katatennis.application.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@Builder
@EqualsAndHashCode
@JsonRootName("Score")
@Getter
public class MatchDto {

  private int currentGamePlayer1;
  private int currentGamePlayer2;
  private List<GameSetDto> gameSets;
  private String matchStatus;
}
