package com.sarny.spocone.server.game;

/**
 * @author Kamil Rojek
 */
class InvalidBoardCreationException extends Exception {
    InvalidBoardCreationException(String msg) {
        super(msg);
    }

    InvalidBoardCreationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
