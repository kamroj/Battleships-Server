package com.sarny.spocone.server.game;

/**
 * @author Kamil Rojek
 */
public class InvalidShipPlacementException extends Exception {
    InvalidShipPlacementException(String msg) {
        super(msg);
    }

    InvalidShipPlacementException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
