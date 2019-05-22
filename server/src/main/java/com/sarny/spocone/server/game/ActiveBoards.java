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

    ShotOutcome markShot(Shot shot, int oppositePlayerID) {
        Board board = getPlayerBoard(oppositePlayerID);
        ShotOutcome shotOutcome = board.markShot(shot.getField());
        board.toString();
        return shotOutcome;
    }

    Ship getShipOnField(int fieldNumber, int playerID) {
        return boardsInGame.get(playerID).getShipFromField(fieldNumber);
    }

    private Board getPlayerBoard(int playerID) {
        return boardsInGame.get(playerID);
    }
}
