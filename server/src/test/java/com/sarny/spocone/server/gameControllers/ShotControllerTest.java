package com.sarny.spocone.server.gameControllers;

import com.google.gson.Gson;
import com.sarny.spocone.publicclasses.shot.Shot;
import com.sarny.spocone.publicclasses.shot.ShotOutcome;
import com.sarny.spocone.publicclasses.shot.ShotResult;
import com.sarny.spocone.server.game.ComputerEasy;
import com.sarny.spocone.server.game.Game;
import com.sarny.spocone.server.game.AI;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author Wojciech Makiela
 */
public class ShotControllerTest {
    private final int player1Id = 1;
    private final int player2Id = 2;

    private ActiveGames activeGames;
    private ShotController controller;
    private Gson gson = new Gson();

    @BeforeMethod
    public void setUp() {
        activeGames = new ActiveGames();
        controller = new ShotController(activeGames);
    }

    @Test
    public void testStubRegistration_when2PlayersRegister_createNewGame() {
        // act
        controller.stubRegistration(player1Id);
        controller.stubRegistration(player2Id);

        // assert
        assertNotNull(activeGames.findGameForPlayer(player1Id));
        assertNotNull(activeGames.findGameForPlayer(player2Id));
    }

    @Test
    public void testStubRegistration_when4PlayersRegister_2DifferentGameObjectsAreCreated() {
        // arrange
        int player3Id = 3;
        int player4Id = 4;
        controller.stubRegistration(player1Id);
        controller.stubRegistration(player2Id);
        controller.stubRegistration(player3Id);
        controller.stubRegistration(player4Id);

        // act
        Game firstGame = activeGames.findGameForPlayer(player1Id);
        Game secondGame = activeGames.findGameForPlayer(player3Id);

        // assert
        assertNotEquals(firstGame, secondGame);
    }

    @Test
    public void testFire_whenPlayerMissed_returnMiss() {
        // arrange
        controller.stubRegistration(player1Id);
        controller.stubRegistration(player2Id);

        // act
        String actual = controller.fire(new Shot(player1Id, 13));
        String expected = gson.toJson(new ShotResult(ShotOutcome.MISS, 12));

        // assert
        assertEquals(actual, expected);
    }

    @Test
    public void testPlayAgainstComputer_whenPlayerWantsToPlayAgainstComputer_createNewGameWithComputer(){
        //Arrange
        AI computer = new ComputerEasy(AI.generateID());

        //Act
        controller.stubRegistration(player1Id);
        controller.stubRegistration(computer.getID());

        //Assert
        // assert
        assertNotNull(activeGames.findGameForPlayer(player1Id));
        assertNotNull(activeGames.findGameForPlayer(computer.getID()));
    }

    @Test
    public void testSequence_whenPlayerPlaysAgainstComputer_canPlayTwoRounds(){
        //Arrange
        AI computer = new ComputerEasy(AI.generateID());

        //Act
        controller.stubRegistration(player1Id);
        controller.stubRegistration(computer.getID());
        controller.fire(new Shot(player1Id, 13));
        String result = controller.fire(new Shot(player1Id, 14));
        String expected = gson.toJson(new ShotResult(ShotOutcome.MISS, 13));

        //Assert
        assertEquals(expected, result);
    }
}