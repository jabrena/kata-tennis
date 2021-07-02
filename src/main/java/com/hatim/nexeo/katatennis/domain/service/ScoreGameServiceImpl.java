package com.hatim.nexeo.katatennis.domain.service;

import com.hatim.nexeo.katatennis.domain.model.PlayerEnum;
import com.hatim.nexeo.katatennis.domain.model.Game;
import com.hatim.nexeo.katatennis.domain.model.GameSet;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ScoreGameServiceImpl implements ScoreGameService {

  @Override
  public Game score(Game game, PlayerEnum player) {
    switch (player) {
      case PLAYER1:
        game.setCurrentGamePlayer1(game.getCurrentGamePlayer1() + 1);
        break;
      case PLAYER2:
        game.setCurrentGamePlayer2(game.getCurrentGamePlayer2() + 1);
        break;
      default:
        throw new IllegalArgumentException("Illegal player: " + player);
    }

    // check if one of the player wins the game, update the Set by the new score
    checkGameWinner(game);

    // check if one of the player wins the Set, update the match status if a player wins the match
    checkSetWinner(game);

    return game;
  }

  private void checkGameWinner(Game game) {
    if (game.getCurrentGamePlayer1() >= 4
        && game.getCurrentGamePlayer1() >= game.getCurrentGamePlayer2() + 2) {
      // update player1 current set by one point
      game.getGameSets()
          .get(game.getGameSets().size() - 1)
          .setGameSetPlayer1(
              game.getGameSets().get(game.getGameSets().size() - 1).getGameSetPlayer1() + 1);
      // reinit current score for new set
      game.setCurrentGamePlayer1(0);
      game.setCurrentGamePlayer2(0);
    } else if (game.getCurrentGamePlayer2() >= 4
        && game.getCurrentGamePlayer2() >= game.getCurrentGamePlayer1() + 2) {
      // update player1 current set by one point
      game.getGameSets()
          .get(game.getGameSets().size() - 1)
          .setGameSetPlayer2(
              game.getGameSets().get(game.getGameSets().size() - 1).getGameSetPlayer2() + 1);
      // reinit current score for new set
      game.setCurrentGamePlayer1(0);
      game.setCurrentGamePlayer2(0);
    }
  }

  private void checkSetWinner(Game game) {
    //get current game Set
    GameSet currentGameSet = game.getGameSets().get(game.getGameSets().size() - 1);
    if (isPlayer1WinsTheSet(currentGameSet)) {
      if (isPlayer1WinsTheMatch(game)) {
        game.setMatchStatus("Player1 wins");
      } else {
        // update match by adding new game Set
        GameSet gameSet = new GameSet(null, 0,0);
        game.getGameSets().add(gameSet);
        // reinit current score for new set
        game.setCurrentGamePlayer1(0);
        game.setCurrentGamePlayer2(0);
      }
    } else if (isPlayer2winsTheSet(currentGameSet)) {
      if (isPlayer2WinsTheMatch(game)) {
        game.setMatchStatus("Player2 wins");
      } else {
        // update match by adding new game Set
        GameSet gameSet = new GameSet(null, 0,0);
        game.getGameSets().add(gameSet);
        // reinit current score for new set
        game.setCurrentGamePlayer1(0);
        game.setCurrentGamePlayer2(0);
      }
    }
  }

  private boolean isPlayer1WinsTheSet(GameSet currentGameSet) {
    //should have a score of 6 games or greater, and lead by 2 games
    return currentGameSet.getGameSetPlayer1() >= 6
            && currentGameSet.getGameSetPlayer1() >= currentGameSet.getGameSetPlayer2() + 2;
  }

  private boolean isPlayer2winsTheSet(GameSet currentGameSet) {
    //should have a score of 6 games or greater, and lead by 2 games
    return currentGameSet.getGameSetPlayer2() >= 6
        && currentGameSet.getGameSetPlayer2() >= currentGameSet.getGameSetPlayer1() + 2;
  }

  private boolean isPlayer1WinsTheMatch(Game game) {
    //should wins 3 sets
    return game.getGameSets().stream()
            .filter(gameSet -> gameSet.getGameSetPlayer1() > gameSet.getGameSetPlayer2())
            .count()
        >= 3;
  }

  private boolean isPlayer2WinsTheMatch(Game game) {
    //should wins 3 sets
    return game.getGameSets().stream()
            .filter(gameSet -> gameSet.getGameSetPlayer2() > gameSet.getGameSetPlayer1())
            .count() >= 3;
  }

  public String gameStatus(Game game) {
    if (game.getCurrentGamePlayer1() >= 3
        && game.getCurrentGamePlayer1() == game.getCurrentGamePlayer2()) {
      return "deuce";
    } else if (game.getCurrentGamePlayer1() >= 4
        && game.getCurrentGamePlayer1() == game.getCurrentGamePlayer2() + 1) {
      //advantage player1
      return "advantage";
    } else if (game.getCurrentGamePlayer2() >= 4
        && game.getCurrentGamePlayer2() == game.getCurrentGamePlayer1() + 1) {
      //advantage player2
      return "advantage";
    } else {
      return translateScore(game.getCurrentGamePlayer1())
          + "-"
          + translateScore(game.getCurrentGamePlayer2());
    }
  }

  private String translateScore(int score) {
    //display the score as 0, 15, 30, 40
    switch (score) {
      case 0:
        return "0";
      case 1:
        return "15";
      case 2:
        return "30";
      case 3:
        return "40";
      default:
        throw new IllegalArgumentException("Illegal score: " + score);
    }
  }

  @Override
  public String gameScore(Game gameInit) {
    if(gameInit.getGameSets().isEmpty()){
      return "(0-0)";
    }
    return gameInit.getGameSets().stream()
        .map(gameSet -> "(" + gameSet.getGameSetPlayer1() + "-" + gameSet.getGameSetPlayer2() + ")")
        .collect(Collectors.joining());
  }

  @Override
  public String matchStatus(Game gameInit) {
    if(gameInit.getMatchStatus() == null){
      return "In Progress";
    }

    return gameInit.getMatchStatus();
  }

  @Override
  public String status(Game game) {
    StringBuilder status = new StringBuilder();

    status.append("Player 1: ").append(game.getPlayer1()).append("\n");
    status.append("Player 2: ").append(game.getPlayer2()).append("\n");
    status.append("Score: ").append(gameScore(game)).append("\n");
    status.append("Current game status: ").append(gameStatus(game)).append("\n");
    status.append("Match status: ").append(matchStatus(game)).append("\n");

    return status.toString();
  }
}
