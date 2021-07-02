package com.hatim.nexeo.katatennis.infrastructure.gateway;

import com.hatim.nexeo.katatennis.application.spi.GameRepository;
import com.hatim.nexeo.katatennis.domain.model.Game;
import com.hatim.nexeo.katatennis.domain.service.GameRepositoryH2;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class GameRepositoryH2Adapter implements GameRepository {

    private GameRepositoryH2 gameRepositoryH2;

    public GameRepositoryH2Adapter(GameRepositoryH2 gameRepositoryH2) {
        this.gameRepositoryH2 = gameRepositoryH2;
    }

    @Override
    public Mono<Game> persist(Game game) {
        return this.gameRepositoryH2.save(game);
    }

    @Override
    public Mono<Game> getGameById(String idGame) {
        return gameRepositoryH2.findById(idGame);
    }

}
