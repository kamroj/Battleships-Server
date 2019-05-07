package com.sarny.spocone.server.game;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Wojciech Makiela
 */
class ShipPlacementValidator {

    private Map<Integer, Integer> shipsOfLengthToPlace;

    private ShipPlacementValidator(HashMap<Integer, Integer> shipsOfLengthToPlace) {
        this.shipsOfLengthToPlace = shipsOfLengthToPlace;
    }

    static class Builder implements WithShipsOfLength4, WithShipsOfLength3, WithShipsOfLength2, WithShipsOfLength1, BuildShipPlacementValidator{

        private int length4 = 1;
        private int length3 = 2;
        private int length2 = 3;
        private int length1 = 4;

        @Override
        public WithShipsOfLength3 withShipsOfLength4(int ships) {
            length4 = ships;
            return this;
        }

        @Override
        public WithShipsOfLength2 withShipsOfLength3(int ships) {
            length3 = ships;
            return this;
        }

        @Override
        public WithShipsOfLength1 withShipsOfLength2(int ships) {
            length2 = ships;
            return this;
        }

        @Override
        public BuildShipPlacementValidator withShipsOfLength1(int ships) {
            length1 = ships;
            return this;
        }

        @Override
        public ShipPlacementValidator build() {
            HashMap<Integer, Integer> shipsOfLengthToPlace = new HashMap<>();
            shipsOfLengthToPlace.put(4, length4);
            shipsOfLengthToPlace.put(3, length3);
            shipsOfLengthToPlace.put(2, length2);
            shipsOfLengthToPlace.put(1, length1);
            return new ShipPlacementValidator(shipsOfLengthToPlace);
        }
    }

    private interface WithShipsOfLength4 {
        WithShipsOfLength3 withShipsOfLength4(int ships);
    }


    private interface WithShipsOfLength3 {
        WithShipsOfLength2 withShipsOfLength3(int ships);

    }


    private interface WithShipsOfLength2 {
        WithShipsOfLength1 withShipsOfLength2(int ships);
    }


    private interface WithShipsOfLength1 {
        BuildShipPlacementValidator withShipsOfLength1(int ships);
    }

    private interface BuildShipPlacementValidator {
        ShipPlacementValidator build();
    }
}
