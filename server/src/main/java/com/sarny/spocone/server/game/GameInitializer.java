package com.sarny.spocone.server.game;

import com.sarny.spocone.publicclasses.ship.ShipDTO;
import com.sarny.spocone.publicclasses.ship.ShipPlacementData;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Class responsible for providing new {@link Game} objects.
 *
 * @author Kamil Rojek
 * @see Game
 */
public class GameInitializer {
    final int FIRST_PLAYER_ID;
    final int SECOND_PLAYER_ID;
    Map<Integer, BoardInitializer> boardInitializers = new LinkedHashMap<>();

    public GameInitializer(int firstPlayerID, int secondPlayerID) {
        FIRST_PLAYER_ID = firstPlayerID;
        SECOND_PLAYER_ID = secondPlayerID;

        boardInitializers.put(FIRST_PLAYER_ID, new BoardInitializer());
        boardInitializers.put(SECOND_PLAYER_ID, new BoardInitializer());
    }

    public ShipDTO placeShip(int playerID, Ship ship) throws InvalidShipPlacementException {
        BoardInitializer boardInitializer = this.boardInitializers.get(playerID);
        boardInitializer.placeShip(ship);
        return ship.asDTO();
    }

    public List<ShipDTO> placeShip(int playerID, List<Ship> ships) throws InvalidShipPlacementException {
        BoardInitializer boardInitializer = this.boardInitializers.get(playerID);
        List<ShipDTO> shipsDTO = new ArrayList<>();
        for (Ship ship : ships) {
            boardInitializer.placeShip(ship);
            shipsDTO.add(ship.asDTO());
        }
        return shipsDTO;
    }

    public ShipDTO placeShip(int playerID, ShipPlacementData shipData) throws InvalidShipPlacementException {
        Ship ship = new Ship(shipData);
        BoardInitializer boardInitializer = this.boardInitializers.get(playerID);
        boardInitializer.placeShip(ship);
        return ship.asDTO();
    }

    public boolean areBothPlayersDone() {
        return boardInitializers.get(FIRST_PLAYER_ID).isBoardReady()
                && boardInitializers.get(SECOND_PLAYER_ID).isBoardReady();
    }

    public Game generateGame() throws InvalidBoardCreationException {
        Map<Integer, Board> boardsInGame = new LinkedHashMap<>();

        for (Map.Entry<Integer, BoardInitializer> playerID : boardInitializers.entrySet()) {
            BoardInitializer boardInitializer = this.boardInitializers.get(playerID.getKey());
            boardsInGame.put(playerID.getKey(), boardInitializer.generateBoard());
        }

        ActiveBoards activeBoards = new ActiveBoards(boardsInGame);
        return new Game(activeBoards, FIRST_PLAYER_ID, SECOND_PLAYER_ID);
    }

}
