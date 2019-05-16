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

    protected ActiveBoards activeBoards;
    protected Stack<Round> rounds;
    protected Round activeRound;
    protected List<Integer> playersIDs;

    Game(ActiveBoards activeBoards, int firstPlayerID, int secondPlayerID) {
        this.activeBoards = activeBoards;
        this.playersIDs = new LinkedList<>(Arrays.asList(firstPlayerID, secondPlayerID));
        this.rounds = new Stack<>();
        createNewRound();
    }

    public List<Integer> getPlayersIDs() {
        return playersIDs;
    }

    /**
     * Takes a {@link Shot} and performs it. If both contestants make their moves in one round,
     * then create a new {@link Round}.
     *
     * @param shot made by the player
     * @return {@link ShotResult} of shot executed on opponents {@link Board}
     */
    public ShotResult handleShot(Shot shot) {
        ShotResult shotResult = activeRound.handleShot(shot);

        if (activeRound.isRoundOver()) {
            rounds.add(activeRound);
            createNewRound();
        }

        return shotResult;
    }

    /**
     * Informs about whether or not the player with the given ID can shot.
     *
     * @param playerID who needs to be checked
     * @return current active players ID is equal to provided playerID
     */
    public boolean isPlayerRound(int playerID) {
        return activeRound.isPlayerRound(playerID);
    }

    /**
     * Returns all opponent {@link ShotResult} in current {@link Round}.
     *
     * @param playerID who needs to check opponent shots
     * @return summary of opponent shots
     */
    public ShotsSummary getOpponentsShots(int playerID) {
        return activeRound.getOpponentsShots(playerID);
    }

    /**
     * Generates guaranteed misses for the last ship sunk by the player with given ID.
     *
     * @param playerID who needs to get guaranteed misses
     * @return set of neighbouring fields
     */
    public Set<Integer> getGuaranteedMisses(int playerID) {
        Ship ship = getLastSunkShip(playerID);
        return new ShipNeighbouringFieldsGenerator().generateNeighbours(ship);
    }

    private Ship getLastSunkShip(int playerID) {
        int oppositePlayerID = activeRound.oppositePlayerID(playerID);
        Integer fieldNumber = activeRound.lastSunkField(playerID);
        return activeBoards.getShipOnField(fieldNumber, oppositePlayerID);
    }

    protected void createNewRound() {
        activeRound = new Round(activeBoards, playersIDs);
    }
}
