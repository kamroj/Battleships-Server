package com.sarny.spocone.server.game;

import java.util.Stack;
import com.sarny.spocone.publicclasses.shot.Shot;
import com.sarny.spocone.publicclasses.shot.ShotOutcome;
import com.sarny.spocone.publicclasses.shot.ShotResult;

/**
 * Provides set of functions allowing conducting single game
 * Package private constructor prevents instantiating Game without using GameInitializer
 *
 * @author Wojciech Makiela
 */
public class Game {

    private ActiveBoards activeBoards;
    private Stack<Round> rounds;

    Game() {}

    public ShotResult handleShot(Shot shot) {
        return new ShotResult(ShotOutcome.MISS, shot.getField());
    }
}
