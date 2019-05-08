package com.sarny.spocone.server.game;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Agnieszka Trzewik
 */
class GuaranteedMissGenerator {

    private final int COLUMNS = 10;

    Set<Integer> generateMisses(Ship ship) {
        Set<Integer> misses = new HashSet<>();
        List<Integer> occupiedFields = ship.toHit;
        occupiedFields.addAll(ship.hit);
        for (Integer field : occupiedFields) {
            if (field != (((field / COLUMNS) * COLUMNS) + COLUMNS - 1)) {
                generateMissesFromRightSideOfAField(field, misses, ship.hit);
            }
            if (field != ((field / COLUMNS) * COLUMNS)) {
                generateMissesFromLeftSideOfAField(field, misses, ship.hit);
            }
            generateMissesAboveAndUnderAField(field, misses, ship.hit);

        }
        return misses;
    }

    private void generateMissesAboveAndUnderAField(int field, Set<Integer> misses, List<Integer> ship) {
        addMissIfNotAShip(field - COLUMNS, misses, ship);
        addMissIfNotAShip(field + COLUMNS, misses, ship);
    }

    private void generateMissesFromLeftSideOfAField(int field, Set<Integer> misses, List<Integer> ship) {
        int miss = field - 1;
        generateSideMisses(miss, misses, ship);
    }

    private void generateMissesFromRightSideOfAField(int field, Set<Integer> misses, List<Integer> ship) {
        int miss = field + 1;
        generateSideMisses(miss, misses, ship);
    }

    private void generateSideMisses(int miss, Set<Integer> misses, List<Integer> ship) {
        addMissIfNotAShip(miss - COLUMNS, misses, ship);
        addMissIfNotAShip(miss, misses, ship);
        addMissIfNotAShip(miss + COLUMNS, misses, ship);
    }

    private void addMissIfNotAShip(int miss, Set<Integer> misses, List<Integer> ship) {
        if (!ship.contains(miss)) {
            addMissIfOnABoard(miss, misses);
        }
    }

    private void addMissIfOnABoard(int miss, Set<Integer> misses) {
        if (miss >= 0 && miss < 100) {
            misses.add(miss);
        }
    }
}