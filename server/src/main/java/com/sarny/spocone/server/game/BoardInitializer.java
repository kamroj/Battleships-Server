package com.sarny.spocone.server.game;

import com.sarny.spocone.publicclasses.ship.ShipDTO;

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

    ShipDTO placeShip(Ship ship) throws InvalidShipPlacementException {
        if (shipPlacementValidator.validate(ship)) {
            shipPlacementValidator.placeNewShip(ship);
            return ship.asDTO();
        }
        throw new InvalidShipPlacementException("Ship " + ship.toString() + "cannot be placed on the board!");
    }

    Board generateBoard() throws InvalidBoardCreationException {
        if (isBoardReady()) {
            return board;
        }
        throw new InvalidBoardCreationException("Board is not ready yet!");
    }

    boolean isBoardReady() {
        return shipPlacementValidator.allShipsPlaced();
    }
}
