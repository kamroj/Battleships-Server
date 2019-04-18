package com.sarny.spocone.battleships.prototype;

import org.springframework.stereotype.Component;

/**
 * @author Makiela Wojciech
 */
@Component
class Board {

    private char[] symbols = new char[100];

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
        }
    }

    private void placeHorizontally(ShipPlacementData ship) {
        for (int i = ship.fieldNumber; i < ship.fieldNumber + ship.length; i++) {
            symbols[i] = 's';
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < symbols.length; i++) {
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

