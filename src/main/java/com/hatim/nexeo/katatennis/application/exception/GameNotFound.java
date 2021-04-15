package com.hatim.nexeo.katatennis.application.exception;

/**
 * Throw exception if game not found
 */
public class GameNotFound extends RuntimeException {
    public GameNotFound() {
        super("Error, no game found");
    }
}
