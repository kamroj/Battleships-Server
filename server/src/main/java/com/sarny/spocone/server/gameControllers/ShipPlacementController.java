package com.sarny.spocone.server.gameControllers;

import com.sarny.spocone.publicclasses.ship.ShipDTO;
import com.sarny.spocone.publicclasses.ship.ShipPlacementData;
import com.sarny.spocone.server.game.InvalidShipPlacementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Wojciech Makiela
 */
@RestController
class ShipPlacementController {

    private ActiveGameInitializers initializers;
    private ActiveGames activeGames;

    @Autowired
    public ShipPlacementController(ActiveGameInitializers initializers, ActiveGames activeGames) {
        this.initializers = initializers;
        this.activeGames = activeGames;
    }

    @Autowired
    public ShipPlacementController(ActiveGames activeGames) {
        this.activeGames = activeGames;
    }

    @PostMapping("/placeShip")
    ResponseEntity<ShipDTO> placeShip(@RequestBody ShipPlacementData placementData) {
        if (placementData == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        try {
            return placeShipAndReturnItsDTO(placementData);
        } catch (InvalidShipPlacementException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    private ResponseEntity<ShipDTO> placeShipAndReturnItsDTO(@RequestBody ShipPlacementData placementData) throws InvalidShipPlacementException {
        int playerId = placementData.getPlayerID();
        ShipDTO shipDTO = initializers.getInitializerForPlayer(playerId).placeShip(playerId, placementData);
        return new ResponseEntity<>(shipDTO, HttpStatus.OK);
    }
}
