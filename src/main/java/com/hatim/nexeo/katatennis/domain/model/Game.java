package com.hatim.nexeo.katatennis.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "game")
@Data
@AllArgsConstructor
@Builder
public class Game {

    @Id
    private String id;
    private String player1;
    private String player2;
    private int currentGamePlayer1;
    private int currentGamePlayer2;
    private List<GameSet> gameSets;
    private String matchStatus;
}
