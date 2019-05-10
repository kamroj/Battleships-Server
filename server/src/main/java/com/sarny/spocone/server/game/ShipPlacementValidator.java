package com.sarny.spocone.server.game;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Wojciech Makiela
 */
class ShipPlacementValidator {

    private final Board board;
    private final ShipNeighbouringFieldsGenerator generator;
    private final Map<Integer, Integer> shipsOfLengthToPlace;

    private ShipPlacementValidator(Map<Integer, Integer> shipsOfLengthToPlace, Board board, ShipNeighbouringFieldsGenerator generator) {
        this.shipsOfLengthToPlace = shipsOfLengthToPlace;
        this.board = board;
        this.generator = generator;
    }

    boolean allShipsPlaced() {
        for (Integer leftToPlace : shipsOfLengthToPlace.values()) {
            if (leftToPlace > 0) {
                return false;
            }
        }
        return true;
    }

    boolean validate(Ship ship) {
        int shipsToPlace = shipsOfLengthToPlace.get(ship.length());
        if (shipsToPlace <= 0) {
            return false;
        }
        Set<Integer> fieldsAroundShip = generator.generateNeighbours(ship);
        fieldsAroundShip.addAll(ship.toHit);

        for (Integer field : fieldsAroundShip) {
            if (board.getShipFromField(field) != null) {
                return false;
            }
        }
        return true;
    }

    void placedNewShip(Ship ship) {
        int previousRemainingShips = shipsOfLengthToPlace.get(ship.length());
        shipsOfLengthToPlace.put(ship.length(), previousRemainingShips - 1);
    }

    static class Builder implements WithShipsOfLength4, WithShipsOfLength3, WithShipsOfLength2, WithShipsOfLength1, WithGuaranteedMissGenerator, BuildShipPlacementValidator {

        private int shipLength4 = 1;
        private int shipLength3 = 2;
        private int shipLength2 = 3;
        private int shipLength1 = 4;
        private ShipNeighbouringFieldsGenerator generator;

        @Override
        public WithShipsOfLength3 withShipsOfLength4(int ships) {
            shipLength4 = ships;
            return this;
        }

        @Override
        public WithShipsOfLength2 withShipsOfLength3(int ships) {
            shipLength3 = ships;
            return this;
        }

        @Override
        public WithShipsOfLength1 withShipsOfLength2(int ships) {
            shipLength2 = ships;
            return this;
        }

        @Override
        public WithGuaranteedMissGenerator withShipsOfLength1(int ships) {
            shipLength1 = ships;
            return this;
        }

        @Override
        public BuildShipPlacementValidator withGuaranteedMissGenerator(ShipNeighbouringFieldsGenerator generator) {
            this.generator = generator;
            return this;
        }

        @Override
        public ShipPlacementValidator forBoard(Board board) {
            HashMap<Integer, Integer> shipsOfLengthToPlace = new HashMap<>();
            shipsOfLengthToPlace.put(4, shipLength4);
            shipsOfLengthToPlace.put(3, shipLength3);
            shipsOfLengthToPlace.put(2, shipLength2);
            shipsOfLengthToPlace.put(1, shipLength1);
            return new ShipPlacementValidator(shipsOfLengthToPlace, board, generator);
        }
    }

    interface WithShipsOfLength4 {
        WithShipsOfLength3 withShipsOfLength4(int ships);
    }

    interface WithShipsOfLength3 {
        WithShipsOfLength2 withShipsOfLength3(int ships);

    }

    interface WithShipsOfLength2 {
        WithShipsOfLength1 withShipsOfLength2(int ships);
    }

    interface WithShipsOfLength1 {
        WithGuaranteedMissGenerator withShipsOfLength1(int ships);
    }

    interface WithGuaranteedMissGenerator {
        BuildShipPlacementValidator withGuaranteedMissGenerator(ShipNeighbouringFieldsGenerator generator);
    }

    interface BuildShipPlacementValidator {
        ShipPlacementValidator forBoard(Board board);
    }
}
