package com.sarny.spocone.server.gameControllers;

import com.google.gson.Gson;
import com.sarny.spocone.publicclasses.shot.Shot;
import com.sarny.spocone.publicclasses.shot.ShotsSummary;
import com.sarny.spocone.server.game.Game;
import com.sarny.spocone.server.game.StubRegister;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
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
        shot = updateOffsetBetweenClientAndServerSide(shot);
        Game gameForPlayer = activeGames.findGameForPlayer(shot.getPlayerID());
        if (gameForPlayer == null) {
            return null;
        }
        return gson.toJson(gameForPlayer.handleShot(shot));
    }

    @GetMapping("/turn/{id}")
    Boolean isPlayersTurn(@PathVariable Integer id) {
        Game gameForPlayer = activeGames.findGameForPlayer(id);
        if (gameForPlayer == null) return false;
        return gameForPlayer.isPlayerRound(id);
    }

    @GetMapping("/summary/{firstPlayerId}")
    ShotsSummary getSummaryOfOpponentsShots(@PathVariable Integer firstPlayerId) {
        Game gameForPlayer = activeGames.findGameForPlayer(firstPlayerId);
        if (gameForPlayer == null) return null;
        return gameForPlayer.getOpponentsShots(firstPlayerId);
    }

    @GetMapping("/misses/{id}")
    List<Integer> getGuaranteedMisses(@PathVariable Integer id) {
        Game gameForPlayer = activeGames.findGameForPlayer(id);
        if (gameForPlayer == null) return null;
        Set<Integer> guaranteedMisses = gameForPlayer.getGuaranteedMisses(id);
        return updateOffsetBetweenClientAndServerSide(guaranteedMisses);
    }

    /**
     * Client side enumerates fields starting from 1, while server starts from 0
     * this method solves this problem by decrementing field id
     *
     * @param shot
     * @return
     */
    private Shot updateOffsetBetweenClientAndServerSide(@RequestBody Shot shot) {
        shot = new Shot(shot.getPlayerID(), shot.getField() - 1);
        return shot;
    }

    /**
     * Client side enumerates fields starting from 1, while server starts from 0
     * this method solves this problem by incremening occupied indexes
     *
     * @param guaranteedMisses
     * @return
     */
    private List<Integer> updateOffsetBetweenClientAndServerSide(Set<Integer> guaranteedMisses) {
        List<Integer> guaranteedMissesWithUpdatedOffset = new ArrayList<>();
        for (Integer miss : guaranteedMisses) {
            guaranteedMissesWithUpdatedOffset.add(miss + 1);
        }
        return guaranteedMissesWithUpdatedOffset;
    }

    // TODO - remove once ShipPlacementController is implemented
    @PostMapping(path = "/register")
    public List<Integer> stubRegistration(@RequestBody Integer id) {
        if (id == null) {
            return null;
        }
        logger.info("Register player with id " + id);
        List<Integer> fieldsWithShips = register.registerPlayer(id);


        if (register.isRegistrationOver()) {
            logger.info("Registration complete");
            activeGames.addNewGame(register.finalizeCreation(), register.player1Id, register.player2Id);
            register = new StubRegister();
        }

        return fieldsWithShips;
    }
}
