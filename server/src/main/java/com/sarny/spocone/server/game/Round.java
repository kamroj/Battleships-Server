package com.sarny.spocone.server.game;

import com.sarny.spocone.publicclasses.shot.Shot;
import com.sarny.spocone.publicclasses.shot.ShotOutcome;
import com.sarny.spocone.publicclasses.shot.ShotResult;
import com.sarny.spocone.publicclasses.shot.ShotsSummary;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kamil Rojek
 * @author Agnieszka Trzewik
 */
class Round {

    private static final int FIRST_PLAYER_INDEX = 0;

    private ActiveBoards activeBoards;
    private Map<Integer, List<ShotResult>> playersShots;
    private int activePlayerID;
    private int misses = 0;

    Round(ActiveBoards activeBoards, List<Integer> playersIDs) {
        this.activeBoards = activeBoards;
        this.activePlayerID = playersIDs.get(FIRST_PLAYER_INDEX);
        initializePlayersShotsContainer(playersIDs);
    }

    ShotResult handleShot(Shot shot) {
        ShotOutcome shotOutcome = activeBoards.markShot(shot, oppositePlayerID(activePlayerID));
        updateRoundData(shot, shotOutcome);
        return new ShotResult(shotOutcome, shot.getField());
    }

    boolean isRoundOver() {
        return misses == 2;
    }

    ShotsSummary getOpponentsShots(int playerID) {
        int oppositePlayerID = oppositePlayerID(playerID);
        List<ShotResult> shotResults = playersShots.get(oppositePlayerID);
        return new ShotsSummary(shotResults, checkIfLastShotIsWin(shotResults));
    }

    Integer lastSunkField(int playerID) {
        return playersShots.get(playerID).stream()
                .filter(r -> r.getShotOutcome() == ShotOutcome.SUNK || r.getShotOutcome() == ShotOutcome.WIN)
                .reduce((first, second) -> second)
                .map(ShotResult::getField)
                .orElse(null);
    }

    boolean isPlayerRound(int playerID) {
        return playerID == activePlayerID;
    }

    private void updateRoundData(Shot shot, ShotOutcome shotOutcome) {
        ShotResult shotResult = new ShotResult(shotOutcome, shot.getField());
        playersShots.get(activePlayerID).add(shotResult);

        if (shotOutcome == ShotOutcome.MISS) {
            activePlayerID = oppositePlayerID(shot.getPlayerID());
            misses++;
        }
    }

    int oppositePlayerID(int currentPlayerID) {
        return playersShots.keySet().stream()
                .filter(id -> id != currentPlayerID)
                .findFirst()
                .get();
    }

    private boolean checkIfLastShotIsWin(List<ShotResult> shotResults) {
        return shotResults.get(shotResults.size() - 1).getShotOutcome() == ShotOutcome.WIN;
    }

    private void initializePlayersShotsContainer(List<Integer> playersIDs) {
        playersShots = new LinkedHashMap<>();
        playersIDs.forEach(id -> playersShots.put(id, new ArrayList<>()));
    }
}
