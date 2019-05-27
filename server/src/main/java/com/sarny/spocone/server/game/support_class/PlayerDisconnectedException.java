package com.sarny.spocone.server.game.support_class;

/**
 * @author Wojciech Makiela
 */
public class PlayerDisconnectedException extends Exception {

    public final int disconnectedPlayerId;

    public PlayerDisconnectedException(int disconnectedPlayerId) {
        this.disconnectedPlayerId = disconnectedPlayerId;
    }
}
