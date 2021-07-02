package com.hatim.nexeo.katatennis.application;

import com.hatim.nexeo.katatennis.application.dto.GameDto;
import com.hatim.nexeo.katatennis.domain.service.ScoreGameService;
import com.hatim.nexeo.katatennis.domain.model.PlayerEnum;
import com.hatim.nexeo.katatennis.application.exception.GameNotCreated;
import com.hatim.nexeo.katatennis.application.exception.GameNotFound;
import com.hatim.nexeo.katatennis.application.mapper.GameMapper;
import com.hatim.nexeo.katatennis.application.spi.GameRepository;
import com.hatim.nexeo.katatennis.domain.model.Game;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

  private final GameRepository gameRepository;
  private final ScoreGameService scoreGameService;

  @Override
  public Mono<String> createGame(GameDto initGameDto) {
    return Mono.just(initGameDto)
        .map(GameMapper::toDomain)
        .flatMap(gameRepository::persist)
        .map(Game::getId)
        .switchIfEmpty(Mono.error(new GameNotCreated()));
  }

  @Override
  public Mono<String> getGame(String idGame) {
    return this.gameRepository
        .getGameById(idGame)
            .switchIfEmpty(Mono.error(new GameNotFound()))
        .map(game -> scoreGameService.status(game));
  }

  @Override
  public Mono<GameDto> scorePoint(String idGame, PlayerEnum playerEnum) {
    return this.gameRepository
        .getGameById(idGame)
        .map(game -> scoreGameService.score(game, playerEnum))
        .flatMap(gameRepository::persist)
        .map(GameMapper::toAPI);
  }
}
