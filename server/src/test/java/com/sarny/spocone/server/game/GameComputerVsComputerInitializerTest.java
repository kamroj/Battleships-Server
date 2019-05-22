package com.sarny.spocone.server.game;

import com.sarny.spocone.server.game.computer_players.AI;
import com.sarny.spocone.server.game.computer_players.ComputerEasy;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author Kamil Rojek
 */
public class GameComputerVsComputerInitializerTest {
    private AI firstComputer;
    private AI secondComputer;
    private GameComputerVsComputer game;


    @BeforeMethod
    public void setUp() {
        firstComputer = new ComputerEasy(AI.generateID());
        secondComputer = new ComputerEasy(AI.generateID());

        GameComputerVsComputerInitializer initializer = new GameComputerVsComputerInitializer(firstComputer, secondComputer);
        game = initializer.generateGame();
    }

    @Test
    public void testRunAutomaticGame__(){
        //Arrange
        game.runAutomaticGame();

        //Act


        //Assert

    }
}