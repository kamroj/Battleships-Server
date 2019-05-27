package com.sarny.spocone.server.game;

import com.sarny.spocone.publicclasses.shot.Shot;
import com.sarny.spocone.publicclasses.shot.ShotResult;
import com.sarny.spocone.publicclasses.shot.ShotsSummary;
import com.sarny.spocone.server.game.support_class.GameTimeoutChecker;
import com.sarny.spocone.server.game.support_class.PlayerDisconnectedException;

import java.util.*;

/**
 * Provides set of functions allowing conducting single game.
 * Package private constructor prevents instantiating Game without using GameInitializer.
 *
 * @author Wojciech Makiela
 * @author Agnieszka Trzewik
 */
public class Game {

    ActiveBoards activeBoards;
    Stack<Round> rounds;
    Round activeRound;
    private List<Integer> playersIDs;
    private GameTimeoutChecker timeoutChecker;

    Game(ActiveBoards activeBoards, int firstPlayerID, int secondPlayerID) {
        this.activeBoards = activeBoards;
        this.playersIDs = new LinkedList<>(Arrays.asList(firstPlayerID, secondPlayerID));
        this.rounds = new Stack<>();
        timeoutChecker = new GameTimeoutChecker(firstPlayerID, secondPlayerID);
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
    public boolean isPlayerRound(int playerID) throws PlayerDisconnectedException {
        timeoutChecker.playerWithIdTookAction(playerID);
        return activeRound.isPlayerRound(playerID);
    }

    /**
     * Returns all opponent {@link ShotResult} in current {@link Round}.
     *
     * @param playerID who needs to check opponent shots
     * @return summary of opponent shots
     */
    public ShotsSummary getOpponentsShots(int playerID) {
        if (isFirstPlayer(playerID)) {
            return summaryForFirstPlayer(playerID);
        }
        return summaryForSecondPlayer(playerID);
    }

    private ShotsSummary summaryForSecondPlayer(int playerID) {
        return activeRound.getOpponentsShots(playerID);
    }

    // History for first player is acquired from previous round
    private ShotsSummary summaryForFirstPlayer(int playerID) {
        return rounds.isEmpty() ? null : rounds.peek().getOpponentsShots(playerID);
    }

    private boolean isFirstPlayer(int playerID) {
        return playersIDs.get(0) == playerID;
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
        int oppositePlayerID = activeRound.oppositePlayerId(playerID);
        Integer fieldNumber = activeRound.getLastSunkField(playerID);
        return activeBoards.getShipOnField(fieldNumber, oppositePlayerID);
    }

    void createNewRound() {
        activeRound = new Round(activeBoards, playersIDs);
    }
}
