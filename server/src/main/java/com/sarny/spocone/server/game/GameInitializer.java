package com.sarny.spocone.server.game;

import com.sarny.spocone.publicclasses.ship.ShipDTO;
import com.sarny.spocone.publicclasses.ship.ShipPlacementData;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Class responsible for providing new {@link Game} objects.
 *
 * @author Kamil Rojek
 * @see Game
 */
public class GameInitializer {
    private Map<Integer, BoardInitializer> boardInitializers = new LinkedHashMap<>();
    private final int FIRST_PLAYER_ID;
    private final int SECOND_PLAYER_ID;

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

    public ShipDTO placeShip(int playerID, ShipPlacementData shipData) throws InvalidShipPlacementException {
        Ship ship = new Ship(shipData);
        BoardInitializer boardInitializer = this.boardInitializers.get(playerID);
        boardInitializer.placeShip(ship);
        return ship.asDTO();
    }

    public boolean areBothPlayersDone() {
        return boardInitializers.get(FIRST_PLAYER_ID).isBoardReady() &&
                boardInitializers.get(SECOND_PLAYER_ID).isBoardReady();
    }

    public Game generateGame() throws InvalidBoardCreationException {
        Map<Integer, Board> boardsInGame = new LinkedHashMap<>();

        for (Integer playerID : boardInitializers.keySet()) {
            BoardInitializer boardInitializer = this.boardInitializers.get(playerID);
            boardsInGame.put(playerID, boardInitializer.generateBoard());
        }

        ActiveBoards activeBoards = new ActiveBoards(boardsInGame);
        return new Game(activeBoards, FIRST_PLAYER_ID, SECOND_PLAYER_ID);
    }

    //Temp solution - when random or manual ship placement will be done this method should be removed
    public Game generateStandardGame() throws InvalidShipPlacementException {
        Map<Integer, Board> boardsInGame = new LinkedHashMap<>();

        for (Integer playerID : boardInitializers.keySet()) {
            BoardInitializer boardInitializer = this.boardInitializers.get(playerID);
            boardsInGame.put(playerID, boardInitializer.generateStandardBoard());
        }

        ActiveBoards activeBoards = new ActiveBoards(boardsInGame);
        return new Game(activeBoards, FIRST_PLAYER_ID, SECOND_PLAYER_ID);
    }

}
