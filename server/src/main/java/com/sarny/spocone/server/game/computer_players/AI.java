package com.sarny.spocone.server.game.computer_players;

import com.sarny.spocone.publicclasses.shot.Shot;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Kamil Rojek
 */
public interface AI {
    Shot generateShot();
    Integer getID();

    static Integer generateID() {
        return ThreadLocalRandom.current().nextInt(9999, 999999999);
    }
}
