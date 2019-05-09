package com.sarny.spocone.server.game;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.*;

/**
 * @author Agnieszka Trzewik
 */
public class GuaranteedMissGeneratorTest {

    @DataProvider
    public static Object[][] dprov_shipFieldsAndProperMisses() {
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

    @Test(dataProvider = "dprov_shipFieldsAndProperMisses")
    public void testGenerateMisses_whenShipsGenerateProperly_returnTrue(List<Integer> shipFields, Set<Integer> properMisses) {
        Ship ship = new Ship(Collections.emptyList());
        ship.hit = shipFields;
        Set<Integer> misses = new GuaranteedMissGenerator().generateMisses(ship);
        assert misses.containsAll(properMisses) && properMisses.containsAll(misses);
    }
}