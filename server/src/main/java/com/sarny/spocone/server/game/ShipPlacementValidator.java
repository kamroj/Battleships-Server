package com.sarny.spocone.server.game;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Wojciech Makiela
 */
class ShipPlacementValidator {

    private static final int COLUMNS = 10;
    private static final int BIGGEST_INDEX_OF_BOARD = 99;

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
        return hasValidLength(ship) && isShipOnTheProperPositionOnTheBoard(ship) && isNotPlacedOnOtherShip(ship);
    }

    private boolean isNotPlacedOnOtherShip(Ship ship) {
        Set<Integer> fieldsAroundShip = generator.generateNeighbours(ship);
        fieldsAroundShip.addAll(ship.fieldsToHit);

        for (Integer field : fieldsAroundShip) {
            if (board.getShipFromField(field) != null) {
                return false;
            }
        }
        return true;
    }

    private boolean isShipOnTheProperPositionOnTheBoard(Ship ship) {
        return isShipOfLength1WithProperPosition(ship) || isHorizontalWithValidPosition(ship) || isVerticalWithValidPosition(ship);
    }

    private boolean isVerticalWithValidPosition(Ship ship) {
        return shipIsVertically(ship) && hasValidVerticalPosition(ship);
    }

    private boolean isHorizontalWithValidPosition(Ship ship) {
        return shipIsHorizontally(ship) && hasValidHorizontalPosition(ship);
    }

    private boolean isShipOfLength1WithProperPosition(Ship ship) {
        return (ship.length() == 1) && isOnTheBoard(ship);
    }

    private boolean hasValidLength(Ship ship) {
        int shipsToPlace = shipsOfLengthToPlace.get(ship.length());
        return shipsToPlace > 0;
    }

    void placeNewShip(Ship ship) {
        int previousRemainingShips = shipsOfLengthToPlace.get(ship.length());
        shipsOfLengthToPlace.put(ship.length(), previousRemainingShips - 1);
        board.placeShip(ship);
    }

    private boolean hasValidHorizontalPosition(Ship ship) {
        return isInARow(ship);
    }

    private boolean hasValidVerticalPosition(Ship ship) {
        return isOnTheBoard(ship);
    }

    private boolean shipIsHorizontally(Ship ship) {
        return shipIsNotOneMast(ship) && (ship.fieldsToHit.get(1) - ship.fieldsToHit.get(0) == 1);
    }

    private boolean shipIsVertically(Ship ship) {
        return shipIsNotOneMast(ship) && (ship.fieldsToHit.get(1) - ship.fieldsToHit.get(0) == 10);
    }

    private boolean shipIsNotOneMast(Ship ship) {
        return ship.length() > 1;
    }

    private boolean isInARow(Ship ship) {
        int lastFieldInRow = getLastPossibleFieldInARowForAShip(getFirstFieldOfAShip(ship));
        return getLastFieldOfAShip(ship) <= lastFieldInRow;
    }

    private boolean isOnTheBoard(Ship ship) {
        return getLastFieldOfAShip(ship) <= BIGGEST_INDEX_OF_BOARD;
    }

    private int getLastPossibleFieldInARowForAShip(int firstFieldOfShip) {
        return ((firstFieldOfShip / COLUMNS) * COLUMNS) + COLUMNS - 1;
    }

    private int getFirstFieldOfAShip(Ship ship) {
        return ship.fieldsToHit.get(0);
    }

    private int getLastFieldOfAShip(Ship ship) {
        return ship.fieldsToHit.get(ship.length() - 1);
    }

    int leftToPlaceOfLength(int shipLength) {
        return shipsOfLengthToPlace.get(shipLength);
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
            Map<Integer, Integer> shipsOfLengthToPlace = new HashMap<>();
            shipsOfLengthToPlace.put(4, shipLength4);
            shipsOfLengthToPlace.put(3, shipLength3);
            shipsOfLengthToPlace.put(2, shipLength2);
            shipsOfLengthToPlace.put(1, shipLength1);
            return new ShipPlacementValidator(shipsOfLengthToPlace, board, generator);
        }
    }
}
