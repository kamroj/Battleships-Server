package com.sarny.spocone.server.game;

import com.sarny.spocone.server.game.computer_players.AI;
import com.sarny.spocone.server.game.computer_players.ComputerEasy;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Kamil Rojek
 */
public class GameComputerVsComputerInitializerAUTOMAT {
    private GameComputerVsComputer game;
    private int gameNumber = 0;

    @BeforeMethod
    public void setUp() {
        gameNumber++;
        AI firstComputer = new ComputerEasy(AI.generateID());
        AI secondComputer = new ComputerEasy(AI.generateID());

        GameComputerVsComputerInitializer initializer = new GameComputerVsComputerInitializer(firstComputer, secondComputer);
        game = initializer.generateGame();
    }

    @Test(enabled = false)
    //@Test(invocationCount = 10)
    public void runAutomaticGame(){
        System.out.printf("Game number: %d\n\n", gameNumber);
        game.runAutomaticGame();
    }
}