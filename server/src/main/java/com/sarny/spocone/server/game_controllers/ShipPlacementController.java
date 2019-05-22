package com.sarny.spocone.server.game_controllers;

import com.sarny.spocone.publicclasses.ship.ShipDTO;
import com.sarny.spocone.publicclasses.ship.ShipPlacementData;
import com.sarny.spocone.server.game.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Wojciech Makiela
 */
@RestController
class ShipPlacementController {

    private final ActiveGameInitializers initializers;
    private final ActiveGames activeGames;

    @Autowired
    public ShipPlacementController(ActiveGameInitializers initializers, ActiveGames activeGames) {
        this.initializers = initializers;
        this.activeGames = activeGames;
    }

    @PostMapping("/placeShip")
    ResponseEntity<ShipDTO> placeShip(@RequestBody ShipPlacementData placementData) {
        if (placementData == null || placementData.getShipLength() <= 0 || placementData.getShipLength() > 4)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        try {
            ResponseEntity<ShipDTO> shipDTOResponseEntity = placeShipAndReturnItsDTO(placementData);
            moveGameToActiveGamesIfFinalized(placementData.getPlayerID());
            return shipDTOResponseEntity;
        } catch (InvalidShipPlacementException | InvalidBoardCreationException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/placeShipsRandomly/{playerId}")
    ResponseEntity<List<ShipDTO>> placeShipsRandomly(@PathVariable Integer playerId) throws InvalidShipPlacementException {
        if (playerId == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        try {
            ShipPlacementRandomly shipPlacementRandomly = new ShipPlacementRandomly();
            List<Ship> ships = shipPlacementRandomly.generateRandomShipList();
            List<ShipDTO> shipsDTO = initializers.getInitializerForPlayer(playerId).placeShip(playerId, ships);
            moveGameToActiveGamesIfFinalized(playerId);
            return new ResponseEntity<>(shipsDTO, HttpStatus.OK);
        } catch (InvalidBoardCreationException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    private void moveGameToActiveGamesIfFinalized(Integer playerId) throws InvalidBoardCreationException {
        GameInitializer initializerForPlayer = initializers.getInitializerForPlayer(playerId);
        if (initializerForPlayer.areBothPlayersDone()) {
            Game game = initializerForPlayer.generateGame();
            activeGames.addNewGame(game);
            removeInitializerForPlayers(game);
        }
    }

    private void removeInitializerForPlayers(Game game) {
        List<Integer> playersIDs = game.getPlayersIDs();
        Integer playerOneId = playersIDs.get(0);
        Integer playerTwoId = playersIDs.get(1);
        initializers.removeInitializerForPlayers(playerOneId, playerTwoId);
    }

    private ResponseEntity<ShipDTO> placeShipAndReturnItsDTO(@RequestBody ShipPlacementData placementData) throws InvalidShipPlacementException {
        int playerId = placementData.getPlayerID();
        ShipDTO shipDTO = initializers.getInitializerForPlayer(playerId).placeShip(playerId, placementData);
        return new ResponseEntity<>(shipDTO, HttpStatus.OK);
    }
}
