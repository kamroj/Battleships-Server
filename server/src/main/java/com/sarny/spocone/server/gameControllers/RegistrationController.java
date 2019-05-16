package com.sarny.spocone.server.gameControllers;

import com.sarny.spocone.server.game.GameInitializer;
import com.sarny.spocone.server.game.GameVsComputerInitializer;
import com.sarny.spocone.server.game.computer_players.AI;
import com.sarny.spocone.server.game.computer_players.ComputerEasy;
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

        return register.map(room -> new ResponseEntity<>(room.id, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.BAD_REQUEST));

    }

    private void moveRoomToActiveGames(Rooms.Room room) {
        int player1Id = room.players.firstPlayerId;
        int player2Id = room.players.secondPlayerId;
        initializers.addNewActiveGameInitializer(new GameInitializer(player1Id, player2Id), player1Id, player2Id);
    }

    @PostMapping(path = "/joinRoom")
    ResponseEntity<Integer> joinRoom(@RequestParam("playerId") Integer playerId, @RequestParam("roomId") Integer roomId) {
        if (playerId == null || roomId == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        Optional<Rooms.Room> register = rooms.register(playerId, roomId);

        if (register.isPresent()) {
            Rooms.Room room = register.get();
            moveRoomToActiveGames(room);
            return new ResponseEntity<>(room.id, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
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