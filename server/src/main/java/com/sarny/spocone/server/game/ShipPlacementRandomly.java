package com.sarny.spocone.server.game;

import java.util.*;

/**
 * @author Agnieszka Trzewik
 */
public class ShipPlacementRandomly {

    private static final int NUMBER_TO_MOVE_VERTICALLY = 10;
    private static final int NUMBER_TO_MOVE_HORIZONTALLY = 1;
    private static final int BOARD_CAPACITY = 100;
    private static final int MAX_SHIP_LENGTH = 4;

    private List<Ship> randomlyPlacedShips;
    private ShipPlacementValidator shipPlacementValidator;
    private Random random;

    ShipPlacementRandomly(ShipPlacementValidator shipPlacementValidator) {
        randomlyPlacedShips = new ArrayList<>();
        this.shipPlacementValidator = shipPlacementValidator;
        this.random = new Random();
    }

    List<Ship> finishRandomPlacement() {
        randomlyPlacedShips = new ArrayList<>();
        for (int shipLength = MAX_SHIP_LENGTH; shipLength > 0; shipLength--) {
            for (int i = shipPlacementValidator.leftToPlaceOfLength(shipLength); i > 0; i--) {
                placeShipInRandomDirection(shipLength);
            }
        }
        return randomlyPlacedShips;
    }

    void placeAllShipsRandomly() {
        for (int shipLength = MAX_SHIP_LENGTH; shipLength > 0; shipLength--) {
            for (int i = 0; i < numberOfGivenShipsToPlaceOnTheBoard(shipLength); i++) {
                placeShipInRandomDirection(shipLength);
            }
        }
    }

    private void placeShipInRandomDirection(int shipLength) {
        boolean moveHorizontally = random.nextBoolean();
        placeShipRandomly(shipLength,
                moveHorizontally ? NUMBER_TO_MOVE_HORIZONTALLY : NUMBER_TO_MOVE_VERTICALLY);
    }

    private void placeShipRandomly(int shipLength, int numberToMoveForward) {
        Ship ship;
        do {
            List<Integer> fields = new ArrayList<>();
            int field = getRandomFieldOfTheBoard();
            for (int i = 0; i < shipLength; i++) {
                fields.add(field + (i * numberToMoveForward));
            }
            ship = new Ship(fields);
        }
        while (!shipPlacementValidator.validate(ship));
        shipPlacementValidator.placeNewShip(ship);
        randomlyPlacedShips.add(ship);
    }

    private int getRandomFieldOfTheBoard() {
        return random.nextInt(BOARD_CAPACITY);
    }

    private Integer numberOfGivenShipsToPlaceOnTheBoard(int shipLength) {
        return getAllShipsToPlaceOnTheBoard().get(shipLength);
    }

    private Map<Integer, Integer> getAllShipsToPlaceOnTheBoard() {
        Map<Integer, Integer> shipsOfLengthToPlace = new HashMap<>();
        shipsOfLengthToPlace.put(4, 1);
        shipsOfLengthToPlace.put(3, 2);
        shipsOfLengthToPlace.put(2, 3);
        shipsOfLengthToPlace.put(1, 4);
        return shipsOfLengthToPlace;
    }
}
