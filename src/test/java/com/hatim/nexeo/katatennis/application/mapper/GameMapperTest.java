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
    gameSets.add(new GameSet(null, 4,6));
    gameSets.add(new GameSet(null, 2,5));

    Game gameInit =
            Game.builder().player1("Nadal").player2("Federer").currentGamePlayer1(1).currentGamePlayer2(3).gameSets(gameSets).build();

    GameDto resultGame = GameMapper.toAPI(gameInit);

    List<GameSetDto> gameSetDtoList = new ArrayList<>();
    gameSetDtoList.add(new GameSetDto(4,6));
    gameSetDtoList.add(new GameSetDto(2,5));

    MatchDto scorePlayer =
            new MatchDto(1,3, gameSetDtoList, null);

    GameDto expectedGame = new GameDto("Nadal","Federer",scorePlayer);

    assertEquals(expectedGame, resultGame);

  }
  @Test
  void toAPI_gameWithoutSet_shouldReturnGameAPI(){

    Game gameInit =
            Game.builder().player1("Nadal").player2("Federer").currentGamePlayer1(1).currentGamePlayer2(3).build();

    GameDto resultGame = GameMapper.toAPI(gameInit);

    List<GameSetDto> gameSetDtoList = new ArrayList<>();

    MatchDto scorePlayer = new MatchDto(1,3, gameSetDtoList, null);
    GameDto expectedGame = new GameDto("Nadal","Federer",scorePlayer);

    assertEquals(expectedGame, resultGame);

  }

  @Test
  void toDomain_nullGame_ShouldReturnNull() {
    assertNull(GameMapper.toDomain(null));
  }

  @Test
  void toDomain_completeGame_shouldReturnGameDomain(){

    List<GameSetDto> gameSetDtoList = new ArrayList<>();
    gameSetDtoList.add(new GameSetDto(0,4));

    MatchDto scorePlayer = new MatchDto(1,0, gameSetDtoList, null);
    GameDto testGame = new GameDto("Nadal","Federer",scorePlayer);
    Game resultGame = GameMapper.toDomain(testGame);

    GameSet gameSet = new GameSet(null, 0,4);
    List<GameSet> expectedGameSet = List.of(gameSet);
    Game expectedGame = Game.builder()
            .player1("Nadal").player2("Federer")
            .currentGamePlayer1(1).currentGamePlayer2(0)
            .gameSets(expectedGameSet).build();

    assertEquals(expectedGame, resultGame);

  }
}
