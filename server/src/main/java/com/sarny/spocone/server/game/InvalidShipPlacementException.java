package com.sarny.spocone.server.game;

/**
 * @author Kamil Rojek
 */
class InvalidShipPlacementException extends Exception {
    InvalidShipPlacementException(String msg) {
        super(msg);
    }

    InvalidShipPlacementException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
