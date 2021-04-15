package com.hatim.nexeo.katatennis.resource;

import com.hatim.nexeo.katatennis.application.dto.GameDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class TennisGameResourceTestIT {

  private static final String URI_GAME = "/game";
  @Autowired private WebTestClient webTestClient;

  @Autowired private TennisGameResource profilesResource;

  @Test
  void should_create_new_game() {

    GameDto initGameDto = GameDto.builder().player1("Nadal").player2("Federer").build();

    this.webTestClient
        .post()
        .uri(URI_GAME)
        .bodyValue(initGameDto)
        .exchange()
        .expectStatus()
        .isEqualTo(HttpStatus.CREATED)
        .expectHeader()
        .exists("Location")
        .expectHeader()
        .valueMatches("Location", URI_GAME + "/.*")
        .expectBody(Void.class);
  }

  //TODO Tests

}
