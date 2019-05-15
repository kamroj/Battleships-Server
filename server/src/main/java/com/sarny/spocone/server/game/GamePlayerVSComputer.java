package com.sarny.spocone.server.game;

import com.sarny.spocone.publicclasses.shot.Shot;
import com.sarny.spocone.publicclasses.shot.ShotOutcome;
import com.sarny.spocone.publicclasses.shot.ShotResult;

/**
 * @author Kamil Rojek
 */
class GamePlayerVSComputer extends Game {
    private AI computer;

    GamePlayerVSComputer(ActiveBoards activeBoards, int firstPlayerID, AI computer) {
        super(activeBoards, firstPlayerID, computer.getID());
        this.computer = computer;
    }

    @Override
    public ShotResult handleShot(Shot shot) {
        ShotResult shotResult = super.handleShot(shot);

        if (shotResult.getShotOutcome() == ShotOutcome.MISS) {
            ShotResult aiShot;
            do {
                shot = computer.generateShot();
                aiShot = activeRound.handleShot(shot);
            } while (aiShot.getShotOutcome() != ShotOutcome.MISS);
        }
        return shotResult;
    }
}
