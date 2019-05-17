package com.sarny.spocone.server.game_controllers;

import com.sarny.spocone.server.game.Game;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Wojciech Makiela
 */
@Component
class ActiveGames {

    private Map<Integer, Game> activeGames = new HashMap<>();

    Game findGameForPlayer(int playerId) {
        return activeGames.get(playerId);
    }

     void addNewGame(Game game) {
        for (int id : game.getPlayersIDs()) {
            activeGames.put(id, game);
        }
    }

}
