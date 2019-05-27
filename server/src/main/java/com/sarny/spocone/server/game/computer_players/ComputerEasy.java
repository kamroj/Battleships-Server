package com.sarny.spocone.server.game.computer_players;

import com.sarny.spocone.publicclasses.shot.Shot;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Basic AI implementation - randomly shoot till miss.
 *
 * @author Kamil Rojek
 */
public class ComputerEasy extends AI {
    private final int ID;
    private Set<Integer> shots = new HashSet<>();

    public ComputerEasy(int ID) {
        this.ID = ID;
    }

    /**
     * Generates random shot on board 10x10.
     *
     * @return {@link Shot}
     */
    @Override
    public Shot generateShot() {
        int fieldToShot = generateFieldToShot();
        return new Shot(ID, fieldToShot);
    }

    @Override
    public Integer getID() {
        return ID;
    }

    private Integer generateFieldToShot() {
        int fieldToShot;

        do {
            fieldToShot = ThreadLocalRandom.current().nextInt(0, 100);
        } while (shots.contains(fieldToShot));

        shots.add(fieldToShot);
        return fieldToShot;
    }
}


