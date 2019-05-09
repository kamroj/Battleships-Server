package com.sarny.spocone.server.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Hard coded for demo. Will be deleted once ShipPlacementController is implemented.
 *
 * @author Wojciech Makiela
 */
public class StubRegister {
    public Integer player1Id = null;
    public Integer player2Id = null;
    private Board forP1 = new Board(new ArrayList<>(Arrays.asList(
            new Ship(new ArrayList<>(Arrays.asList(1, 2, 3, 4))),
            new Ship(new ArrayList<>(Arrays.asList(31, 32))),
            new Ship(new ArrayList<>(Arrays.asList(20, 21, 22)))
    )));

    private Board forP2 = new Board(new ArrayList<>(Arrays.asList(
            new Ship(new ArrayList<>(Arrays.asList(1, 10, 20, 30))),
            new Ship(new ArrayList<>(Arrays.asList(3, 13, 23))),
            new Ship(new ArrayList<>(Arrays.asList(6, 16)))
    )));

    public void registerPlayer(int id) {
        if (player1Id == null) {
            player1Id = id;
        } else if (player2Id == null) {
            player2Id = id;
        }
    }

    public boolean isRegistrationOver() {
        return player1Id != null && player2Id != null;
    }

    public Game finalizeCreation() {
        Map<Integer, Board> playerBoardMap = new HashMap<>();
        playerBoardMap.put(player1Id, forP1);
        playerBoardMap.put(player2Id, forP2);
        ActiveBoards activeBoards = new ActiveBoards(playerBoardMap);
        return new Game(activeBoards, player1Id, player2Id);
    }
}
