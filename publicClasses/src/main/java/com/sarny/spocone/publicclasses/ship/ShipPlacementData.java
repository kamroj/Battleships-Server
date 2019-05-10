package com.sarny.spocone.publicclasses.ship;

/**
 * A class which provides information about who and where places a ship on a board.
 *
 * @author Agnieszka Trzewik
 */
public class ShipPlacementData {

    private int playerID;
    private int field;
    private int shipLength;
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
}
