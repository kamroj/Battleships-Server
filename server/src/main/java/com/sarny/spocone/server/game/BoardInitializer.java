package com.sarny.spocone.server.game;

import com.sarny.spocone.publicclasses.ship.ShipDTO;

import java.util.List;
import java.util.stream.Collectors;

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
        throw new InvalidShipPlacementException("Ship " + ship.toString()
                + "cannot be placed on the board!");
    }

    List<ShipDTO> placeShipsRandomly() {
        ShipPlacementRandomly shipPlacementRandomly = new ShipPlacementRandomly(shipPlacementValidator);
        List<Ship> ships = shipPlacementRandomly.finishRandomPlacement();
        return ships.stream().map(Ship::asDTO).collect(Collectors.toList());
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
