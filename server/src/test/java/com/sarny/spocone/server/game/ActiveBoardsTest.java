package com.sarny.spocone.server.game;

import com.sarny.spocone.publicclasses.shot.Shot;
import com.sarny.spocone.publicclasses.shot.ShotOutcome;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.*;

/**
 * @author Kamil Rojek
 */
public class ActiveBoardsTest {
    private ActiveBoards activeBoards;

    @BeforeMethod
    public void setUp() {
        Map<Integer, Board> boardsInGame = new HashMap<>();

        Board playerOneBoard = new Board(new ArrayList<>(Arrays.asList(
                new Ship(new ArrayList<>(Arrays.asList(1, 2, 3))),
                new Ship(new ArrayList<>(Arrays.asList(10, 20, 30, 40))))));

        Board playerTwoBoard = new Board(new ArrayList<>(Arrays.asList(
                new Ship(new ArrayList<>(Arrays.asList(1, 2, 3))),
                new Ship(new ArrayList<>(Arrays.asList(10, 20, 30, 40))))));

        boardsInGame.put(1, playerOneBoard);
        boardsInGame.put(2, playerTwoBoard);

        activeBoards = new ActiveBoards(boardsInGame);
    }

    @Test
    public void testMarkShot_shotIntoWater_returnMiss() {
        //Arrange
        Shot shot = new Shot(1, 4);

        //Act
        ShotOutcome shotResult = activeBoards.markShot(shot);

        //Assert
        Assert.assertEquals(shotResult, ShotOutcome.MISS);
    }

    @Test
    public void testMarkShot_shotHitShip_returnHit() {
        //Arrange
        Shot shot = new Shot(1, 3);

        //Act
        ShotOutcome shotResult = activeBoards.markShot(shot);

        //Assert
        Assert.assertEquals(shotResult, ShotOutcome.HIT);
    }

    @Test
    public void testMarkShot_shotSunkShip_returnSUNK() {
        //Arrange
        Shot firstShot = new Shot(1, 1);
        Shot secondShot = new Shot(1, 2);
        Shot thirdShot = new Shot(1, 3);

        //Act
        activeBoards.markShot(firstShot);
        activeBoards.markShot(secondShot);
        ShotOutcome shotResult = activeBoards.markShot(thirdShot);

        //Assert
        Assert.assertEquals(shotResult, ShotOutcome.SUNK);
    }

    @Test
    public void testMarkShot_shotWinGame_returnWIN() {
        //Arrange
        List<Shot> shots = List.of(
                new Shot(1, 1),
                new Shot(1, 2),
                new Shot(1, 3),
                new Shot(1, 10),
                new Shot(1, 20),
                new Shot(1, 30),
                new Shot(1, 40)
        );

        ShotOutcome shotResult = null;

        //Act
        for (Shot shot : shots) {
            shotResult = activeBoards.markShot(shot);
        }

        //Assert
        Assert.assertEquals(shotResult, ShotOutcome.WIN);
    }

    @Test
    public void testGetShipOnField_fieldPointingToShip_returnShip() {
        //Arrange
        int field = 1;
        int playerID = 1;

        //Act
        Ship ship = activeBoards.getShipOnField(field, playerID);

        //Assert
        Assert.assertEquals(new Ship(Arrays.asList(1, 2, 3)), ship);
    }
}