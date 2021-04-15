package com.hatim.nexeo.katatennis.application.mapper;

import com.hatim.nexeo.katatennis.application.dto.GameDto;
import com.hatim.nexeo.katatennis.application.dto.GameSetDto;
import com.hatim.nexeo.katatennis.application.dto.MatchDto;
import com.hatim.nexeo.katatennis.domain.model.Game;
import com.hatim.nexeo.katatennis.domain.model.GameSet;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameMapperTest {

  @Test
  void toAPI_nullGame_ShouldReturnNull() {
    assertNull(GameMapper.toAPI(null));
  }

  @Test
  void toAPI_completeGame_shouldReturnGameAPI(){

    List<GameSet> gameSets = new ArrayList<>();
    gameSets.add(GameSet.builder().gameSetPlayer1(4).gameSetPlayer2(6).build());
    gameSets.add(GameSet.builder().gameSetPlayer1(2).gameSetPlayer2(5).build());

    Game gameInit =
            Game.builder().player1("Nadal").player2("Federer").currentGamePlayer1(1).currentGamePlayer2(3).gameSets(gameSets).build();

    GameDto resultGame = GameMapper.toAPI(gameInit);

    List<GameSetDto> gameSetDtoList = new ArrayList<>();
    gameSetDtoList.add(GameSetDto.builder().gameSetPlayer1(4).gameSetPlayer2(6).build());
    gameSetDtoList.add(GameSetDto.builder().gameSetPlayer1(2).gameSetPlayer2(5).build());

    MatchDto scorePlayer =
            MatchDto.builder()
                    .gameSets(gameSetDtoList)
                    .currentGamePlayer1(1)
                    .currentGamePlayer2(3)
                    .build();

    GameDto expectedGame =
            GameDto.builder().player1("Nadal").player2("Federer").match(scorePlayer).build();

    assertEquals(expectedGame, resultGame);

  }
  @Test
  void toAPI_gameWithoutSet_shouldReturnGameAPI(){

    Game gameInit =
            Game.builder().player1("Nadal").player2("Federer").currentGamePlayer1(1).currentGamePlayer2(3).build();

    GameDto resultGame = GameMapper.toAPI(gameInit);

    List<GameSetDto> gameSetDtoList = new ArrayList<>();

    MatchDto scorePlayer =
            MatchDto.builder()
                    .gameSets(gameSetDtoList)
                    .currentGamePlayer1(1)
                    .currentGamePlayer2(3)
                    .build();

    GameDto expectedGame =
            GameDto.builder().player1("Nadal").player2("Federer").match(scorePlayer).build();

    assertEquals(expectedGame, resultGame);

  }

  @Test
  void toDomain_nullGame_ShouldReturnNull() {
    assertNull(GameMapper.toDomain(null));
  }

  @Test
  void toDomain_completeGame_shouldReturnGameDomain(){

    List<GameSetDto> gameSetDtoList = new ArrayList<>();
    gameSetDtoList.add(GameSetDto.builder().gameSetPlayer1(0).gameSetPlayer2(4).build());

    MatchDto scorePlayer =
            MatchDto.builder()
                    .gameSets(gameSetDtoList)
                    .currentGamePlayer1(1)
                    .currentGamePlayer2(0)
                    .build();

    GameDto testGame =
            GameDto.builder().player1("Nadal").player2("Federer").match(scorePlayer).build();
    Game resultGame = GameMapper.toDomain(testGame);

    List<GameSet> expectedGameSet = List.of(GameSet.builder().gameSetPlayer1(0).gameSetPlayer2(4).build());
    Game expectedGame = Game.builder()
            .player1("Nadal").player2("Federer")
            .currentGamePlayer1(1).currentGamePlayer2(0)
            .gameSets(expectedGameSet).build();

    assertEquals(expectedGame, resultGame);

  }
}
