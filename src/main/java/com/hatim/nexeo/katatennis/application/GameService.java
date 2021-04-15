package com.hatim.nexeo.katatennis.application;

import com.hatim.nexeo.katatennis.application.dto.GameDto;
import com.hatim.nexeo.katatennis.domain.model.PlayerEnum;
import reactor.core.publisher.Mono;

public interface GameService {
     /**
      * Init a new game with players name and scores to zero
      * @param initGameDto
      * @return
      */
     Mono<String> createGame(GameDto initGameDto);

     /**
      * Get Game details by Id
      * @param idGame id existing game
      * @return detail about the game and scores
      */
     Mono<String> getGame(String idGame);

     /**
      * score a point for a player
      * @param idGame
      * @return
      */
     Mono<GameDto> scorePoint(String idGame, PlayerEnum playerEnum);
}
