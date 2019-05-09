package com.sarny.spocone.server.gameControllers;

import com.sarny.spocone.server.game.Game;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Wojciech Makiela
 */
@Component
class ActiveGames {

    Map<Integer, Game> activeGames = new HashMap<>();

    Game findGameForPlayer(int playerId) {
        return activeGames.get(playerId);
    }

    void addNewGame(Game game, int... playersIds) {
        for (int id : playersIds) {
            activeGames.put(id, game);
        }
    }

}
