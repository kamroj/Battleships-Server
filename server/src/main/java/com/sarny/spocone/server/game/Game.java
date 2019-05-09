package com.sarny.spocone.server.game;

import com.sarny.spocone.publicclasses.shot.Shot;
import com.sarny.spocone.publicclasses.shot.ShotResult;
import com.sarny.spocone.publicclasses.shot.ShotsSummary;

import java.util.*;

/**
 * Provides set of functions allowing conducting single game
 * Package private constructor prevents instantiating Game without using GameInitializer
 *
 * @author Wojciech Makiela
 * @author Agnieszka Trzewik
 */
public class Game {

    private ActiveBoards activeBoards;
    private Stack<Round> rounds;
    private Round activeRound;
    private List<Integer> playersIDs;

    Game(ActiveBoards activeBoards, int firstPlayerID, int secondPlayerID) {
        this.activeBoards = activeBoards;
        this.playersIDs = new LinkedList<>(Arrays.asList(firstPlayerID, secondPlayerID));
        this.rounds = new Stack<>();
        createNewRound();
    }

    public ShotResult handleShot(Shot shot) {
        ShotResult shotResult = activeRound.handleShot(shot);

        if (activeRound.isRoundOver()) {
            rounds.add(activeRound);
            createNewRound();
        }

        return shotResult;
    }

    public boolean isPlayerRound(int playerID) {
        return activeRound.isPlayerRound(playerID);
    }

    public ShotsSummary getOpponentsShots(int playerID) {
        return activeRound.getOpponentsShots(playerID);
    }

    public Set<Integer> getGuaranteedMisses(int playerID) {
        Ship ship = getLasSunkShip(playerID);
        return new GuaranteedMissGenerator().generateMisses(ship);
    }

    private Ship getLasSunkShip(int playerID) {
        int oppositePlayerID = activeRound.oppositePlayerID(playerID);
        Integer fieldNumber = activeRound.lastSunkField(playerID);
        return activeBoards.getShipOnField(fieldNumber, oppositePlayerID);
    }

    private void createNewRound() {
        activeRound = new Round(activeBoards, playersIDs);
    }
}
