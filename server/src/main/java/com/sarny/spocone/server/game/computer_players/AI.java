package com.sarny.spocone.server.game.computer_players;

import com.sarny.spocone.publicclasses.shot.Shot;

/**
 * Base of AI requirements implementation.
 * @author Kamil Rojek
 */
public abstract class AI {

    private static int nextId = 0;

    public static Integer generateID() {
        return nextId++;
    }

    public abstract Shot generateShot();

    public abstract Integer getID();
}
