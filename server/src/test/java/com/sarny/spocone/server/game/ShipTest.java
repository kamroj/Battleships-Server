package com.sarny.spocone.server.game;

import com.sarny.spocone.publicclasses.shot.ShotOutcome;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Kamil Rojek
 */
public class ShipTest {

    @DataProvider
    public static Object[][] dprov_isOnField_generateTrueParameters() {
        return new Object[][]{
                {List.of(1, 2, 3), 1},
                {List.of(1, 11, 21), 11},
                {List.of(4, 5, 6), 5},
                {List.of(23, 33, 43), 43},
                {List.of(97, 98, 99), 99},
        };
    }

    @DataProvider
    public static Object[][] dprov_isOnField_generateFalseParameters() {
        return new Object[][]{
                {List.of(1, 2, 3), 4},
                {List.of(1, 11, 21), 0},
                {List.of(4, 5, 6), 12},
                {List.of(23, 33, 43), 3},
                {List.of(97, 98, 99), 0},
        };
    }

    @Test(dataProvider = "dprov_isOnField_generateTrueParameters")
    public void testIsOnField_whenPointingToFieldWhereShipIsPlaced_returnTrue(List<Integer> shipFields, int fieldOnBoard) {
        //Arrange
        Ship ship = new Ship(shipFields);

        //Act
        boolean isOnField = ship.isOnField(fieldOnBoard);

        //Assert
        Assert.assertTrue(isOnField);
    }

    @Test(dataProvider = "dprov_isOnField_generateFalseParameters")
    public void testIsOnField_whenPointingToFieldWhereShipIsNotPlaced_returnFalse(List<Integer> shipFields, int fieldOnBoard) {
        //Arrange
        Ship ship = new Ship(shipFields);

        //Act
        boolean isOnField = ship.isOnField(fieldOnBoard);

        //Assert
        Assert.assertFalse(isOnField);
    }

    @Test
    public void testFire_whenNotHitShip_returnShotOutcomeMiss() {
        //Arrange
        Ship ship = new Ship(Arrays.asList(0, 1, 2));

        //Act
        ShotOutcome fireResult = ship.fire(4);

        //Assert
        Assert.assertEquals(fireResult, ShotOutcome.MISS);
    }

    @Test
    public void testFire_whenHitShipButNotSunk_returnShotOutcomeHit() {
        //Arrange
        Ship ship = new Ship(new ArrayList<>(Arrays.asList(0, 1, 2)));

        //Act
        ShotOutcome fireResult = ship.fire(0);

        //Assert
        Assert.assertEquals(fireResult, ShotOutcome.HIT);
    }

    @Test
    public void testFire_whenHitShipAndSunk_returnShotOutcomeSunk() {
        //Arrange
        Ship ship = new Ship(new ArrayList<>(Arrays.asList(0, 1, 2)));

        //Act
        ship.fire(0);
        ship.fire(1);
        ShotOutcome fireResult = ship.fire(2);

        //Assert
        Assert.assertEquals(fireResult, ShotOutcome.SUNK);
    }

    @Test
    public void testEquals_whenTwoShipsHaveSameSize_returnTrue() {
        //Arrange
        Ship firstShip = new Ship(Arrays.asList(1, 2, 3));
        Ship secondShip = new Ship(Arrays.asList(1, 2, 3));

        //Assert
        Assert.assertEquals(firstShip, secondShip);
    }

    @Test
    public void testEquals_whenShipsHaveDifferentSize_returnFalse() {
        //Arrange
        Ship firstShip = new Ship(Arrays.asList(1, 2, 3));
        Ship secondShip = new Ship(Arrays.asList(1, 2, 3, 4));

        //Assert
        Assert.assertNotEquals(firstShip, secondShip);
    }

    @Test
    public void testHashCode_whenTwoShipsHaveSameSize_returnTrue() {
        //Arrange
        Ship firstShip = new Ship(Arrays.asList(1, 2, 3));
        Ship secondShip = new Ship(Arrays.asList(1, 2, 3));

        //Assert
        Assert.assertEquals(firstShip.hashCode(), secondShip.hashCode());
    }

    @Test
    public void testHashCode_whenShipsHaveDifferentSize_returnTrue() {
        //Arrange
        Ship firstShip = new Ship(Arrays.asList(1, 2, 3));
        Ship secondShip = new Ship(Arrays.asList(1, 2, 3, 4));

        //Assert
        Assert.assertNotEquals(firstShip.hashCode(), secondShip.hashCode());
    }
}
