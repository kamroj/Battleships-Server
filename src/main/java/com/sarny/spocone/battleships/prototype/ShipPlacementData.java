package com.sarny.spocone.battleships.prototype;

/**
 * @author Makiela Wojciech
 */
class ShipPlacementData {
    int length;
    boolean isVertical;
    int fieldNumber;

    ShipPlacementData(int length, boolean isVertical, int fieldNumber) {
        this.length = length;
        this.isVertical = isVertical;
        this.fieldNumber = fieldNumber;
    }
}
