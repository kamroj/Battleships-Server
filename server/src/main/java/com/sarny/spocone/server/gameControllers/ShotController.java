package com.sarny.spocone.server.gameControllers;

import com.google.gson.Gson;
import com.sarny.spocone.publicclasses.shot.Shot;
import com.sarny.spocone.publicclasses.shot.ShotsSummary;
import com.sarny.spocone.server.game.Game;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * @author Wojciech Makiela
 */
@RestController
class ShotController {

    private final Gson gson;
    private final ActiveGames activeGames;

    @Autowired
    public ShotController(ActiveGames activeGames) {
        this.activeGames = activeGames;
        gson = new Gson();
    }

    @PostMapping("/shot")
    String fire(@RequestBody Shot shot) {
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
    ResponseEntity<?> getGuaranteedMisses(@PathVariable Integer id) {
        Game gameForPlayer = activeGames.findGameForPlayer(id);
        if (gameForPlayer == null) return null;
        Set<Integer> guaranteedMisses = gameForPlayer.getGuaranteedMisses(id);
        return new ResponseEntity<>(guaranteedMisses, HttpStatus.OK);
    }

}
