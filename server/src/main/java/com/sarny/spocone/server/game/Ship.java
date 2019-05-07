package com.sarny.spocone.server.game;

import com.sarny.spocone.publicclasses.shot.ShotOutcome;
import java.util.*;

/**
 * @author Kamil Rojek
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

    ShotOutcome fire(int fieldNumber) {
        if (toHit.contains(fieldNumber)) {
            toHit.remove(fieldNumber);
            hit.add(fieldNumber);
            return toHit.isEmpty() ? ShotOutcome.SUNK : ShotOutcome.HIT;
        }
        return ShotOutcome.MISS;
    }
}
