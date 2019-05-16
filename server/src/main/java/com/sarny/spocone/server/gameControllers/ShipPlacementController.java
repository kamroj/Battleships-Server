package com.sarny.spocone.server.gameControllers;

import com.sarny.spocone.publicclasses.ship.ShipDTO;
import com.sarny.spocone.publicclasses.ship.ShipPlacementData;
import com.sarny.spocone.server.game.Game;
import com.sarny.spocone.server.game.GameInitializer;
import com.sarny.spocone.server.game.InvalidBoardCreationException;
import com.sarny.spocone.server.game.InvalidShipPlacementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @PostMapping("/placeShip")
    ResponseEntity<ShipDTO> placeShip(@RequestBody ShipPlacementData placementData) {
        System.out.println(placementData);
        if (placementData == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        try {
            ResponseEntity<ShipDTO> shipDTOResponseEntity = placeShipAndReturnItsDTO(placementData);
            moveGameToActiveGamesIfFinalized(placementData);
            return shipDTOResponseEntity;
        } catch (InvalidShipPlacementException | InvalidBoardCreationException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    private void moveGameToActiveGamesIfFinalized(ShipPlacementData placementData) throws InvalidBoardCreationException {
        int playerId = placementData.getPlayerID();
        GameInitializer initializerForPlayer = initializers.getInitializerForPlayer(playerId);
        if (initializerForPlayer.areBothPlayersDone()) {
            Game game = initializerForPlayer.generateGame();
            List<Integer> playersIDs = game.getPlayersIDs();
            Integer p1Id = playersIDs.get(0);
            Integer p2Id = playersIDs.get(1);
            activeGames.addNewGame(game, p1Id, p2Id);
            initializers.removeInitializerForPlayers(p1Id, p2Id);
        }
    }

    private ResponseEntity<ShipDTO> placeShipAndReturnItsDTO(@RequestBody ShipPlacementData placementData) throws InvalidShipPlacementException {
        int playerId = placementData.getPlayerID();
        ShipDTO shipDTO = initializers.getInitializerForPlayer(playerId).placeShip(playerId, placementData);
        return new ResponseEntity<>(shipDTO, HttpStatus.OK);
    }
}
