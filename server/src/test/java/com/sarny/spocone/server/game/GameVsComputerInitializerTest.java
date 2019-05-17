package com.sarny.spocone.server.game;

import com.sarny.spocone.server.game.computer_players.ComputerEasy;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * @author Wojciech Makiela
 */
public class GameVsComputerInitializerTest {
    List<Ship> shipsToPlace = Arrays.asList(
            new Ship(new ArrayList<>(Arrays.asList(1, 2, 3, 4))),
            new Ship(new ArrayList<>(Arrays.asList(6, 7, 8))),
            new Ship(new ArrayList<>(Arrays.asList(20, 21, 22))),
            new Ship(new ArrayList<>(Arrays.asList(24, 25))),
            new Ship(new ArrayList<>(Arrays.asList(27, 28))),
            new Ship(new ArrayList<>(Arrays.asList(40, 41))),
            new Ship(new ArrayList<>(Collections.singletonList(43))),
            new Ship(new ArrayList<>(Collections.singletonList(45))),
            new Ship(new ArrayList<>(Collections.singletonList(47))),
            new Ship(new ArrayList<>(Collections.singletonList(49)))
    );

    @Test
    public void testAreBothPlayersDone_whenHumanPlayerPlacesAllShips_returnTrue() throws InvalidShipPlacementException {
        // arrange
        GameVsComputerInitializer initializer = new GameVsComputerInitializer(1, 2, new ComputerEasy(2));
        for (Ship ship : shipsToPlace) {
            initializer.placeShip(1, ship);
        }

        // act
        boolean areReady = initializer.areBothPlayersDone();
        // assert
        assertTrue(areReady);
    }

    @Test(invocationCount = 10000)
    public void testGenerateGame_whenHumanPlayerFinishedPlacingShips_returnValidBoardForAI() throws InvalidBoardCreationException, InvalidShipPlacementException {
        // arrange
        int AI_ID = 2;
        GameVsComputerInitializer initializer = new GameVsComputerInitializer(1, AI_ID, new ComputerEasy(AI_ID));
        for (Ship ship : shipsToPlace) {
            initializer.placeShip(1, ship);
        }
        Game game = initializer.generateGame();

        // act
        int shipsFound = 0;
        for (int i = 0; i < 100; i++) {
            if (game.activeBoards.getShipOnField(i, AI_ID) != null) {
                shipsFound++;
            }
        }

        // assert
        assertEquals(shipsFound, 20);
    }

    @Test(expectedExceptions = InvalidBoardCreationException.class)
    public void testGenerateGame_whenCalledWithoutAnyPlayersShips_throwException() throws InvalidBoardCreationException {
        // act
        new GameVsComputerInitializer(1, 2, new ComputerEasy(2)).generateGame();
    }
}