package com.hatim.nexeo.katatennis.gateway;

import com.hatim.nexeo.katatennis.domain.model.Game;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface GameRepositoryH2 extends ReactiveCrudRepository<Game, String> {

    Mono<Game> findById(String idGame);
}
