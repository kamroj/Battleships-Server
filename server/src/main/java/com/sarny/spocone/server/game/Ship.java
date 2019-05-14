package com.sarny.spocone.server.game;

import com.sarny.spocone.publicclasses.ship.ShipDTO;
import com.sarny.spocone.publicclasses.shot.ShotOutcome;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Kamil Rojek
 */
class Ship {

    List<Integer> toHit;
    List<Integer> hit;

    Ship(List<Integer> fields) {
        toHit = fields;
        hit = new ArrayList<>();
    }

    boolean isOnField(int fieldNumber) {
        return toHit.contains(fieldNumber) || hit.contains(fieldNumber);
    }

    ShotOutcome fire(int fieldNumber) {
        if (toHit.contains(fieldNumber)) {
            toHit.remove(Integer.valueOf(fieldNumber)); //Used Integer.valueOf cause fieldNumber is treated as Index
            hit.add(fieldNumber);
            return toHit.isEmpty() ? ShotOutcome.SUNK : ShotOutcome.HIT;
        }
        return ShotOutcome.MISS;
    }

    int length() {
        return toHit.size() + hit.size();
    }

    ShipDTO asDTO() {
        List<Integer> occupiedFields = new ArrayList<>(length());
        occupiedFields.addAll(toHit);
        occupiedFields.addAll(hit);
        return new ShipDTO(occupiedFields);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ship ship = (Ship) o;
        return this.length() == ship.length();
    }

    @Override
    public int hashCode() {
        return Objects.hash(toHit, hit);
    }

    @Override
    public String toString() {
        return "Ship{" + "toHit=" + toHit + ", hit=" + hit +'}';
    }
}
