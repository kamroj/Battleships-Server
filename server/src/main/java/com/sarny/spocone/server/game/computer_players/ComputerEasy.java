package com.sarny.spocone.server.game.computer_players;

import com.sarny.spocone.publicclasses.shot.Shot;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Kamil Rojek
 */
public class ComputerEasy implements AI {
    private Set<Integer> shots = new HashSet<>();
    private final int ID;

    public ComputerEasy(int ID) {
        this.ID = ID;
    }

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

        System.out.println("COMPUTER :: SHOOTING " + fieldToShot);
        return fieldToShot;
    }
}


