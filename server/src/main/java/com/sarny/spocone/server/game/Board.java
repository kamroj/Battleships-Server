package com.sarny.spocone.server.game;

import com.sarny.spocone.publicclasses.shot.ShotOutcome;

import java.util.*;

/**
 * @author Kamil Rojek
 */
class Board {
    private List<Ship> ships;

    Board(List<Ship> ships) {
        this.ships = ships;
    }

    ShotOutcome markShot(int fieldNumber) {
        Ship ship = getShipFromField(fieldNumber);
        if (ship == null) {
            return ShotOutcome.MISS;
        }
        return getShotOutcome(ship, fieldNumber);
    }

    private ShotOutcome getShotOutcome(Ship ship, int fieldNumber) {
        ShotOutcome shotOutcome = ship.fire(fieldNumber);

        switch (shotOutcome) {
            case HIT:
                return ShotOutcome.HIT;
            case SUNK:
                ships.remove(ship);
                return checkIfWin();
            default:
                return ShotOutcome.MISS;
        }
    }

    private ShotOutcome checkIfWin() {
        return ships.isEmpty() ? ShotOutcome.WIN : ShotOutcome.SUNK;
    }

    Ship getShipFromField(int fieldNumber) {
        for (Ship ship : ships) {
            if (ship.isOnField(fieldNumber))
                return ship;
        }
        return null;
    }
}
