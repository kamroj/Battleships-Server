package com.sarny.spocone.server.gameControllers;

import com.google.gson.Gson;
import com.sarny.spocone.publicclasses.shot.Shot;
import com.sarny.spocone.publicclasses.shot.ShotsSummary;
import com.sarny.spocone.server.game.Game;
import com.sarny.spocone.server.game.StubRegister;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * @author Wojciech Makiela
 */
@RestController
class ShotController {

    private static Logger logger = LogManager.getLogger(ShotController.class);
    private final Gson gson;
    private final ActiveGames activeGames;
    private StubRegister register;

    @Autowired
    public ShotController(ActiveGames activeGames) {
        this.activeGames = activeGames;
        gson = new Gson();
        register = new StubRegister();
    }

    @PostMapping("/shot")
    String fire(@RequestBody Shot shot) {
        Game gameForPlayer = activeGames.findGameForPlayer(shot.getPlayerID());
        if (gameForPlayer == null) {
            return null;
        }
        return gson.toJson(gameForPlayer.handleShot(shot));
    }

    @GetMapping("/turn")
    boolean isPlayersTurn(@RequestBody Integer id) {
        Game gameForPlayer = activeGames.findGameForPlayer(id);
        if (gameForPlayer == null) return false;
        return gameForPlayer.isPlayerRound(id);
    }

    @GetMapping("/summary")
    ShotsSummary getSummaryOfOpponentsShots(@RequestBody Integer firstPlayerId) {
        Game gameForPlayer = activeGames.findGameForPlayer(firstPlayerId);
        if (gameForPlayer == null) return null;
        return gameForPlayer.getSecondPlayerShots(firstPlayerId);
    }

    @GetMapping("/misses")
    Set<Integer> getGuaranteedMisses(@RequestBody Integer id) {
        Game gameForPlayer = activeGames.findGameForPlayer(id);
        if (gameForPlayer == null) return null;
        return gameForPlayer.getGuaranteedMisses(id);
    }

    // TODO - remove once ShipPlacementController is implemented
    @PostMapping(path = "/register")
    public void stubRegistration(@RequestBody Integer id) {
        if (id == null) {
            return;
        }
        logger.info("Register player with id " + id);
        register.registerPlayer(id);


        if (register.isRegistrationOver()) {
            logger.info("Registration complete");
            activeGames.addNewGame(register.finalizeCreation(), register.player1Id, register.player2Id);
            register = new StubRegister();
        }
    }
}
