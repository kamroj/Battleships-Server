package com.sarny.spocone.server.game;

import com.sarny.spocone.publicclasses.shot.ShotOutcome;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kamil Rojek
 */
class Board {

    List<Ship> ships;
    List<Ship> sunkShips;

    Board() {
        this.ships = new ArrayList<>();
        sunkShips = new ArrayList<>();
    }

    Board(List<Ship> ships) {
        this.ships = ships;
        sunkShips = new ArrayList<>();
    }

    void placeShip(Ship ship) {
        ships.add(ship);
    }

    ShotOutcome markShot(int fieldNumber) {
        return getShotResult(getShipFromField(fieldNumber), fieldNumber);
    }

    Ship getShipFromField(int fieldNumber) {
        for (Ship ship : ships) {
            if (ship.isOnField(fieldNumber))
                return ship;
        }

        for (Ship ship : sunkShips) {
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
            sunkShips.add(ship);
            return checkIfWin();
        } else {
            return shotOutcome;
        }
    }

    private ShotOutcome checkIfWin() {
        return ships.isEmpty() ? ShotOutcome.WIN : ShotOutcome.SUNK;
    }

}
