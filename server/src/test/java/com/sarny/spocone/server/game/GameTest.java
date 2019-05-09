package com.sarny.spocone.server.game;

import com.sarny.spocone.publicclasses.shot.Shot;
import com.sarny.spocone.publicclasses.shot.ShotOutcome;
import com.sarny.spocone.publicclasses.shot.ShotResult;
import com.sarny.spocone.publicclasses.shot.ShotsSummary;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.*;

/**
 * @author Agnieszka Trzewik
 */
public class GameTest {

    private Map<Integer, Board> boardsInGame;
    private Game game;
    private int firstPlayerID = 1;
    private int secondPlayerID = 2;

    @BeforeMethod()
    public void setUp() {
        boardsInGame = new HashMap<>();
        ActiveBoards activeBoards = new ActiveBoards(boardsInGame);
        game = new Game(activeBoards, firstPlayerID, secondPlayerID);

    }

    @Test
    public void testIsFirstPlayerRound_whenGameInitialized_returnTrue() {
        //Act
        boolean isPlayerRound = game.isPlayerRound(firstPlayerID);

        //Assert
        Assert.assertTrue(isPlayerRound);
    }

    @Test
    public void testIsNotSecondPlayerRound_whenGameInitialized_returnTrue() {
        //Act
        boolean isNotPlayerRound = game.isPlayerRound(secondPlayerID);

        //Assert
        Assert.assertFalse(isNotPlayerRound);
    }

    @DataProvider
    public static Object[][] dprov_forSecondPlayerShotsGetter() {
        return new Object[][]{
                {new ArrayList<>(Arrays.asList(20, 21, 22))},
                {new ArrayList<>(Arrays.asList(1, 2, 3, 4))},
                {new ArrayList<>(Arrays.asList(20, 21, 22))},
                {new ArrayList<>(Arrays.asList(87, 88, 89))},
                {new ArrayList<>(Arrays.asList(34, 44, 54))},
                {new ArrayList<>(Arrays.asList(13, 23, 33))},
        };
    }

    @Test(dataProvider = "dprov_forSecondPlayerShotsGetter")
    public void testGetSecondPlayerShots_when_return(List<Integer> fieldsForSecondPlayerShip) {
        //Arrange
        Board boardForFirstPlayer = new Board(new ArrayList<>());

        Board boardForSecondPlayer = new Board(new ArrayList<>(Collections.singletonList(
                new Ship(fieldsForSecondPlayerShip))));

        boardsInGame.put(firstPlayerID, boardForFirstPlayer);
        boardsInGame.put(secondPlayerID, boardForSecondPlayer);

        List<ShotResult> shotResults = new ArrayList<>();

        int numberOfShots = fieldsForSecondPlayerShip.size();

        for (int i = 0; i < numberOfShots; i++) {
            ShotResult shotResult = game.handleShot((new Shot(firstPlayerID, fieldsForSecondPlayerShip.get(0))));
            shotResults.add(shotResult);
        }

        //Act
        ShotsSummary shotsSummary = game.getOpponentsShots(secondPlayerID);

        //Assert
        Assert.assertTrue(shotsSummary.getShotResults().containsAll(shotResults));
    }

    @DataProvider
    public static Object[][] dprov_forGuaranteedMissesGetter() {
        return new Object[][]{
                {new ArrayList<>(Arrays.asList(11, 12, 13)), Set.of(0, 1, 2, 3, 4, 10, 14, 20, 21, 22, 23, 24)},
                {new ArrayList<>(Collections.singletonList(0)), Set.of(1, 10, 11)},
                {new ArrayList<>(Arrays.asList(19, 29)), Set.of(8, 9, 18, 28, 38, 39)},
                {new ArrayList<>(Arrays.asList(79, 89, 99)), Set.of(68, 69, 78, 88, 98)},
                {new ArrayList<>(Arrays.asList(81, 91)), Set.of(70, 71, 72, 80, 82, 90, 92)},
                {new ArrayList<>(Arrays.asList(60, 61, 62)), Set.of(50, 51, 52, 53, 63, 70, 71, 72, 73)},
                {new ArrayList<>(Arrays.asList(10, 20)), Set.of(0, 1, 11, 21, 30, 31)},
                {new ArrayList<>(Arrays.asList(8, 9)), Set.of(7, 17, 18, 19)},
                {new ArrayList<>(Arrays.asList(90, 91, 92)), Set.of(80, 81, 82, 83, 93)},
                {new ArrayList<>(Collections.singletonList(24)), Set.of(13, 14, 15, 23, 25, 33, 34, 35)},
        };
    }

