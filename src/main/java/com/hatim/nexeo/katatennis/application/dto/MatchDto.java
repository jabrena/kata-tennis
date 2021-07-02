package com.hatim.nexeo.katatennis.application.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Value;

import java.util.List;

@JsonRootName("Score")
@Value
public class MatchDto {

  private int currentGamePlayer1;
  private int currentGamePlayer2;
  private List<GameSetDto> gameSets;
  private String matchStatus;
}
