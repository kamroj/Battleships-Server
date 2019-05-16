package com.sarny.spocone.server.game;

import com.sarny.spocone.publicclasses.shot.Shot;
import com.sarny.spocone.server.game.computer_players.AI;
import com.sarny.spocone.server.game.computer_players.ComputerEasy;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kamil Rojek
 */
public class GamePlayerVSComputerTest {
    private AI computer;
    private Game game;
    private final int firstPlayerID = 1;


    @BeforeMethod
    public void setUp() {
        computer = new ComputerEasy(AI.generateID());
        Map<Integer, Board> boardsInGame = new HashMap<>();
        ActiveBoards activeBoards = new ActiveBoards(boardsInGame);
        game = new GamePlayerVSComputer(activeBoards, firstPlayerID, computer);
    }

    @Test
    public void testHandleShot_whenFirstPlayerShotIsMissThenComputerShots(){
        //Arrange
        Shot shot = new Shot(firstPlayerID, 13);

        //Act


        //Assert

    }

}