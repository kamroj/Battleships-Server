package com.sarny.spocone.server.gameControllers;

import com.google.gson.Gson;
import com.sarny.spocone.publicclasses.shot.Shot;
import com.sarny.spocone.server.game.Game;
import com.sarny.spocone.server.game.StubRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Wojciech Makiela
 */
@RestController
class ShotController {

    private final Gson gson;
    private final ActiveGames activeGames;
    private StubRegister register;

    @Autowired
    public ShotController(ActiveGames activeGames) {
        this.activeGames = activeGames;
        gson = new Gson();
        register = new StubRegister();
    }

    @PostMapping
    private String fire(@RequestBody Shot shot) {
        Game gameForPlayer = activeGames.findGameForPlayer(shot.getPlayerID());
        if (gameForPlayer == null) {
            return null;
        }
        return gson.toJson(gameForPlayer.handleShot(shot));
    }

    // TODO - remove once ShipPlacementController is implemented
    @PostMapping(path = "/register")
    public void stubRegistration(@RequestBody Integer id) {
        if (id == null) {
            return;
        }
        System.out.println("Register player with id " + id);
        register.registerPlayer(id);


        if (register.isRegistrationOver()) {
            System.out.println("Registration complete");
            activeGames.addNewGame(register.finalizeCreation(), register.player1Id, register.player2Id);
            register = new StubRegister();
        }
    }
}
