package com.sarny.spocone.server.game;

import com.sarny.spocone.publicclasses.shot.Shot;
import com.sarny.spocone.publicclasses.shot.ShotOutcome;
import com.sarny.spocone.publicclasses.shot.ShotResult;
import com.sarny.spocone.server.game.computer_players.AI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Kamil Rojek
 */
class GameComputerVsComputer extends Game {
    private AI firstComputer;
    private AI secondComputer;
    private BoardDrawer boardDrawer;
    private Logger logger = LogManager.getLogger(GameComputerVsComputer.class);

    GameComputerVsComputer(ActiveBoards activeBoards, AI firstComputer, AI secondComputer) {
        super(activeBoards, firstComputer.getID(), secondComputer.getID());
        this.firstComputer = firstComputer;
        this.secondComputer = secondComputer;
        boardDrawer = new BoardDrawer(activeBoards);
    }

    void runAutomaticGame() {
        ShotResult shotResult;
        boolean firstComputerRound = true;
        do {
            if (firstComputerRound) {
                shotResult = playRound(firstComputer);
                firstComputerRound = false;
            } else {
                shotResult = playRound(secondComputer);
                firstComputerRound = true;
            }
        } while (shotResult.getShotOutcome() != ShotOutcome.WIN);
    }

    private ShotResult playRound(AI computer) {
        ShotResult shotResult;
        do {
            Shot shot = computer.generateShot();
            shotResult = activeRound.handleShot(shot);

            if (shotResult.getShotOutcome() == ShotOutcome.SUNK ||
                    shotResult.getShotOutcome() == ShotOutcome.WIN) {
                printBoards(computer);
            }
        } while (shotResult.getShotOutcome() != ShotOutcome.MISS
                && shotResult.getShotOutcome() != ShotOutcome.WIN);

        return shotResult;
    }

    private void printBoards(AI whoseTurn) {
        logger.info("TURN :: " + whoseTurn.getID());
        logger.info("\n" + boardDrawer.draw());
    }
}