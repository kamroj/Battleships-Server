package com.sarny.spocone.server.gameControllers;

import com.sarny.spocone.server.game.GameInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Wojciech Makiela
 */
@RestController
class RegistrationController {

    private ActiveGameInitializers initializers;
    private Integer player1Id = null;
    private Integer player2Id = null;

    @Autowired
    RegistrationController(ActiveGameInitializers initializers) {
        this.initializers = initializers;
    }

    @PostMapping(path = "/createRoom")
    public ResponseEntity<Integer> createRoom(@RequestBody Integer id) {
        if (id == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        savePlayersRegistration(id);
        return new ResponseEntity<>(1, HttpStatus.OK);
    }

    @PostMapping(path = "/joinRoom")
    public ResponseEntity<Integer> joinRoom(@RequestParam("playerId") Integer playerId, @RequestParam("roomId") Integer roomId) {
        if (playerId == null || roomId ==null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } //TODO after implementing Rooms.class implement logic here
        return new ResponseEntity<>(1, HttpStatus.OK);
    }

   @PostMapping(path = "/playVersusAi")
   public ResponseEntity<Integer> playVersusAi(@RequestBody Integer id) {
      if (id == null) {
         return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
      }
      //TODO after implementing Rooms.class implement logic here
      return new ResponseEntity<>(1, HttpStatus.OK);
   }

    private void savePlayersRegistration(@RequestBody Integer id) {
        if (player1Id == null) {
            player1Id = id;
        } else {
            player2Id = id;
            finalizeRegistration();
        }
    }

    private void finalizeRegistration() {
        initializers.addNewActiveGameInitializer(new GameInitializer(player1Id, player2Id), player1Id, player2Id);
        player1Id = null;
        player2Id = null;
    }
}