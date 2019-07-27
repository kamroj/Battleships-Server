package com.sarny.spocone.server.game_controllers;

import com.sarny.spocone.publicclasses.shot.Shot;
import com.sarny.spocone.publicclasses.shot.ShotOutcome;
import com.sarny.spocone.publicclasses.shot.ShotResult;
import com.sarny.spocone.publicclasses.shot.ShotsSummary;
import com.sarny.spocone.server.chat.ChatService;
import com.sarny.spocone.server.game.Game;
import com.sarny.spocone.server.game.support_class.PlayerDisconnectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * Entry point for {@link Shot} related requests.
 * Responsible for handling {@link Shot}s.
 * Informs player if it is his turn, and
 * returns opponents {@link ShotsSummary}.
 *
 * @author Wojciech Makiela
 */
@RestController
class ShotController {

    private final ActiveGames activeGames;
    private final ChatService chatService;

    @Autowired
    public ShotController(ActiveGames activeGames, ChatService chatService) {
        this.activeGames = activeGames;
        this.chatService = chatService;
    }

    /**
     * Responsible for handling {@link Shot}s, and adds shot summary info in Chat.
     * Informs about game ending.
     *
     * @see ChatService
     * @param shot taken by player with possible move.
     * @return {@link ShotResult} of executed {@link Shot}.
     */
    @PostMapping("/shot")
    ResponseEntity<?> fire(@RequestBody Shot shot) {
        Game gameForPlayer;
        if ((shot == null) || (shot.getField() < 0) ||
                (((gameForPlayer = activeGames.findGameOfPlayer(shot.getPlayerId()))) == null)) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        ShotResult shotResult = gameForPlayer.handleShot(shot);
        addShotSummaryToChat(shot, shotResult);
        return new ResponseEntity<>(shotResult, HttpStatus.OK);
    }

    private void addShotSummaryToChat(Shot shot, ShotResult shotResult) {
        int gameId = shot.getGameId();
        int playerId = shot.getPlayerId();
        chatService.addPlayerShotMessage(playerId, shotResult, gameId);

        if (shotResult.getShotOutcome() == ShotOutcome.MISS) {
            chatService.addTurnEndedMessage(playerId, gameId);
        } else if (shotResult.getShotOutcome() == ShotOutcome.WIN) {
            chatService.addGameEndedMessage(gameId);
        }
    }

    /**
     * Provides information if its player's turn.
     *
     * @param playerId who currently asking about his turn.
     * @param gameId corresponding game id.
     * @return true if current player can do a move.
     */
    @GetMapping("/turn/{playerId}/{gameId}")
    ResponseEntity<?> isPlayersTurn(@PathVariable Integer playerId, @PathVariable Integer gameId) {
        Game gameForPlayer = activeGames.findGameOfPlayer(playerId);
        if (gameForPlayer == null) return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

        try {
            boolean isPlayersRound = gameForPlayer.isPlayerRound(playerId);
            return new ResponseEntity<>(isPlayersRound, HttpStatus.OK);
        } catch (PlayerDisconnectedException e) {
            int disconnectedPlayerId = e.disconnectedPlayerId;
            chatService.addGameEndedMessage(gameId);
            chatService.playerDisconnected(gameId, disconnectedPlayerId);
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }

    /**
     * Provides information about opponents {@link ShotsSummary}.
     *
     * @param firstPlayerId who ask for previous players {@link ShotsSummary}.
     * @return opponents {@link ShotsSummary}.
     */
    @GetMapping("/summary/{firstPlayerId}")
    ResponseEntity<ShotsSummary> getSummaryOfOpponentsShots(@PathVariable Integer firstPlayerId) {
        Game gameForPlayer = activeGames.findGameOfPlayer(firstPlayerId);

        if (gameForPlayer == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        ShotsSummary opponentsShots = gameForPlayer.getOpponentsShots(firstPlayerId);
        return new ResponseEntity<>(opponentsShots, HttpStatus.OK);
    }

    /**
     * Provides set of integers representing guaranteed misses around last sunk ship.
     *
     * @param playerId who currently asking about his guaranteed misses.
     * @return set of guaranteed misses.
     */
    @GetMapping("/misses/{playerId}")
    ResponseEntity<?> getGuaranteedMisses(@PathVariable Integer playerId) {
        Game gameForPlayer = activeGames.findGameOfPlayer(playerId);
        if (gameForPlayer == null) return null;
        Set<Integer> guaranteedMisses = gameForPlayer.getGuaranteedMisses(playerId);
        return new ResponseEntity<>(guaranteedMisses, HttpStatus.OK);
    }

}
