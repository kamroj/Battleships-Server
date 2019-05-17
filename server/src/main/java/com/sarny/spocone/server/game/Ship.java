package com.sarny.spocone.server.game;

import com.sarny.spocone.publicclasses.ship.ShipDTO;
import com.sarny.spocone.publicclasses.ship.ShipPlacementData;
import com.sarny.spocone.publicclasses.shot.ShotOutcome;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Kamil Rojek
 */
class Ship {

    List<Integer> fieldsToHit;
    List<Integer> fieldsAlreadyHit;

    Ship(List<Integer> fields) {
        fieldsToHit = fields;
        fieldsAlreadyHit = new ArrayList<>();
    }

    public Ship(ShipPlacementData shipData) {
        fieldsToHit = new ArrayList<>();
        fieldsAlreadyHit = new ArrayList<>();
        updateOccupiedFieldsAccordingToShipPlacementData(shipData);
    }

    private void updateOccupiedFieldsAccordingToShipPlacementData(ShipPlacementData shipData) {
        int offsetBetweenFields = shipData.isHorizontally() ? 1 : 10; // move 1 to right (1), or 1 down (10)
        int nextOccupiedFieldId = shipData.getField();
        int fieldsToOccupy = shipData.getShipLength();

        while (length() < fieldsToOccupy) {
            fieldsToHit.add(nextOccupiedFieldId);
            nextOccupiedFieldId += offsetBetweenFields;
        }
    }

    boolean isOnField(int fieldNumber) {
        return fieldsToHit.contains(fieldNumber) || fieldsAlreadyHit.contains(fieldNumber);
    }

    ShotOutcome fire(int fieldNumber) {
        if (fieldsToHit.contains(fieldNumber)) {
            fieldsToHit.remove(Integer.valueOf(fieldNumber)); //Used Integer.valueOf cause fieldNumber is treated as Index
            fieldsAlreadyHit.add(fieldNumber);
            return fieldsToHit.isEmpty() ? ShotOutcome.SUNK : ShotOutcome.HIT;
        }
        return ShotOutcome.MISS;
    }

    int length() {
        return fieldsToHit.size() + fieldsAlreadyHit.size();
    }

    ShipDTO asDTO() {
        List<Integer> occupiedFields = new ArrayList<>(length());
        occupiedFields.addAll(fieldsToHit);
        occupiedFields.addAll(fieldsAlreadyHit);
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
        return Objects.hash(fieldsToHit, fieldsAlreadyHit);
    }

    @Override
    public String toString() {
        return "Ship{" + "fieldsToHit=" + fieldsToHit + ", fieldsAlreadyHit=" + fieldsAlreadyHit + '}';
    }
}
