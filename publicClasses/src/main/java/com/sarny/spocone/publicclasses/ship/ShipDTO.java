package com.sarny.spocone.publicclasses.ship;

import java.util.List;

/**
 * A class which provides information about shot down fields on ship.
 *
 * @author Agnieszka Trzewik
 */
public class ShipDTO {

    private List<Integer> shotDownFields;

    public ShipDTO(List<Integer> shotDownFields) {
        this.shotDownFields = shotDownFields;
    }

    public ShipDTO() {
    }

    public List<Integer> getShotDownFields() {
        return shotDownFields;
    }
}
