package com.hatim.nexeo.katatennis.application;

import com.hatim.nexeo.katatennis.application.dto.GameDto;
import com.hatim.nexeo.katatennis.application.dto.MatchDto;
import com.hatim.nexeo.katatennis.application.dto.GameSetDto;
import com.hatim.nexeo.katatennis.domain.service.ScoreGameService;
import com.hatim.nexeo.katatennis.domain.model.PlayerEnum;
import com.hatim.nexeo.katatennis.application.spi.GameRepository;
import com.hatim.nexeo.katatennis.domain.model.Game;
import com.hatim.nexeo.katatennis.domain.model.GameSet;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@SpringBootTest
public class GameServiceTest {

  @MockBean private GameRepository gameRepository;

  @MockBean private ScoreGameService scoreGameService;

  @Test
  void createNewGame_playersSet_shouldCreateNewGame() {

    GameDto initGameDto = GameDto.builder().player1("Nadal").player2("Federer").build();

    String idGame = "1";

    Mono<Game> gameResult = Mono.just(Game.builder().id(idGame).build());
    Mockito.when(this.gameRepository.persist(Mockito.any(Game.class))).thenReturn(gameResult);
    final ArgumentCaptor<Game> argument = ArgumentCaptor.forClass(Game.class);

    GameService gameService = new GameServiceImpl(gameRepository, scoreGameService);
    Mono<String> createdGameId = gameService.createGame(initGameDto);

    assertNotNull(createdGameId);
    StepVerifier.create(createdGameId).expectNextMatches(idGame::equals).expectComplete().verify();

    Mockito.verify(gameRepository, Mockito.times(1)).persist(argument.capture());
    assertNotNull(argument.getValue());
    assertEquals(initGameDto.getPlayer1(), argument.getValue().getPlayer1());
    assertEquals(initGameDto.getPlayer2(), argument.getValue().getPlayer2());
    assertNotNull(argument.getValue().getGameSets());
    assertEquals(1, argument.getValue().getGameSets().size());
    assertEquals(0, argument.getValue().getGameSets().get(0).getGameSetPlayer1());
    assertEquals(0, argument.getValue().getGameSets().get(0).getGameSetPlayer2());
  }

  @Test
  void createNewGame_playersEmpty_shouldThrowGameWrongException() {

    Mockito.when(this.gameRepository.persist(Mockito.any(Game.class))).thenReturn(Mono.empty());

    GameService gameService = new GameServiceImpl(gameRepository, scoreGameService);
    Mono<String> createdGameId =
        gameService.createGame(GameDto.builder().player1("Nadal").player2("Federer").build());
    StepVerifier.create(createdGameId).expectErrorMessage("Error creating new game").verify();
  }

  @Test
  void getGame_gameExist_shouldReturnGame() {

    String idGame = "1";

    List<GameSet> gameSets = new ArrayList<>();
    gameSets.add(GameSet.builder().gameSetPlayer1(0).gameSetPlayer2(2).build());

    Mono<Game> gameResult =
        Mono.just(
            Game.builder()
                .player1("Nadal")
                .player2("Federer")
                .currentGamePlayer1(0)
                .currentGamePlayer2(0)
                .gameSets(gameSets)
                .matchStatus("In Progress")
                .build());

    Mockito.when(this.gameRepository.getGameById(Mockito.eq(idGame))).thenReturn(gameResult);

    String expectedString =
            "Player 1: Nadal\n"
                    + "Player 2: Federer\n"
                    + "Score: (0-2)\n"
                    + "Current game status: 0-0\n"
                    + "Match status: In Progress\n";
    Mockito.when(
            this.scoreGameService.status(Mockito.any(Game.class)))
            .thenReturn(expectedString);

    GameService gameService = new GameServiceImpl(gameRepository, scoreGameService);
    Mono<String> foundGame = gameService.getGame(idGame);

    assertNotNull(foundGame);


    StepVerifier.create(foundGame).expectNext(expectedString).expectComplete().verify();
  }

  @Test
  void getGame_noGameExist_shouldThrowGameNotFoundException() {

    Mockito.when(this.gameRepository.getGameById(Mockito.anyString())).thenReturn(Mono.empty());

    GameService gameService = new GameServiceImpl(gameRepository, scoreGameService);
    Mono<String> gameDtoMono = gameService.getGame("0");
    StepVerifier.create(gameDtoMono).expectErrorMessage("Error, no game found").verify();
  }

  @Test
  void scorePoint_gameExist_shouldReturnGameScore() {

    String idGame = "1";

    Mono<Game> gameResultDataBase =
        Mono.just(
            Game.builder()
                .player1("Nadal")
                .player2("Federer")
                .currentGamePlayer1(0)
                .currentGamePlayer2(0)
                .gameSets(List.of())
                .build());
    Mockito.when(this.gameRepository.getGameById(Mockito.eq(idGame)))
        .thenReturn(gameResultDataBase);

    List<GameSet> gameSets = new ArrayList<>();
    gameSets.add(GameSet.builder().gameSetPlayer1(0).gameSetPlayer2(4).build());

    Game gameResult =
        Game.builder()
            .player1("Nadal")
            .player2("Federer")
            .currentGamePlayer1(1)
            .currentGamePlayer2(0)
            .gameSets(gameSets)
            .build();
    Mockito.when(
            this.scoreGameService.score(Mockito.any(Game.class), Mockito.eq(PlayerEnum.PLAYER1)))
        .thenReturn(gameResult);

    Mono<Game> gameResultUpdatedDataBase =
            Mono.just(gameResult);
    Mockito.when(
            this.gameRepository.persist(Mockito.any(Game.class)))
            .thenReturn(gameResultUpdatedDataBase);

    GameService gameService = new GameServiceImpl(gameRepository, scoreGameService);
    Mono<GameDto> gameScore = gameService.scorePoint(idGame, PlayerEnum.PLAYER1);

    assertNotNull(gameScore);

    List<GameSetDto> gameSetDtoList = new ArrayList<>();
    gameSetDtoList.add(GameSetDto.builder().gameSetPlayer1(0).gameSetPlayer2(4).build());

    MatchDto scorePlayer =
        MatchDto.builder()
            .gameSets(gameSetDtoList)
            .currentGamePlayer1(1)
            .currentGamePlayer2(0)
            .build();

    GameDto expectedGame =
        GameDto.builder().player1("Nadal").player2("Federer").match(scorePlayer).build();

    StepVerifier.create(gameScore).expectNext(expectedGame).expectComplete().verify();
  }
}
