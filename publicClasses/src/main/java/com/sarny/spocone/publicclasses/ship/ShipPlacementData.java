package com.sarny.spocone.publicclasses.ship;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A class which provides information about who and where places a ship on a board.
 *
 * @author Agnieszka Trzewik
 */
public class ShipPlacementData {

    private int playerID;
    private int field;
    private int shipLength;
    @JsonProperty
    private boolean isHorizontally;

    public ShipPlacementData(int playerID, int field, int shipLength, boolean isHorizontally) {
        this.playerID = playerID;
        this.field = field;
        this.shipLength = shipLength;
        this.isHorizontally = isHorizontally;
    }

    public ShipPlacementData() {
    }

    public int getPlayerID() {
        return playerID;
    }

    public int getField() {
        return field;
    }

    public int getShipLength() {
        return shipLength;
    }

    public boolean isHorizontally() {
        return isHorizontally;
    }

    @Override
    public String toString() {
        return "ShipPlacementData{" +
                "playerID=" + playerID +
                ", field=" + field +
                ", shipLength=" + shipLength +
                ", isHorizontally=" + isHorizontally +
                '}';
    }
}
