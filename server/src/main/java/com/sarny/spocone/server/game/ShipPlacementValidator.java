package com.sarny.spocone.server.game;

import java.util.*;

/**
 * @author Wojciech Makiela
 */
class ShipPlacementValidator {

    private final Board board;
    private final GuaranteedMissGenerator generator;
    private Map<Integer, Integer> shipsOfLengthToPlace;

    private ShipPlacementValidator(HashMap<Integer, Integer> shipsOfLengthToPlace, Board board, GuaranteedMissGenerator generator) {
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
        Set<Integer> fieldsAroundShip = generator.generateMisses(ship);
        fieldsAroundShip.addAll(ship.toHit);

        for (Integer field : fieldsAroundShip) {
            if (board.getShipFromField(field) != null) {
                return false;
            }
        }
        return true;
    }

    static class Builder implements WithShipsOfLength4, WithShipsOfLength3, WithShipsOfLength2, WithShipsOfLength1, WithGuaranteedMissGenerator, BuildShipPlacementValidator {

        private int length4 = 1;
        private int length3 = 2;
        private int length2 = 3;
        private int length1 = 4;
        private GuaranteedMissGenerator generator;

        @Override
        public WithShipsOfLength3 withShipsOfLength4(int ships) {
            length4 = ships;
            return this;
        }

        @Override
        public WithShipsOfLength2 withShipsOfLength3(int ships) {
            length3 = ships;
            return this;
        }

        @Override
        public WithShipsOfLength1 withShipsOfLength2(int ships) {
            length2 = ships;
            return this;
        }

        @Override
        public WithGuaranteedMissGenerator withShipsOfLength1(int ships) {
            length1 = ships;
            return this;
        }

        @Override
        public BuildShipPlacementValidator withGuaranteedMissGenerator(GuaranteedMissGenerator generator) {
            this.generator = generator;
            return this;
        }

        @Override
        public ShipPlacementValidator forBoard(Board board) {
            HashMap<Integer, Integer> shipsOfLengthToPlace = new HashMap<>();
            shipsOfLengthToPlace.put(4, length4);
            shipsOfLengthToPlace.put(3, length3);
            shipsOfLengthToPlace.put(2, length2);
            shipsOfLengthToPlace.put(1, length1);
            return new ShipPlacementValidator(shipsOfLengthToPlace, board, generator);
        }
    }

    private interface WithShipsOfLength4 {
        WithShipsOfLength3 withShipsOfLength4(int ships);
    }

    private interface WithShipsOfLength3 {
        WithShipsOfLength2 withShipsOfLength3(int ships);

    }

    private interface WithShipsOfLength2 {
        WithShipsOfLength1 withShipsOfLength2(int ships);
    }

    private interface WithShipsOfLength1 {
        WithGuaranteedMissGenerator withShipsOfLength1(int ships);
    }

    private interface WithGuaranteedMissGenerator {
        BuildShipPlacementValidator withGuaranteedMissGenerator(GuaranteedMissGenerator generator);
    }

    private interface BuildShipPlacementValidator {
        ShipPlacementValidator forBoard(Board board);
    }
}
