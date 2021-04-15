package com.hatim.nexeo.katatennis.application.spi;

import com.hatim.nexeo.katatennis.domain.model.Game;
import reactor.core.publisher.Mono;

public interface GameRepository {


    /**
     * Add new game
     * @param game game to persist in database
     * @return the persisted game
     */
    Mono<Game> persist(Game game);

    /**
     * Get an existing game
     * @param idGame
     * @return the persisted game
     */
    Mono<Game> getGameById(String idGame);
}
