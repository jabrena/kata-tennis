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

    MatchDto matchDto = new MatchDto(
            game.getCurrentGamePlayer1(),
            game.getCurrentGamePlayer2(),
            toAPI(game.getGameSets()),
            game.getMatchStatus());
    return new GameDto(
        game.getPlayer1(),
        game.getPlayer2(),
        matchDto);
  }

  public static Game toDomain(GameDto gameDto) {

    if (gameDto == null) {
      return null;
    }

    if (gameDto.getMatch() != null) {
      return new Game(
              null,
              gameDto.getPlayer1(),
              gameDto.getPlayer2(),
              gameDto.getMatch().getCurrentGamePlayer1(),
              gameDto.getMatch().getCurrentGamePlayer2(),
              toDomain(gameDto.getMatch()),
              null);
    } else {
      return new Game(
              null,
              gameDto.getPlayer1(),
              gameDto.getPlayer2(),
              0,
              0,
              toDomain(gameDto.getMatch()),
              null);
    }
  }

  private static List<GameSetDto> toAPI(List<GameSet> gameSets) {

    if (gameSets == null) {
      return new ArrayList<>();
    }

    return gameSets.stream()
        .map(
            domain -> new GameSetDto(
                    domain.getGameSetPlayer1(),
                    domain.getGameSetPlayer2()))
        .collect(Collectors.toUnmodifiableList());
  }

  private static List<GameSet> toDomain(MatchDto match) {

    if (match == null) {
      // init new match
      GameSet gameSet = new GameSet(null, 0,0);
      return List.of(gameSet);
    }

    return match.getGameSets().stream()
        .map(api -> new GameSet(null, api.getGameSetPlayer1(), api.getGameSetPlayer2()))
        .collect(Collectors.toUnmodifiableList());
  }
}
