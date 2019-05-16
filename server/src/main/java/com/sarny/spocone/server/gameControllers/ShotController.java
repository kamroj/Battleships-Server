package com.sarny.spocone.server.gameControllers;

import com.google.gson.Gson;
import com.sarny.spocone.publicclasses.shot.Shot;
import com.sarny.spocone.publicclasses.shot.ShotResult;
import com.sarny.spocone.publicclasses.shot.ShotsSummary;
import com.sarny.spocone.server.game.Game;
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

    private final ActiveGames activeGames;

    @Autowired
    public ShotController(ActiveGames activeGames) {
        this.activeGames = activeGames;
    }

    @PostMapping("/shot")
    ResponseEntity<?> fire(@RequestBody Shot shot) {
        Game gameForPlayer = activeGames.findGameForPlayer(shot.getPlayerID());
        if (gameForPlayer == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        ShotResult shotResult = gameForPlayer.handleShot(shot);
        return new ResponseEntity<>(shotResult, HttpStatus.OK);
    }

    @GetMapping("/turn/{id}")
    ResponseEntity<?> isPlayersTurn(@PathVariable Integer id) {
        Game gameForPlayer = activeGames.findGameForPlayer(id);
        if (gameForPlayer == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        boolean isPlayersRound = gameForPlayer.isPlayerRound(id);
        return new ResponseEntity<>(isPlayersRound, HttpStatus.OK);
    }

    @GetMapping("/summary/{firstPlayerId}")
    ResponseEntity<ShotsSummary> getSummaryOfOpponentsShots(@PathVariable Integer firstPlayerId) {
        Game gameForPlayer = activeGames.findGameForPlayer(firstPlayerId);

        if (gameForPlayer == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        ShotsSummary opponentsShots = gameForPlayer.getOpponentsShots(firstPlayerId);
        return new ResponseEntity<>(opponentsShots, HttpStatus.OK);
    }

    @GetMapping("/misses/{id}")
    ResponseEntity<?> getGuaranteedMisses(@PathVariable Integer id) {
        Game gameForPlayer = activeGames.findGameForPlayer(id);
        if (gameForPlayer == null) return null;
        Set<Integer> guaranteedMisses = gameForPlayer.getGuaranteedMisses(id);
        return new ResponseEntity<>(guaranteedMisses, HttpStatus.OK);
    }

}
