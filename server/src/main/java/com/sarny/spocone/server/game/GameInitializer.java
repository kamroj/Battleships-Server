package com.sarny.spocone.server.game;

import com.sarny.spocone.publicclasses.ship.ShipDTO;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Class responsible for providing new {@link Game} objects.
 *
 * @author Kamil Rojek
 * @see Game
 */
public class GameInitializer {
    private Map<Integer, BoardInitializer> boardInitializators = new LinkedHashMap<>();
    private final int FIRST_PLAYER_ID;
    private final int SECOND_PLAYER_ID;

    GameInitializer(int firstPlayerID, int secondPlayerID) {
        FIRST_PLAYER_ID = firstPlayerID;
        SECOND_PLAYER_ID = secondPlayerID;

        boardInitializators.put(FIRST_PLAYER_ID, new BoardInitializer());
        boardInitializators.put(SECOND_PLAYER_ID, new BoardInitializer());
    }

    ShipDTO placeShip(int playerID, Ship ship) throws InvalidShipPlacementException {
        BoardInitializer boardInitializer = boardInitializators.get(playerID);
        boardInitializer.placeShip(ship);
        return ship.asDTO();
    }

    boolean areBothPlayersDone() {
        return boardInitializators.get(FIRST_PLAYER_ID).isBoardReady() &&
                boardInitializators.get(SECOND_PLAYER_ID).isBoardReady();
    }

    Game generateGame() throws InvalidBoardCreationException {
        Map<Integer, Board> boardsInGame = new LinkedHashMap<>();

        for (Integer playerID : boardInitializators.keySet()) {
            BoardInitializer boardInitializer = boardInitializators.get(playerID);
            boardsInGame.put(playerID, boardInitializer.generateBoard());
        }

        ActiveBoards activeBoards = new ActiveBoards(boardsInGame);
        return new Game(activeBoards, FIRST_PLAYER_ID, SECOND_PLAYER_ID);
    }

    //Temp solution - when random or manual ship placement will be done this method should be removed
    Game generateStandardGame() throws InvalidShipPlacementException {
        Map<Integer, Board> boardsInGame = new LinkedHashMap<>();

        for (Integer playerID : boardInitializators.keySet()) {
            BoardInitializer boardInitializer = boardInitializators.get(playerID);
            boardsInGame.put(playerID, boardInitializer.generateStandardBoard());
        }

        ActiveBoards activeBoards = new ActiveBoards(boardsInGame);
        return new Game(activeBoards, FIRST_PLAYER_ID, SECOND_PLAYER_ID);
    }

}
