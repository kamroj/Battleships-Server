package com.sarny.spocone.publicclasses.ship;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A class which provides information about ship location in simplified format.
 *
 * @author Agnieszka Trzewik
 */
public class ShipDTO {

    private List<Integer> shotDownFields;

    public ShipDTO(List<Integer> shotDownFields) {
        this.shotDownFields = shotDownFields;
    }

    public ShipDTO() {
        shotDownFields = new ArrayList<>();
    }

    public List<Integer> getShotDownFields() {
        return shotDownFields;
    }

    public void addFields(List<Integer> fields) {
        shotDownFields.addAll(fields);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShipDTO shipDTO = (ShipDTO) o;
        return Objects.equals(shotDownFields, shipDTO.shotDownFields);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shotDownFields);
    }
}
