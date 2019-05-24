package com.sarny.spocone.server.game.support_class;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

/**
 * Controls players activity during game.
 * When player is inactive longer than 30 seconds, {@link PlayerDisconnectedException} is thrown.
 *
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

    /**
     * Update players latest activity and check if opponent is inactive - if so, throw {@link PlayerDisconnectedException}.
     *
     * @param playerId
     * @throws PlayerDisconnectedException
     */
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
        return playerId == FIRST_PLAYER ? SECOND_PLAYER : FIRST_PLAYER;
    }

    private boolean opponentIsInactive(int opponentId) {
        LocalTime lastActivity = lastPlayerActivity.get(opponentId);
        LocalTime timeoutLimit = lastActivity.plus(30, ChronoUnit.SECONDS);
        LocalTime now = LocalTime.now();
        return now.isAfter(timeoutLimit);
    }
}
