package com.hatim.nexeo.katatennis.domain;

import com.hatim.nexeo.katatennis.domain.model.PlayerEnum;
import com.hatim.nexeo.katatennis.domain.model.Game;
import com.hatim.nexeo.katatennis.domain.model.GameSet;
import com.hatim.nexeo.katatennis.domain.service.ScoreGameService;
import com.hatim.nexeo.katatennis.domain.service.ScoreGameServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScoreGameServiceTest {

  @Test
  void scorePoint_newGame_shouldUpdatePointForPlayer1() {
    ScoreGameService scoreGameService = new ScoreGameServiceImpl();

    List<GameSet> gameSets = new ArrayList<>();
    gameSets.add(GameSet.builder().gameSetPlayer1(0).gameSetPlayer2(0).build());
    Game gameInit =
        Game.builder().currentGamePlayer1(0).currentGamePlayer2(0).gameSets(gameSets).build();
    Game gameResult = scoreGameService.score(gameInit, PlayerEnum.PLAYER1);

    assertNotNull(gameResult);
    assertEquals(1, gameResult.getCurrentGamePlayer1());
    assertEquals(0, gameResult.getCurrentGamePlayer2());
  }

  @Test
  void scorePoint_newGame_shouldUpdatePointForPlayer2() {
    ScoreGameService scoreGameService = new ScoreGameServiceImpl();

    List<GameSet> gameSets = new ArrayList<>();
    gameSets.add(GameSet.builder().gameSetPlayer1(0).gameSetPlayer2(0).build());
    Game gameInit =
        Game.builder().currentGamePlayer1(0).currentGamePlayer2(0).gameSets(gameSets).build();
    Game gameResult = scoreGameService.score(gameInit, PlayerEnum.PLAYER2);

    assertNotNull(gameResult);
    assertEquals(0, gameResult.getCurrentGamePlayer1());
    assertEquals(1, gameResult.getCurrentGamePlayer2());
  }

  @Test
  void scorePoint_newGame_shouldUpdateSetPlayer1AndResetCurrentGamePoint() {
    ScoreGameService scoreGameService = new ScoreGameServiceImpl();

    List<GameSet> gameSets = new ArrayList<>();
    gameSets.add(GameSet.builder().gameSetPlayer1(6).gameSetPlayer2(1).build());
    gameSets.add(GameSet.builder().gameSetPlayer1(1).gameSetPlayer2(1).build());

    Game gameInit =
        Game.builder().currentGamePlayer1(3).currentGamePlayer2(1).gameSets(gameSets).build();
    Game gameResult = scoreGameService.score(gameInit, PlayerEnum.PLAYER1);

    assertNotNull(gameResult);
    assertEquals(0, gameResult.getCurrentGamePlayer1());
    assertEquals(0, gameResult.getCurrentGamePlayer2());

    List<GameSet> gameSetsExpected = new ArrayList<>();
    gameSetsExpected.add(GameSet.builder().gameSetPlayer1(6).gameSetPlayer2(1).build());
    gameSetsExpected.add(GameSet.builder().gameSetPlayer1(2).gameSetPlayer2(1).build());

    assertEquals(gameSetsExpected, gameResult.getGameSets());
  }

  @Test
  void scorePoint_deuce_shouldUpdateGamePoint() {
    ScoreGameService scoreGameService = new ScoreGameServiceImpl();
    // player1
    List<GameSet> gameSets = new ArrayList<>();
    gameSets.add(GameSet.builder().gameSetPlayer1(6).gameSetPlayer2(1).build());
    gameSets.add(GameSet.builder().gameSetPlayer1(1).gameSetPlayer2(1).build());

    Game gameInit =
        Game.builder().currentGamePlayer1(3).currentGamePlayer2(3).gameSets(gameSets).build();
    Game gameResult = scoreGameService.score(gameInit, PlayerEnum.PLAYER1);

    assertNotNull(gameResult);
    assertEquals(4, gameResult.getCurrentGamePlayer1());
    assertEquals(3, gameResult.getCurrentGamePlayer2());

    List<GameSet> gameSetsExpected = new ArrayList<>();
    gameSetsExpected.add(GameSet.builder().gameSetPlayer1(6).gameSetPlayer2(1).build());
    gameSetsExpected.add(GameSet.builder().gameSetPlayer1(1).gameSetPlayer2(1).build());

    assertEquals(gameSetsExpected, gameResult.getGameSets());

    // player2
    List<GameSet> gameSetsPlayer2 = new ArrayList<>();
    gameSetsPlayer2.add(GameSet.builder().gameSetPlayer1(6).gameSetPlayer2(1).build());
    gameSetsPlayer2.add(GameSet.builder().gameSetPlayer1(1).gameSetPlayer2(1).build());

    Game gameInitPlayer2 =
        Game.builder()
            .currentGamePlayer1(4)
            .currentGamePlayer2(2)
            .gameSets(gameSetsPlayer2)
            .build();
    Game gameResultPlayer2 = scoreGameService.score(gameInitPlayer2, PlayerEnum.PLAYER2);

    assertNotNull(gameResultPlayer2);
    assertEquals(4, gameResultPlayer2.getCurrentGamePlayer1());
    assertEquals(3, gameResultPlayer2.getCurrentGamePlayer2());

    List<GameSet> gameSetsPlayer2Expected = new ArrayList<>();
    gameSetsPlayer2Expected.add(GameSet.builder().gameSetPlayer1(6).gameSetPlayer2(1).build());
    gameSetsPlayer2Expected.add(GameSet.builder().gameSetPlayer1(1).gameSetPlayer2(1).build());

    assertEquals(gameSetsPlayer2Expected, gameResultPlayer2.getGameSets());
  }

  @Test
  void scorePoint_newGame_shouldUpdateSetPlayer2AndResetCurrentGamePoint() {
    ScoreGameService scoreGameService = new ScoreGameServiceImpl();

    List<GameSet> gameSets = new ArrayList<>();
    gameSets.add(GameSet.builder().gameSetPlayer1(6).gameSetPlayer2(1).build());
    gameSets.add(GameSet.builder().gameSetPlayer1(1).gameSetPlayer2(1).build());

    Game gameInit =
        Game.builder().currentGamePlayer1(1).currentGamePlayer2(3).gameSets(gameSets).build();
    Game gameResult = scoreGameService.score(gameInit, PlayerEnum.PLAYER2);

    assertNotNull(gameResult);
    assertEquals(0, gameResult.getCurrentGamePlayer1());
    assertEquals(0, gameResult.getCurrentGamePlayer2());

    List<GameSet> gameSetsExpected = new ArrayList<>();
    gameSetsExpected.add(GameSet.builder().gameSetPlayer1(6).gameSetPlayer2(1).build());
    gameSetsExpected.add(GameSet.builder().gameSetPlayer1(1).gameSetPlayer2(2).build());

    assertEquals(gameSetsExpected, gameResult.getGameSets());
  }

  @Test
  void scorePoint_Player2leads_shouldUpdateGamePlayer2() {
    ScoreGameService scoreGameService = new ScoreGameServiceImpl();

    List<GameSet> gameSets = new ArrayList<>();
    gameSets.add(GameSet.builder().gameSetPlayer1(6).gameSetPlayer2(1).build());
    gameSets.add(GameSet.builder().gameSetPlayer1(1).gameSetPlayer2(1).build());

    Game gameInit =
        Game.builder().currentGamePlayer1(4).currentGamePlayer2(6).gameSets(gameSets).build();
    Game gameResult = scoreGameService.score(gameInit, PlayerEnum.PLAYER2);

    assertNotNull(gameResult);
    assertEquals(0, gameResult.getCurrentGamePlayer1());
    assertEquals(0, gameResult.getCurrentGamePlayer2());

    List<GameSet> gameSetsExpected = new ArrayList<>();
    gameSetsExpected.add(GameSet.builder().gameSetPlayer1(6).gameSetPlayer2(1).build());
    gameSetsExpected.add(GameSet.builder().gameSetPlayer1(1).gameSetPlayer2(2).build());

    assertEquals(gameSetsExpected, gameResult.getGameSets());
  }

  @Test
  void scorePoint_TieBreak_shouldContinueGame() {
    ScoreGameService scoreGameService = new ScoreGameServiceImpl();

    List<GameSet> gameSets = new ArrayList<>();
    gameSets.add(GameSet.builder().gameSetPlayer1(6).gameSetPlayer2(1).build());
    gameSets.add(GameSet.builder().gameSetPlayer1(6).gameSetPlayer2(6).build());

    Game gameInit =
        Game.builder().currentGamePlayer1(3).currentGamePlayer2(1).gameSets(gameSets).build();
    Game gameResult = scoreGameService.score(gameInit, PlayerEnum.PLAYER1);

    assertNotNull(gameResult);
    // assertEquals(0, gameResult.getCurrentGamePlayer1());
    // assertEquals(0, gameResult.getCurrentGamePlayer2());

    List<GameSet> gameSetsExpected = new ArrayList<>();
    gameSetsExpected.add(GameSet.builder().gameSetPlayer1(6).gameSetPlayer2(1).build());
    gameSetsExpected.add(GameSet.builder().gameSetPlayer1(7).gameSetPlayer2(6).build());

    assertEquals(gameSetsExpected, gameResult.getGameSets());
  }

  @Test
  void scorePoint_player1LeadingGame_shouldPlayer1winSet() {
    ScoreGameService scoreGameService = new ScoreGameServiceImpl();

    List<GameSet> gameSets = new ArrayList<>();
    gameSets.add(GameSet.builder().gameSetPlayer1(6).gameSetPlayer2(1).build());
    gameSets.add(GameSet.builder().gameSetPlayer1(5).gameSetPlayer2(2).build());

    Game gameInit =
        Game.builder().currentGamePlayer1(3).currentGamePlayer2(1).gameSets(gameSets).build();
    Game gameResult = scoreGameService.score(gameInit, PlayerEnum.PLAYER1);

    assertNotNull(gameResult);
    assertEquals(0, gameResult.getCurrentGamePlayer1());
    assertEquals(0, gameResult.getCurrentGamePlayer2());

    List<GameSet> gameSetsExpected = new ArrayList<>();
    gameSetsExpected.add(GameSet.builder().gameSetPlayer1(6).gameSetPlayer2(1).build());
    gameSetsExpected.add(GameSet.builder().gameSetPlayer1(6).gameSetPlayer2(2).build());
    gameSetsExpected.add(GameSet.builder().gameSetPlayer1(0).gameSetPlayer2(0).build());

    assertEquals(gameSetsExpected, gameResult.getGameSets());
  }

  @Test
  void scorePoint_player2LeadingGame_shouldPlayer2winSet() {
    ScoreGameService scoreGameService = new ScoreGameServiceImpl();

    List<GameSet> gameSets = new ArrayList<>();
    gameSets.add(GameSet.builder().gameSetPlayer1(6).gameSetPlayer2(1).build());
    gameSets.add(GameSet.builder().gameSetPlayer1(2).gameSetPlayer2(5).build());

    Game gameInit =
        Game.builder().currentGamePlayer1(1).currentGamePlayer2(3).gameSets(gameSets).build();
    Game gameResult = scoreGameService.score(gameInit, PlayerEnum.PLAYER2);

    assertNotNull(gameResult);
    assertEquals(0, gameResult.getCurrentGamePlayer1());
    assertEquals(0, gameResult.getCurrentGamePlayer2());

    List<GameSet> gameSetsExpected = new ArrayList<>();
    gameSetsExpected.add(GameSet.builder().gameSetPlayer1(6).gameSetPlayer2(1).build());
    gameSetsExpected.add(GameSet.builder().gameSetPlayer1(2).gameSetPlayer2(6).build());
    gameSetsExpected.add(GameSet.builder().gameSetPlayer1(0).gameSetPlayer2(0).build());

    assertEquals(gameSetsExpected, gameResult.getGameSets());
  }

  @Test
  void scorePoint_player1LeadingGameAndSet_shouldPlayer1winMatch() {
    ScoreGameService scoreGameService = new ScoreGameServiceImpl();

    List<GameSet> gameSets = new ArrayList<>();
    gameSets.add(GameSet.builder().gameSetPlayer1(6).gameSetPlayer2(4).build());
    gameSets.add(GameSet.builder().gameSetPlayer1(2).gameSetPlayer2(6).build());
    gameSets.add(GameSet.builder().gameSetPlayer1(6).gameSetPlayer2(1).build());
    gameSets.add(GameSet.builder().gameSetPlayer1(6).gameSetPlayer2(2).build());

    Game gameInit =
        Game.builder().currentGamePlayer1(1).currentGamePlayer2(3).gameSets(gameSets).build();
    Game gameResult = scoreGameService.score(gameInit, PlayerEnum.PLAYER1);

    assertNotNull(gameResult);

    List<GameSet> gameSetsExpected = new ArrayList<>();
    gameSetsExpected.add(GameSet.builder().gameSetPlayer1(6).gameSetPlayer2(4).build());
    gameSetsExpected.add(GameSet.builder().gameSetPlayer1(2).gameSetPlayer2(6).build());
    gameSetsExpected.add(GameSet.builder().gameSetPlayer1(6).gameSetPlayer2(1).build());
    gameSetsExpected.add(GameSet.builder().gameSetPlayer1(6).gameSetPlayer2(2).build());

    assertEquals(gameSetsExpected, gameResult.getGameSets());
    assertEquals("Player1 wins", gameResult.getMatchStatus());
  }

  @Test
  void scorePoint_player2LeadingGameAndSet_shouldPlayer2winMatch() {
    ScoreGameService scoreGameService = new ScoreGameServiceImpl();

    List<GameSet> gameSets = new ArrayList<>();
    gameSets.add(GameSet.builder().gameSetPlayer1(4).gameSetPlayer2(6).build());
    gameSets.add(GameSet.builder().gameSetPlayer1(2).gameSetPlayer2(6).build());
    gameSets.add(GameSet.builder().gameSetPlayer1(6).gameSetPlayer2(1).build());
    gameSets.add(GameSet.builder().gameSetPlayer1(2).gameSetPlayer2(5).build());

    Game gameInit =
        Game.builder().currentGamePlayer1(1).currentGamePlayer2(3).gameSets(gameSets).build();
    Game gameResult = scoreGameService.score(gameInit, PlayerEnum.PLAYER2);

    assertNotNull(gameResult);

    List<GameSet> gameSetsExpected = new ArrayList<>();
    gameSetsExpected.add(GameSet.builder().gameSetPlayer1(4).gameSetPlayer2(6).build());
    gameSetsExpected.add(GameSet.builder().gameSetPlayer1(2).gameSetPlayer2(6).build());
    gameSetsExpected.add(GameSet.builder().gameSetPlayer1(6).gameSetPlayer2(1).build());
    gameSetsExpected.add(GameSet.builder().gameSetPlayer1(2).gameSetPlayer2(6).build());

    assertEquals(gameSetsExpected, gameResult.getGameSets());
    assertEquals("Player2 wins", gameResult.getMatchStatus());
  }

  @Test
  void gameStatus_newGame_shouldReturncurentScore() {
    ScoreGameService scoreGameService = new ScoreGameServiceImpl();

    Game gameInit = Game.builder().currentGamePlayer1(0).currentGamePlayer2(0).build();
    String gameStatus = scoreGameService.gameStatus(gameInit);

    assertEquals("0-0", gameStatus);
  }

  @Test
  void gameStatus_player2Scorefirst_shouldReturncurentScore() {
    ScoreGameService scoreGameService = new ScoreGameServiceImpl();

    Game gameInit = Game.builder().currentGamePlayer1(0).currentGamePlayer2(1).build();
    String gameStatus = scoreGameService.gameStatus(gameInit);

    assertEquals("0-15", gameStatus);
  }

  @Test
  void gameStatus_player1Scorefirst_shouldReturncurentScore() {
    ScoreGameService scoreGameService = new ScoreGameServiceImpl();

    Game gameInit = Game.builder().currentGamePlayer1(1).currentGamePlayer2(0).build();
    String gameStatus = scoreGameService.gameStatus(gameInit);

    assertEquals("15-0", gameStatus);
  }

  @Test
  void gameStatus_bothPlayersScore_shouldReturncurentScore() {
    ScoreGameService scoreGameService = new ScoreGameServiceImpl();

    Game gameInit = Game.builder().currentGamePlayer1(1).currentGamePlayer2(1).build();
    String gameStatus = scoreGameService.gameStatus(gameInit);

    assertEquals("15-15", gameStatus);
  }

  @Test
  void gameStatus_player1Score30Player2Score40_shouldReturncurentScore() {
    ScoreGameService scoreGameService = new ScoreGameServiceImpl();

    Game gameInit = Game.builder().currentGamePlayer1(2).currentGamePlayer2(3).build();
    String gameStatus = scoreGameService.gameStatus(gameInit);

    assertEquals("30-40", gameStatus);
  }

  @Test
  void scorePoint_existingGame_shouldupdatePointForPlayer1() {
    ScoreGameService scoreGameService = new ScoreGameServiceImpl();

    List<GameSet> gameSets = new ArrayList<>();
    gameSets.add(GameSet.builder().gameSetPlayer1(0).gameSetPlayer2(0).build());
    Game gameInit =
        Game.builder().currentGamePlayer1(1).currentGamePlayer2(1).gameSets(gameSets).build();
    Game gameResult = scoreGameService.score(gameInit, PlayerEnum.PLAYER1);

    assertNotNull(gameResult);
    assertEquals(2, gameResult.getCurrentGamePlayer1());
    assertEquals(1, gameResult.getCurrentGamePlayer2());
  }

  @Test
  void gameStatus_bothPlayersHaveScore40_shouldReturnDeuce() {
    ScoreGameService scoreGameService = new ScoreGameServiceImpl();

    Game gameInit = Game.builder().currentGamePlayer1(3).currentGamePlayer2(3).build();
    String gameStatus = scoreGameService.gameStatus(gameInit);

    assertEquals("deuce", gameStatus);
  }

  @Test
  void gameStatus_player1ScoreAfterDeuce_shouldReturnAdvantage() {
    ScoreGameService scoreGameService = new ScoreGameServiceImpl();

    Game gameInit = Game.builder().currentGamePlayer1(4).currentGamePlayer2(3).build();
    String gameStatus = scoreGameService.gameStatus(gameInit);

    assertEquals("advantage", gameStatus);

    // Case game point greater than 5
    Game gameInitCase2 = Game.builder().currentGamePlayer1(6).currentGamePlayer2(5).build();
    String gameStatusCase2 = scoreGameService.gameStatus(gameInitCase2);

    assertEquals("advantage", gameStatusCase2);
  }

  @Test
  void gameStatus_player2ScoreAfterDeuce_shouldReturnAdvantage() {
    ScoreGameService scoreGameService = new ScoreGameServiceImpl();

    Game gameInit = Game.builder().currentGamePlayer1(3).currentGamePlayer2(4).build();
    String gameStatus = scoreGameService.gameStatus(gameInit);

    assertEquals("advantage", gameStatus);

    // Case game point greater than 5
    Game gameInitCase2 = Game.builder().currentGamePlayer1(5).currentGamePlayer2(6).build();
    String gameStatusCase2 = scoreGameService.gameStatus(gameInitCase2);

    assertEquals("advantage", gameStatusCase2);
  }

  @Test
  void gameStatus_WrongScore_shouldthrowException() {
    ScoreGameService scoreGameService = new ScoreGameServiceImpl();

    Game gameInit = Game.builder().currentGamePlayer1(5).currentGamePlayer2(3).build();

    Exception exception =
        assertThrows(IllegalArgumentException.class, () -> scoreGameService.gameStatus(gameInit));

    assertNotNull(exception);
    assertEquals("Illegal score: 5", exception.getMessage());

    // Case game point player2 greater than 4, player1 less with more than one point

    Game gameInitCase2 = Game.builder().currentGamePlayer1(3).currentGamePlayer2(5).build();

    Exception exceptionCase2 =
        assertThrows(
            IllegalArgumentException.class, () -> scoreGameService.gameStatus(gameInitCase2));

    assertNotNull(exceptionCase2);
    assertEquals("Illegal score: 5", exceptionCase2.getMessage());
  }

  @Test
  void gameScore_newGame_shouldReturnScore() {
    ScoreGameService scoreGameService = new ScoreGameServiceImpl();

    List<GameSet> gameSets = new ArrayList<>();
    gameSets.add(GameSet.builder().gameSetPlayer1(0).gameSetPlayer2(0).build());
    Game gameInit = Game.builder().gameSets(gameSets).build();

    String gameScore = scoreGameService.gameScore(gameInit);

    assertEquals("(0-0)", gameScore);
  }

  @Test
  void gameScore_secondSet_shouldReturnScore() {
    ScoreGameService scoreGameService = new ScoreGameServiceImpl();

    List<GameSet> gameSets = new ArrayList<>();
    gameSets.add(GameSet.builder().gameSetPlayer1(6).gameSetPlayer2(1).build());
    gameSets.add(GameSet.builder().gameSetPlayer1(7).gameSetPlayer2(5).build());
    gameSets.add(GameSet.builder().gameSetPlayer1(1).gameSetPlayer2(0).build());
    Game gameInit = Game.builder().gameSets(gameSets).build();

    String gameScore = scoreGameService.gameScore(gameInit);

    assertEquals("(6-1)(7-5)(1-0)", gameScore);
  }

  @Test
  void matchStatus_newGame_shouldReturnInProgress() {
    ScoreGameService scoreGameService = new ScoreGameServiceImpl();

    List<GameSet> gameSets = new ArrayList<>();
    gameSets.add(GameSet.builder().gameSetPlayer1(0).gameSetPlayer2(0).build());
    Game gameInit = Game.builder().gameSets(gameSets).matchStatus("in progress").build();

    String matchStatus = scoreGameService.matchStatus(gameInit);

    assertEquals("in progress", matchStatus);
  }

  @Test
  void currentStatus_newGame_shouldReturnGameDetail() {
    ScoreGameService scoreGameService = new ScoreGameServiceImpl();

    List<GameSet> gameSets = new ArrayList<>();
    gameSets.add(GameSet.builder().gameSetPlayer1(0).gameSetPlayer2(2).build());
    Game gameInit =
        Game.builder()
            .player1("Nadal")
            .player2("Federer")
            .currentGamePlayer1(0)
            .currentGamePlayer2(0)
            .gameSets(gameSets)
            .build();

    String currentStatus = scoreGameService.status(gameInit);

    String expectedStatus =
        "Player 1: Nadal\n"
            + "Player 2: Federer\n"
            + "Score: (0-2)\n"
            + "Current game status: 0-0\n"
            + "Match status: In Progress\n";
    assertEquals(expectedStatus, currentStatus);
  }
}
