package com.sarny.spocone.server.game_controllers;

import com.sarny.spocone.server.game.GameInitializer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Initialize active games for two players.
 *
 * @author Wojciech Makiela
 */
@Component
class ActiveGameInitializers {

    private Map<Integer, GameInitializer> initializers = new HashMap<>();

    GameInitializer getInitializerForPlayer(int playerId) {
        return initializers.get(playerId);
    }

    void addNewActiveGameInitializer(GameInitializer initializer, int player1Id, int player2Id) {
        initializers.put(player1Id, initializer);
        initializers.put(player2Id, initializer);
    }

    void removeInitializerForPlayers(int... playerIds) {
        for (int id : playerIds) {
            initializers.remove(id);
        }
    }
}
