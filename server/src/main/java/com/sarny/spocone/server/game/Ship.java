package com.sarny.spocone.server.game;

import com.sarny.spocone.publicclasses.shot.ShotOutcome;

import java.util.*;

/**
 * @author Kamil Rojek
 */
class Ship {
    List<Integer> toHit;
    List<Integer> hit;

    Ship(List<Integer> fields) {
        toHit = new LinkedList<>(fields);
        hit = new ArrayList<>();
    }

    boolean isOnField(int fieldNumber) {
        return toHit.contains(fieldNumber) || hit.contains(fieldNumber);
    }

    ShotOutcome fire(int fieldNumber) {
        if (toHit.contains(fieldNumber)) {
            toHit.remove(Integer.valueOf(fieldNumber)); //I used Integer.valueOf cause fieldNumber is treated as Index
            hit.add(fieldNumber);
            return toHit.isEmpty() ? ShotOutcome.SUNK : ShotOutcome.HIT;
        }
        return ShotOutcome.MISS;
    }
}
