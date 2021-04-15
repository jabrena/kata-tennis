package com.hatim.nexeo.katatennis.application.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@Builder
@EqualsAndHashCode
@JsonRootName("Score")
public class MatchDto {

  @Getter private int currentGamePlayer1;

  @Getter private int currentGamePlayer2;

  @Getter private List<GameSetDto> gameSets;

  @Getter private String matchStatus;

}
