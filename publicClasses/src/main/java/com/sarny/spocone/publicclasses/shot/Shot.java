package com.sarny.spocone.publicclasses.shot;

import java.util.Objects;

/**
 * A class which provides information about who and where was shooting.
 *
 * @author Agnieszka Trzewik
 */
public class Shot {

    private int playerId;
    private int field;
    private int gameId;

    public Shot(int playerId, int field) {
        this.playerId = playerId;
        this.field = field;
    }

    public Shot() {
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getField() {
        return field;
    }

    public int getGameId() {
        return gameId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shot shot = (Shot) o;
        return playerId == shot.playerId &&
                field == shot.field;
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerId, field);
    }
}
