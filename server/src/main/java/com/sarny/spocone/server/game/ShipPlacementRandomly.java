package com.sarny.spocone.server.game;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @author Agnieszka Trzewik
 */
public class ShipPlacementRandomly {

    private static final int NUMBER_TO_MOVE_VERTICALLY = 10;
    private static final int NUMBER_TO_MOVE_HORIZONTALLY = 1;
    private static final int BOARD_CAPACITY = 100;
    private static final int NUMBER_OF_POSSIBLE_DIRECTIONS = 2;
    private static final int MAX_SHIP_LENGTH = 4;

    private int numberOfShipLength4 = 1;
    private int numberOfShipLength3 = 2;
    private int numberOfShipLength2 = 3;
    private int numberOfShipLength1 = 4;

    private ShipPlacementValidator shipPlacementValidator;
    private Random random;

    @Autowired
    public ShipPlacementRandomly(ShipPlacementValidator shipPlacementValidator) {
        this.shipPlacementValidator = shipPlacementValidator;
        this.random = new Random();
    }

    void placeAllShipsRandomly() {
        for (int shipLength = MAX_SHIP_LENGTH; shipLength > 0; shipLength--) {
            for (int i = 0; i < numberOfGivenShipsToPlaceOnTheBoard(shipLength); i++) {
                placeShipInRandomDirection(shipLength);
            }
        }
    }

    private void placeShipInRandomDirection(int shipLength) {
        int choiceForHorOrVer = random.nextInt(NUMBER_OF_POSSIBLE_DIRECTIONS);
        switch (choiceForHorOrVer) {
            case 0:
                placeShipRandomly(shipLength, NUMBER_TO_MOVE_HORIZONTALLY);
                break;
            case 1:
                placeShipRandomly(shipLength, NUMBER_TO_MOVE_VERTICALLY);
                break;
        }
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
    }

    private int getRandomFieldOfTheBoard() {
        return random.nextInt(BOARD_CAPACITY);
    }

    private Integer numberOfGivenShipsToPlaceOnTheBoard(int shipLength) {
        return getAllShipsToPlaceOnTheBoard().get(shipLength);
    }

    private Map<Integer, Integer> getAllShipsToPlaceOnTheBoard() {
        Map<Integer, Integer> shipsOfLengthToPlace = new HashMap<>();
        shipsOfLengthToPlace.put(4, numberOfShipLength4);
        shipsOfLengthToPlace.put(3, numberOfShipLength3);
        shipsOfLengthToPlace.put(2, numberOfShipLength2);
        shipsOfLengthToPlace.put(1, numberOfShipLength1);
        return shipsOfLengthToPlace;
    }
}
