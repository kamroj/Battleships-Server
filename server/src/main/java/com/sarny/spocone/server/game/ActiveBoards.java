package com.sarny.spocone.server.game;

import com.sarny.spocone.publicclasses.shot.Shot;
import com.sarny.spocone.publicclasses.shot.ShotOutcome;

import java.util.Map;

/**
 * @author Kamil Rojek
 */
class ActiveBoards {
    private Map<Integer, Board> boardsInGame;

    ActiveBoards(Map<Integer, Board> boardsInGame) {
        this.boardsInGame = boardsInGame;
    }

    ShotOutcome markShot(Shot shot) {
        Board board = getPlayerBoard(shot.getPlayerID());
        return board.markShot(shot.getField());
    }

    Ship getShipOnField(int fieldNumber, int playerID) {
        return boardsInGame.get(playerID).getShipFromField(fieldNumber);
    }

    private Board getPlayerBoard(int playerID) {
        return boardsInGame.get(playerID);
    }
}
