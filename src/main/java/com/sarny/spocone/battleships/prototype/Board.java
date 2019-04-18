package com.sarny.spocone.battleships.prototype;

import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author Makiela Wojciech
 */
@Component
class Board {

    private char[] symbols = new char[100];
    private List<Integer> fields = new ArrayList<>(100);

    void placeShip(ShipPlacementData ship) {
        if (ship.isVertical) {
            placeVertically(ship);
        } else {
            placeHorizontally(ship);
        }
    }

    private void placeVertically(ShipPlacementData ship) {
        for (int i = 0; i < ship.length; i++) {
            int symbolIndex = ship.fieldNumber + 10 * i;
            symbols[symbolIndex] = 's';
            fields.add(symbolIndex);
        }
    }

    private void placeHorizontally(ShipPlacementData ship) {
        for (int i = ship.fieldNumber; i < ship.fieldNumber + ship.length; i++) {
            symbols[i] = 's';
            fields.add(i);
        }
    }

    List<Integer> getFieldsWhereShipIsPlaced() {
        return fields;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i < symbols.length; i++) {
            if (i % 10 == 0) {
                builder.append("\n");
            }
            builder.append(symbols[i]);
        }
        return builder.toString();
    }

    String markShot(int shot) {
        if (symbols[shot] == 's') {
            symbols[shot] = 'x';
            return "hit";
        } else {
            symbols[shot] = 'o';
            return "miss";
        }
    }
}

