package com.sarny.spocone.server.game;

import com.sarny.spocone.publicclasses.shot.Shot;
import com.sarny.spocone.publicclasses.shot.ShotOutcome;
import com.sarny.spocone.publicclasses.shot.ShotResult;
import com.sarny.spocone.publicclasses.shot.ShotsSummary;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.*;

import static org.testng.Assert.*;

/**
 * @author Kamil Rojek
 */
public class RoundTest {

    private final int FIRST_PLAYER_ID = 1;
    private final int SECOND_PLAYER_ID = 2;
    private Round round;

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

        ActiveBoards activeBoards = new ActiveBoards(boardsInGame);
        List<Integer> playersIDs = new ArrayList<>(List.of(1, 2));
        round = new Round(activeBoards, playersIDs);
    }

    @DataProvider
    public static Object[][] dprov_generateMissShots() {
        return new Object[][]{
                {4}, {5}, {6}, {7}, {8}, {9},
                {55}, {67}, {99}, {88}, {45}, {57}
        };
    }

    @Test(dataProvider = "dprov_generateMissShots")
    public void testHandleShot_shotIntoWater_returnShotObjectWithMISS(int fieldNumber) {
        //Arrange
        Shot shot = new Shot(1, fieldNumber);

        //Act
        ShotResult shotResult = round.handleShot(shot);
        ShotOutcome outcomeResult = shotResult.getShotOutcome();

        //Assert
        assertEquals(outcomeResult, ShotOutcome.MISS);
        assertEquals(shotResult.getField(), fieldNumber);
    }

    @Test
    public void testGetSecondPlayerShots_whenSecondPlayerHasShotOn2and3Field_returnShotResults2and3() {
        //Arrange
        Shot firstShot = new Shot(FIRST_PLAYER_ID, 2);
        Shot secondShot = new Shot(FIRST_PLAYER_ID, 3);

        //Act
        round.handleShot(firstShot);
        round.handleShot(secondShot);
        ShotsSummary opponentPlayerShots = round.getOpponentsShots(SECOND_PLAYER_ID);
        List<ShotResult> shotResultList = opponentPlayerShots.getShotResults();

        //Assert
        assertEquals(shotResultList.get(0).getField(), 2);
        assertEquals(shotResultList.get(1).getField(), 3);
    }

    @Test
    public void testGetSecondPlayerShots_whenSecondPlayerHasShotOn2And3FieldAndHITShip_returnShotResults2and3HIT() {
        //Arrange
        Shot firstShot = new Shot(FIRST_PLAYER_ID, 2);
        Shot secondShot = new Shot(FIRST_PLAYER_ID, 3);

        //Act
        round.handleShot(firstShot);
        round.handleShot(secondShot);
        ShotsSummary opponentPlayerShots = round.getOpponentsShots(SECOND_PLAYER_ID);
        List<ShotResult> shotResultList = opponentPlayerShots.getShotResults();

        //Assert
        assertEquals(shotResultList.get(0).getShotOutcome(), ShotOutcome.HIT);
        assertEquals(shotResultList.get(1).getShotOutcome(), ShotOutcome.HIT);
    }

    @Test
    public void testLastSunkField_whenLastSunkFieldIs3_returnTrue() {
        //Arrange
        Shot firstShot = new Shot(FIRST_PLAYER_ID, 1);
        Shot secondShot = new Shot(FIRST_PLAYER_ID, 2);
        Shot thirdShot = new Shot(FIRST_PLAYER_ID, 3);

        //Act
        round.handleShot(firstShot);
        round.handleShot(secondShot);
        round.handleShot(thirdShot);
        int fieldNumber = round.getLastSunkField(FIRST_PLAYER_ID);

        //Assert
        assertEquals(fieldNumber, 3);
    }

    @Test
    public void testIsPlayerRound_player2AsksForTurnWhenFirstPlayerHasFinishedHis_returnFalse() {
        //Arrange
        Shot firstPlayerMissShot = new Shot(FIRST_PLAYER_ID, 6);

        //Act
        round.handleShot(firstPlayerMissShot);
        boolean isPlayerRound = round.isPlayerRound(SECOND_PLAYER_ID);

        //Assert
        assertTrue(isPlayerRound);
    }

    @Test
    public void testIsPlayerRound_player2AsksDuringPlayerOneRound_returnFalse() {
        //Act
        boolean isPlayerRound = round.isPlayerRound(SECOND_PLAYER_ID);

        //Assert
        assertFalse(isPlayerRound);
    }
}