    @Test(dataProvider = "dprov_forGuaranteedMissesGetter")
    public void testGetGuaranteedMisses_whenSecondPlayerSunkShip_returnProperMisses(List<Integer> fieldsForFirstPlayerShip, Set<Integer> properMisses) {
        //Arrange
        Board boardForFirstPlayer = new Board(new ArrayList<>(Collections.singletonList(
                new Ship(fieldsForFirstPlayerShip))));

        Board boardForSecondPlayer = new Board(new ArrayList<>());

        boardsInGame.put(firstPlayerID, boardForFirstPlayer);
        boardsInGame.put(secondPlayerID, boardForSecondPlayer);

        game.handleShot(new Shot(firstPlayerID, 6));

        int numberOfShots = fieldsForFirstPlayerShip.size();

        for (int i = 0; i < numberOfShots; i++) {
            game.handleShot((new Shot(secondPlayerID, fieldsForFirstPlayerShip.get(0))));
        }

        //Act
        Set<Integer> misses = game.getGuaranteedMisses(secondPlayerID);

        //Assert
        Assert.assertTrue(misses.containsAll(properMisses) && properMisses.containsAll(misses));

    }

    @Test(dataProvider = "dprov_forGuaranteedMissesGetter")
    public void testGetGuaranteedMisses_whenFirstPlayerSunkShip_returnProperMisses(List<Integer> fieldsForSecondPlayerShip, Set<Integer> properMisses) {
        //Arrange
        Board boardForFirstPlayer = new Board(new ArrayList<>());

        Board boardForSecondPlayer = new Board(new ArrayList<>(Collections.singletonList(
                new Ship(fieldsForSecondPlayerShip))));

        boardsInGame.put(firstPlayerID, boardForFirstPlayer);
        boardsInGame.put(secondPlayerID, boardForSecondPlayer);


        int numberOfShots = fieldsForSecondPlayerShip.size();

        for (int i = 0; i < numberOfShots; i++) {
            game.handleShot((new Shot(firstPlayerID, fieldsForSecondPlayerShip.get(0))));
        }

        //Act
        Set<Integer> misses = game.getGuaranteedMisses(firstPlayerID);

        //Assert
        Assert.assertTrue(misses.containsAll(properMisses) && properMisses.containsAll(misses));

    }

    @DataProvider
    public static Object[][] dprov_forHandleShotTestForPlayer() {
        return new Object[][]{
                {21, new ArrayList<>(Arrays.asList(20, 21, 22))},
                {1, new ArrayList<>(Arrays.asList(1, 2, 3, 4))},
                {20, new ArrayList<>(Arrays.asList(20, 21, 22))},
                {89, new ArrayList<>(Arrays.asList(87, 88, 89))},
                {44, new ArrayList<>(Arrays.asList(34, 44, 54))},
                {33, new ArrayList<>(Arrays.asList(13, 23, 33))},
        };
    }

    @Test(dataProvider = "dprov_forHandleShotTestForPlayer")
    public void testHandleShot_whenSecondPlayerShotShip_returnShotOutcomeHit(int fieldToShot, List<Integer> fieldsForFirstPlayerShip) {
        //Arrange
        Board boardForFirstPlayer = new Board(new ArrayList<>(Collections.singletonList(
                new Ship(fieldsForFirstPlayerShip))));

        Board boardForSecondPlayer = new Board(new ArrayList<>());

        boardsInGame.put(firstPlayerID, boardForFirstPlayer);
        boardsInGame.put(secondPlayerID, boardForSecondPlayer);

        game.handleShot(new Shot(firstPlayerID, 6));

        //Act
        ShotResult shotResult = game.handleShot(new Shot(secondPlayerID, fieldToShot));

        //Assert
        Assert.assertEquals(shotResult.getShotOutcome(), ShotOutcome.HIT);
    }


    @Test(dataProvider = "dprov_forHandleShotTestForPlayer")
    public void testHandleShot_whenFirstPlayerShotShip_returnShotOutcomeHit(int fieldToShot, List<Integer> fieldsForSecondPlayerShip) {
        //Arrange
        Board boardForFirstPlayer = new Board(new ArrayList<>());

        Board boardForSecondPlayer = new Board(new ArrayList<>(Collections.singletonList(
                new Ship(fieldsForSecondPlayerShip))));

        boardsInGame.put(firstPlayerID, boardForFirstPlayer);
        boardsInGame.put(secondPlayerID, boardForSecondPlayer);


        //Act
        ShotResult shotResult = game.handleShot(new Shot(firstPlayerID, fieldToShot));

        //Assert
        Assert.assertEquals(shotResult.getShotOutcome(), ShotOutcome.HIT);
    }
}