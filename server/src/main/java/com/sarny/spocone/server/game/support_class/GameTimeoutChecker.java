package com.sarny.spocone.server.game.support_class;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Wojciech Makiela
 */
public class GameTimeoutChecker {

    private Map<Integer, LocalTime> lastPlayerActivity = new HashMap<>();
    private final int FIRST_PLAYER;
    private final int SECOND_PLAYER;

    public GameTimeoutChecker(int firstPlayerId, int secondPlayerId) {
        FIRST_PLAYER = firstPlayerId;
        SECOND_PLAYER = secondPlayerId;
        updatePlayersActivity(FIRST_PLAYER);
        updatePlayersActivity(SECOND_PLAYER);
    }

    public void playerWithIdTookAction(int playerId) throws PlayerDisconnectedException {
        updatePlayersActivity(playerId);
        int opponentId = getOpponentId(playerId);
        if (opponentIsInactive(opponentId)) {
            throw new PlayerDisconnectedException(opponentId);
        }
    }

    private void updatePlayersActivity(int playerId) {
        lastPlayerActivity.put(playerId, LocalTime.now());
    }

    private int getOpponentId(int playerId) {
        if (playerId == FIRST_PLAYER) {
            return SECOND_PLAYER;
        }
        return FIRST_PLAYER;
    }

    private boolean opponentIsInactive(int opponentId) {
        LocalTime lastActivity = lastPlayerActivity.get(opponentId);
        LocalTime timeoutLimit = lastActivity.plus(30, ChronoUnit.SECONDS);
        LocalTime now = LocalTime.now();
        return now.isAfter(timeoutLimit);
    }
}
