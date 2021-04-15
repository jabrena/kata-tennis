package com.hatim.nexeo.katatennis.domain.service;

import com.hatim.nexeo.katatennis.domain.model.Game;
import com.hatim.nexeo.katatennis.domain.model.PlayerEnum;

public interface ScoreGameService {

  /**
   * Score a point for one of the player randomly
   *
   * @param game current game
   * @return Game updated with the new score
   */
  Game score(Game game, PlayerEnum player);

  /**
   * Get the current game Status
   *
   * @param game current game
   * @return deuce/advantage or current score
   */
  String gameStatus(Game game);

  /**
   * get the game Score formatted as (Player1_score,Player2_score)
   *
   * @param gameInit current game
   * @return current score
   */
  String gameScore(Game gameInit);

  /**
   * get the match Status
   *
   * @param gameInit current game
   * @return In Progress or Player wins
   */
  String matchStatus(Game gameInit);

  /**
   * get the match Status
   *
   * @param game current game
   * @return In Progress or Player wins
   */
   String status(Game game);
}
