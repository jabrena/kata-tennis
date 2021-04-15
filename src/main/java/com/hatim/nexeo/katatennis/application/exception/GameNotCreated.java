package com.hatim.nexeo.katatennis.application.exception;

/**
 * Throw exception if game not created
 */
public class GameNotCreated extends RuntimeException {
    public GameNotCreated() {
        super("Error creating new game");
    }
}
