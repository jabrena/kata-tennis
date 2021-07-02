package com.hatim.nexeo.katatennis.infrastructure.resource;

import com.hatim.nexeo.katatennis.application.GameService;
import com.hatim.nexeo.katatennis.application.dto.GameDto;
import com.hatim.nexeo.katatennis.domain.model.PlayerEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping(value = "/game")
public class TennisGameResource {

    private static final Logger log = LoggerFactory.getLogger(TennisGameResource.class);

    private GameService gameService;

    public TennisGameResource(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * Create a new game
     * @return HTTP 201 with uri in header location
     */
    @PostMapping
    public Mono<ResponseEntity<Void>> createGame(@RequestBody GameDto initGameDto) {
        log.info("Creating game {}", initGameDto);

        return this.gameService.createGame(initGameDto)
                .map(idGame -> ResponseEntity.created(URI.create("/game/" + idGame)).build());
    }

    /**
     * get a game by Id
     * @return HTTP 200,
     */
    @GetMapping("/{idGame}")
    public Mono<ResponseEntity<String>> getGame(@PathVariable(value = "idGame") final String idGame) {
        log.info("Getting game {}", idGame);

        return this.gameService.getGame(idGame)
                .map(ResponseEntity::ok);
    }

    /**
     *
     * score a point for one of the two players
     * @param idGame id existing game to play
     * @param playerEnum PLAYER1, PLAYER2
     * @return HTTP 201 with uri in header location
     */
    @PutMapping("/{idGame}/score/{player}")
    public Mono<ResponseEntity<GameDto>> scorePoint(@PathVariable(value = "idGame") final String idGame, @PathVariable(value = "player") final PlayerEnum playerEnum) {
        log.info("Score a point {}", idGame);

        return this.gameService.scorePoint(idGame, playerEnum)
                .map(ResponseEntity::ok);
    }
}
