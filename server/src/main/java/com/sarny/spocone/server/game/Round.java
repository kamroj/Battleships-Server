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
    private int activePlayerId;
    private int misses = 0;

    Round(ActiveBoards activeBoards, List<Integer> playersIds) {
        this.activeBoards = activeBoards;
        this.activePlayerId = playersIds.get(FIRST_PLAYER_INDEX);
        initializePlayersShotsContainer(playersIds);
    }

    ShotResult handleShot(Shot shot) {
        ShotOutcome shotOutcome = activeBoards.markShot(shot, oppositePlayerId(activePlayerId));
        updateRoundData(shot, shotOutcome);
        return new ShotResult(shotOutcome, shot.getField());
    }

    boolean isRoundOver() {
        return misses == 2;
    }

    ShotsSummary getOpponentsShots(int playerId) {
        int oppositePlayerId = oppositePlayerId(playerId);
        List<ShotResult> shotResults = playersShots.get(oppositePlayerId);
        return new ShotsSummary(shotResults, checkIfLastShotIsWin(shotResults));
    }

    Integer getLastSunkField(int playerId) {
        return playersShots.get(playerId).stream()
                .filter(shotResult -> shotResult.getShotOutcome() == ShotOutcome.SUNK ||
                        shotResult.getShotOutcome() == ShotOutcome.WIN)
                .reduce((first, second) -> second)
                .map(ShotResult::getField)
                .orElse(null);
    }

    boolean isPlayerRound(int playerId) {
        return playerId == activePlayerId;
    }

    private void updateRoundData(Shot shot, ShotOutcome shotOutcome) {
        ShotResult shotResult = new ShotResult(shotOutcome, shot.getField());
        playersShots.get(activePlayerId).add(shotResult);

        if (shotOutcome == ShotOutcome.MISS) {
            activePlayerId = oppositePlayerId(shot.getPlayerId());
            misses++;
        }
    }

    int oppositePlayerId(int currentPlayerId) {
        return playersShots.keySet().stream()
                .filter(id -> id != currentPlayerId)
                .findFirst()
                .get();
    }

    private boolean checkIfLastShotIsWin(List<ShotResult> shotResults) {
        return shotResults.get(shotResults.size() - 1).getShotOutcome() == ShotOutcome.WIN;
    }

    private void initializePlayersShotsContainer(List<Integer> playersIds) {
        playersShots = new LinkedHashMap<>();
        playersIds.forEach(id -> playersShots.put(id, new ArrayList<>()));
    }
}
