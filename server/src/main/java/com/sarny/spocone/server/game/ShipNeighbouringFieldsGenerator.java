package com.sarny.spocone.server.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Agnieszka Trzewik
 */
class ShipNeighbouringFieldsGenerator {

    private static final int COLUMNS = 10;

    Set<Integer> generateNeighbours(Ship ship) {
        Set<Integer> neighbours = new HashSet<>();
        List<Integer> occupiedFields = getOccupiedFields(ship);

        for (Integer field : occupiedFields) {
            if (field != (((field / COLUMNS) * COLUMNS) + COLUMNS - 1)) {
                generateNeighboursFromRightSideOfAField(field, neighbours, ship.hit);
            }
            if (field != ((field / COLUMNS) * COLUMNS)) {
                generateNeighboursFromLeftSideOfAField(field, neighbours, ship.hit);
            }
            generateNeighboursAboveAndUnderAField(field, neighbours, ship.hit);

        }
        return neighbours;
    }

    private void generateNeighboursAboveAndUnderAField(int field, Set<Integer> neighbours, List<Integer> ship) {
        addNeighbourIfNotAShip(field - COLUMNS, neighbours, ship);
        addNeighbourIfNotAShip(field + COLUMNS, neighbours, ship);
    }

    private void generateNeighboursFromLeftSideOfAField(int field, Set<Integer> neighbours, List<Integer> ship) {
        int neighbour = field - 1;
        generateSideNeighbours(neighbour, neighbours, ship);
    }

    private void generateNeighboursFromRightSideOfAField(int field, Set<Integer> neighbours, List<Integer> ship) {
        int neighbour = field + 1;
        generateSideNeighbours(neighbour, neighbours, ship);
    }

    private void generateSideNeighbours(int neighbour, Set<Integer> neighbours, List<Integer> ship) {
        addNeighbourIfNotAShip(neighbour - COLUMNS, neighbours, ship);
        addNeighbourIfNotAShip(neighbour, neighbours, ship);
        addNeighbourIfNotAShip(neighbour + COLUMNS, neighbours, ship);
    }

    private void addNeighbourIfNotAShip(int neighbour, Set<Integer> neighbours, List<Integer> ship) {
        if (!ship.contains(neighbour)) {
            addNeighbourIfOnABoard(neighbour, neighbours);
        }
    }

    private void addNeighbourIfOnABoard(int neighbour, Set<Integer> neighbours) {
        if (neighbour >= 0 && neighbour < 100) {
            neighbours.add(neighbour);
        }
    }

    private List<Integer> getOccupiedFields(Ship ship) {
        List<Integer> occupiedFields = new ArrayList<>();
        occupiedFields.addAll(ship.toHit);
        occupiedFields.addAll(ship.hit);
        return occupiedFields;
    }
}