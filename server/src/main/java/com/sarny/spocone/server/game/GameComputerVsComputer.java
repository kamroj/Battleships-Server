package com.sarny.spocone.server.game;

import com.sarny.spocone.publicclasses.shot.Shot;
import com.sarny.spocone.publicclasses.shot.ShotOutcome;
import com.sarny.spocone.publicclasses.shot.ShotResult;
import com.sarny.spocone.server.game.computer_players.AI;

/**
 * @author Kamil Rojek
 */
class GameComputerVsComputer extends Game {
    private AI firstComputer;
    private AI secondComputer;

    GameComputerVsComputer(ActiveBoards activeBoards, AI firstComputer, AI secondComputer) {
        super(activeBoards, firstComputer.getID(), secondComputer.getID());
        this.firstComputer = firstComputer;
        this.secondComputer = secondComputer;
    }

    public void runAutomaticGame() {
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
            //System.out.println("SHOT RESULT " + shotResult.getShotOutcome());
        } while (shotResult.getShotOutcome() != ShotOutcome.SUNK); //todo zmienić na WIN
    }

    private ShotResult playRound(AI computer) {
        ShotResult shotResult;
        do {
            Shot shot = computer.generateShot();
            shotResult = activeRound.handleShot(shot);
            System.out.println(shotResult.getField() + " : " + shotResult.getShotOutcome());
        } while (shotResult.getShotOutcome() != ShotOutcome.WIN);
        return shotResult;
    }
}