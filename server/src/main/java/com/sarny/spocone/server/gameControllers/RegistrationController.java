package com.sarny.spocone.server.gameControllers;

import com.sarny.spocone.server.game.GameInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author Wojciech Makiela
 */
class RegistrationController {

    private ActiveGameInitializers initializers;
    private Integer player1Id = null;
    private Integer player2Id = null;

    @Autowired
    RegistrationController(ActiveGameInitializers initializers) {
        this.initializers = initializers;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<Boolean> register(@RequestBody Integer id) {
        if (id == null) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }

        savePlayersRegistration(id);
        return new ResponseEntity<>(true, HttpStatus.OK);
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