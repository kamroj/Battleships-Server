package com.sarny.spocone.publicclasses.shot;

import java.util.Objects;

/**
 * A class which provides information about who and where was shooting.
 *
 * @author Agnieszka Trzewik
 */
public class Shot {

    private int playerID;
    private int field;

    public Shot(int playerID, int field) {
        this.playerID = playerID;
        this.field = field;
    }

    public Shot() {
    }

    public int getPlayerID() {
        return playerID;
    }

    public int getField() {
        return field;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shot shot = (Shot) o;
        return playerID == shot.playerID &&
                field == shot.field;
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerID, field);
    }
}
