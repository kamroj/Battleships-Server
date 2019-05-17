package com.sarny.spocone.server.game;

import com.sarny.spocone.publicclasses.shot.ShotOutcome;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Kamil Rojek
 */
public class BoardTest {

    @DataProvider
    public static Object[][] dprov_shipsGenerator() {
        return new Object[][]{
                {new ArrayList<>(Arrays.asList(
                        new Ship(new ArrayList<>(Arrays.asList(0, 1, 2))),
                        new Ship(new ArrayList<>(Arrays.asList(4, 5, 6))),
                        new Ship(new ArrayList<>(Arrays.asList(40, 50, 60))))),
                },
                {new ArrayList<>(Arrays.asList(
                        new Ship(new ArrayList<>(Arrays.asList(0, 1, 2))),
                        new Ship(new ArrayList<>(Arrays.asList(30, 40, 50))),
                        new Ship(new ArrayList<>(Arrays.asList(60, 70, 80))))),
                },
        };
    }

    @Test(dataProvider = "dprov_shipsGenerator")
    public void testMarkShot_whenNotHitShip_returnMiss(List<Ship> ships) {
        //Arrange
        Board board = new Board(ships);

        //Act
        ShotOutcome markResult = board.markShot(9);

        //Assert
        Assert.assertEquals(markResult, ShotOutcome.MISS);
    }

    @Test(dataProvider = "dprov_shipsGenerator")
    public void testMarkShot_whenHitShip_returnHit(List<Ship> ships) {
        //Arrange
        Board board = new Board(ships);

        //Act
        ShotOutcome markResult = board.markShot(0);

        //Assert
        Assert.assertEquals(markResult, ShotOutcome.HIT);
    }

    @Test(dataProvider = "dprov_shipsGenerator")
    public void testMarkShot_whenHitShipAndSunk_returnSunk(List<Ship> ships) {
        //Arrange
        Board board = new Board(ships);

        //Act
        board.markShot(ships.get(0).fieldsToHit.get(0));
        board.markShot(ships.get(0).fieldsToHit.get(0));
        ShotOutcome markResult = board.markShot(ships.get(0).fieldsToHit.get(0));

        //Assert
        Assert.assertEquals(markResult, ShotOutcome.SUNK);
    }

    @Test
    public void testMarkShot_whenAllShipSunk_returnWin() {
        //Arrange
        List<Ship> ships = new ArrayList<>(Collections.singletonList(
                new Ship(new ArrayList<>(Arrays.asList(0, 1, 2)))
        ));

        Board board = new Board(ships);

        //Act
        board.markShot(0);
        board.markShot(1);
        ShotOutcome markResult = board.markShot(2);

        //Assert
        Assert.assertEquals(markResult, ShotOutcome.WIN);
    }
}
