package com.sarny.spocone.server.game;


import java.util.*;

/**
 * @author Wojciech Makiela
 */
class Ship {
    List<Integer> toHit;
    List<Integer> hit;

    Ship(Integer ... fields) {
        toHit = List.of(fields);
        hit = new ArrayList<>();
    }

    boolean isOnField(int fieldNumber) {
        return toHit.contains(fieldNumber) || hit.contains(fieldNumber);
    }
}
