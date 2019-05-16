package com.sarny.spocone.server.game;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Kamil Rojek
 */
class BoardInitializer {
    private Board board;
    private ShipPlacementValidator shipPlacementValidator;

    BoardInitializer() {
        this.board = new Board();
        this.shipPlacementValidator = new ShipPlacementValidator.Builder()
                .withGuaranteedMissGenerator(new ShipNeighbouringFieldsGenerator())
                .forBoard(board);
    }

    Ship placeShip(Ship ship) throws InvalidShipPlacementException {
        if (shipPlacementValidator.validate(ship)) {
            shipPlacementValidator.placeNewShip(ship);
            return ship;
        }
        throw new InvalidShipPlacementException("Ship " + ship.toString() + "cannot be placed on the board!");
    }

    Board generateBoard() throws InvalidBoardCreationException {
        if (isBoardReady()) {
            return board;
        }
        throw new InvalidBoardCreationException("Board is not ready yet!");
    }

    Board generateStandardBoard() throws InvalidShipPlacementException {
        //Arrange
                /*
        1 O 2 2 O O 3 O O 3
        O O O O O O 3 O O 3
        1 O O O O O 3 O O 3
        O O O O O O O O O O
        O O O O O O O O O O
        4 4 4 4 O O O O O 2
        O O O O O O O O O 2
        O O O O O O O O O O
        O O O O O O O O 1 O
        2 2 O O O O 1 O O O
         */
        List<Ship> defaultShips = Arrays.asList(
                //One mast
                new Ship(Collections.singletonList(0)),
                new Ship(Collections.singletonList(20)),
                new Ship(Collections.singletonList(88)),
                new Ship(Collections.singletonList(96)),

                //Two masts
                new Ship(Arrays.asList(2, 3)),
                new Ship(Arrays.asList(59, 69)),
                new Ship(Arrays.asList(90, 91)),

                //Three masts
                new Ship(Arrays.asList(6, 16, 26)),
                new Ship(Arrays.asList(9, 19, 29)),

                //Four mast
                new Ship(Arrays.asList(60, 61, 62, 63))
        );

        for (Ship ship: defaultShips) {
            placeShip(ship);
        }

        return board;
    }


    boolean isBoardReady() {
        return shipPlacementValidator.allShipsPlaced();
    }
}
