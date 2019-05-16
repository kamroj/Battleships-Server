package com.sarny.spocone.server.game;

/**
 * @author Kamil Rojek
 */
public class InvalidBoardCreationException extends Exception {
    InvalidBoardCreationException(String msg) {
        super(msg);
    }

    InvalidBoardCreationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
