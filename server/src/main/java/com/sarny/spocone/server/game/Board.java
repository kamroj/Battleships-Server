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
        return getShotResult(getShipFromField(fieldNumber), fieldNumber);
    }

    Ship getShipFromField(int fieldNumber) {
        for (Ship ship : ships) {
            if (ship.isOnField(fieldNumber))
                return ship;
        }
        return null;
    }

    private ShotOutcome getShotResult(Ship ship, int fieldNumber) {
        if (ship == null)
            return ShotOutcome.MISS;

        ShotOutcome shotOutcome = ship.fire(fieldNumber);

        if (shotOutcome == ShotOutcome.SUNK) {
            ships.remove(ship);
            return checkIfWin();
        } else {
            return shotOutcome;
        }
    }

    private ShotOutcome checkIfWin() {
        return ships.isEmpty() ? ShotOutcome.WIN : ShotOutcome.SUNK;
    }
}
