package com.sarny.spocone.publicclasses.shot;

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
}
