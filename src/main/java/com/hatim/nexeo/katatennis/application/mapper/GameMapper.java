package com.hatim.nexeo.katatennis.application.mapper;

import com.hatim.nexeo.katatennis.application.dto.GameDto;
import com.hatim.nexeo.katatennis.application.dto.GameSetDto;
import com.hatim.nexeo.katatennis.application.dto.MatchDto;
import com.hatim.nexeo.katatennis.domain.model.Game;
import com.hatim.nexeo.katatennis.domain.model.GameSet;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameMapper {

  private GameMapper() {
    throw new IllegalStateException("Utility class");
  }

  public static GameDto toAPI(Game game) {

    if (game == null) {
      return null;
    }

    MatchDto matchDto =
        MatchDto.builder()
            .currentGamePlayer1(game.getCurrentGamePlayer1())
            .currentGamePlayer2(game.getCurrentGamePlayer2())
            .gameSets(toAPI(game.getGameSets()))
            .matchStatus(game.getMatchStatus())
            .build();
    return GameDto.builder()
        .player1(game.getPlayer1())
        .player2(game.getPlayer2())
        .match(matchDto)
        .build();
  }

  public static Game toDomain(GameDto gameDto) {

    if (gameDto == null) {
      return null;
    }
    Game.GameBuilder gameBuilder = Game.builder()
            .player1(gameDto.getPlayer1())
            .player2(gameDto.getPlayer2())
            .gameSets(toDomain(gameDto.getMatch()));
    if(gameDto.getMatch() != null){
      gameBuilder.currentGamePlayer1(gameDto.getMatch().getCurrentGamePlayer1());
      gameBuilder.currentGamePlayer2(gameDto.getMatch().getCurrentGamePlayer2());
    } else {
      gameBuilder.currentGamePlayer1(0);
      gameBuilder.currentGamePlayer2(0);
    }
    return gameBuilder.build();
  }

  private static List<GameSetDto> toAPI(List<GameSet> gameSets) {

    if (gameSets == null) {
      return new ArrayList<>();
    }

    return gameSets.stream()
        .map(
            domain ->
                GameSetDto.builder()
                    .gameSetPlayer1(domain.getGameSetPlayer1())
                    .gameSetPlayer2(domain.getGameSetPlayer2())
                    .build())
        .collect(Collectors.toUnmodifiableList());
  }

  private static List<GameSet> toDomain(MatchDto match) {

    if (match == null) {
      // init new match
      return List.of(GameSet.builder().gameSetPlayer1(0).gameSetPlayer2(0).build());
    }

    return match.getGameSets().stream()
        .map(
            api ->
                GameSet.builder()
                    .gameSetPlayer1(api.getGameSetPlayer1())
                    .gameSetPlayer2(api.getGameSetPlayer2())
                    .build())
        .collect(Collectors.toUnmodifiableList());
  }
}
