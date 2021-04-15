package com.hatim.nexeo.katatennis.domain.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Builder
@EqualsAndHashCode
@Document(collection = "game")
public class Game {

    @Id
    @Getter
    @Setter
    private String id;

    @Getter
    private String player1;

    @Getter
    private String player2;

    @Getter
    @Setter
    private int currentGamePlayer1;

    @Getter
    @Setter
    private int currentGamePlayer2;

    @Getter
    private List<GameSet> gameSets;

    @Getter
    @Setter
    private String matchStatus;
}
