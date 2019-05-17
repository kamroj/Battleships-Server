package com.sarny.spocone.server.gameControllers;

import com.sarny.spocone.server.game.GameInitializer;
import com.sarny.spocone.server.game.GameVsComputerInitializer;
import com.sarny.spocone.server.game.computer_players.AI;
import com.sarny.spocone.server.game.computer_players.ComputerEasy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author Wojciech Makiela
 */
@RestController
class RegistrationController {

    private ActiveGameInitializers initializers;
    private Rooms rooms;
    private Logger logger = LogManager.getLogger(RegistrationController.class);

    @Autowired
    RegistrationController(ActiveGameInitializers initializers, Rooms rooms) {
        this.initializers = initializers;
        this.rooms = rooms;
    }

    @PostMapping(path = "/createRoom")
    ResponseEntity<Integer> createRoom(@RequestBody Integer id) {
        if (id == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        Optional<Rooms.Room> register = rooms.register(id);
        logger.info(String.format("Player with id %d created new game room", id));

        return register.map(room -> new ResponseEntity<>(room.id, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.BAD_REQUEST));

    }

    private void moveRoomToActiveGames(Rooms.Room room) {
        int player1Id = room.players.firstPlayerId;
        int player2Id = room.players.secondPlayerId;
        logger.info("New game room created");
        initializers.addNewActiveGameInitializer(new GameInitializer(player1Id, player2Id), player1Id, player2Id);
    }

    @PostMapping(path = "/joinRoom")
    ResponseEntity<Integer> joinRoom(@RequestParam("playerId") Integer playerId, @RequestParam("roomId") Integer roomId) {
        if (playerId == null || roomId == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        Optional<Rooms.Room> register = rooms.register(playerId, roomId);
        logger.info(String.format("Player with id %d joined room with id %d", playerId, roomId));

        if (register.isPresent()) {
            Rooms.Room room = register.get();
            moveRoomToActiveGames(room);
            return new ResponseEntity<>(room.id, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PostMapping(path = "/playVersusAi")
    ResponseEntity<Integer> playVersusAi(@RequestBody Integer id) {
        if (id == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        int aiId = AI.generateID();
        AI ai = new ComputerEasy(aiId);
        initializers.addNewActiveGameInitializer(new GameVsComputerInitializer(id, aiId, ai), id, aiId);
        return new ResponseEntity<>(-1, HttpStatus.OK);
    }

    @GetMapping("/rooms")
    ResponseEntity<?> getAvailableRooms() {
        return new ResponseEntity<>(rooms.available(), HttpStatus.OK);
    }

}