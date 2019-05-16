package com.sarny.spocone.server.game;

import org.testng.annotations.Test;

import java.util.*;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @author Wojciech Makiela
 */
public class ShipPlacementValidatorTest {

    @Test
    public void testAllShipsPlaced_whenShipsOfLength1Remain_returnFalse() {
        // arrange
        Board board = new Board(new ArrayList<>());
        ShipPlacementValidator.Builder builder = new ShipPlacementValidator.Builder();
        ShipPlacementValidator shipPlacementValidator = builder
                .withShipsOfLength4(0)
                .withShipsOfLength3(0)
                .withShipsOfLength2(1)
                .withShipsOfLength1(1)
                .withGuaranteedMissGenerator(new ShipNeighbouringFieldsGenerator())
                .forBoard(board);
        // act
        shipPlacementValidator.placeNewShip(new Ship(Arrays.asList(60, 61)));
        // assert
        assertFalse(shipPlacementValidator.allShipsPlaced());
    }

    @Test
    public void testAllShipsPlaced_whenShipsOfLength2Remain_returnFalse() {
        // arrange
        Board board = new Board(new ArrayList<>());
        ShipPlacementValidator.Builder builder = new ShipPlacementValidator.Builder();
        ShipPlacementValidator shipPlacementValidator = builder
                .withShipsOfLength4(0)
                .withShipsOfLength3(1)
                .withShipsOfLength2(5)
                .withShipsOfLength1(0)
                .withGuaranteedMissGenerator(new ShipNeighbouringFieldsGenerator())
                .forBoard(board);
        // act
        shipPlacementValidator.placeNewShip(new Ship(Arrays.asList(60, 61, 62)));
        // assert
        assertFalse(shipPlacementValidator.allShipsPlaced());
    }

    @Test
    public void testAllShipsPlaced_whenShipsOfLength3Remain_returnFalse() {
        // arrange
        Board board = new Board(new ArrayList<>());
        ShipPlacementValidator.Builder builder = new ShipPlacementValidator.Builder();
        ShipPlacementValidator shipPlacementValidator = builder
                .withShipsOfLength4(1)
                .withShipsOfLength3(2)
                .withShipsOfLength2(0)
                .withShipsOfLength1(0)
                .withGuaranteedMissGenerator(new ShipNeighbouringFieldsGenerator())
                .forBoard(board);
        // act
        shipPlacementValidator.placeNewShip(new Ship(Arrays.asList(1, 2, 3, 4)));
        // assert
        assertFalse(shipPlacementValidator.allShipsPlaced());
    }

    @Test
    public void testAllShipsPlaced_whenShipsOfLength4Remain_returnFalse() {
        // arrange
        Board board = new Board(new ArrayList<>());
        ShipPlacementValidator.Builder builder = new ShipPlacementValidator.Builder();
        ShipPlacementValidator shipPlacementValidator = builder
                .withShipsOfLength4(2)
                .withShipsOfLength3(0)
                .withShipsOfLength2(0)
                .withShipsOfLength1(0)
                .withGuaranteedMissGenerator(new ShipNeighbouringFieldsGenerator())
                .forBoard(board);
        // act
        shipPlacementValidator.placeNewShip(new Ship(Arrays.asList(1, 2, 3, 4)));
        // assert
        assertFalse(shipPlacementValidator.allShipsPlaced());
    }

    @Test
    public void testAllShipsPlaced_whenOneShipNeededToBePlaced_returnTrue() {
        // arrange
        Board board = new Board(new ArrayList<>());
        ShipPlacementValidator.Builder builder = new ShipPlacementValidator.Builder();
        ShipPlacementValidator shipPlacementValidator = builder
                .withShipsOfLength4(1)
                .withShipsOfLength3(0)
                .withShipsOfLength2(0)
                .withShipsOfLength1(0)
                .withGuaranteedMissGenerator(new ShipNeighbouringFieldsGenerator())
                .forBoard(board);
        // act
        shipPlacementValidator.placeNewShip(new Ship(Arrays.asList(1, 2, 3, 4)));
        // assert
        assertTrue(shipPlacementValidator.allShipsPlaced());
    }

    @Test
    public void testAllShipsPlaced_whenAllShipsPlaced_returnTrue() {
        // arrange
        Board board = new Board(new ArrayList<>());
        ShipPlacementValidator.Builder builder = new ShipPlacementValidator.Builder();
        ShipPlacementValidator shipPlacementValidator = builder
                .withShipsOfLength4(1)
                .withShipsOfLength3(2)
                .withShipsOfLength2(3)
                .withShipsOfLength1(4)
                .withGuaranteedMissGenerator(new ShipNeighbouringFieldsGenerator())
                .forBoard(board);
        // act - Place all requested ships
        shipPlacementValidator.placeNewShip(new Ship(Arrays.asList(1, 2, 3, 4)));
        shipPlacementValidator.placeNewShip(new Ship(Arrays.asList(10, 11, 12)));
        shipPlacementValidator.placeNewShip(new Ship(Arrays.asList(14, 15, 16)));
        shipPlacementValidator.placeNewShip(new Ship(Arrays.asList(20, 21)));
        shipPlacementValidator.placeNewShip(new Ship(Arrays.asList(23, 24)));
        shipPlacementValidator.placeNewShip(new Ship(Arrays.asList(26, 27)));
        shipPlacementValidator.placeNewShip(new Ship(Collections.singletonList(30)));
        shipPlacementValidator.placeNewShip(new Ship(Collections.singletonList(40)));
        shipPlacementValidator.placeNewShip(new Ship(Collections.singletonList(50)));
        shipPlacementValidator.placeNewShip(new Ship(Collections.singletonList(60)));
        // assert
        assertTrue(shipPlacementValidator.allShipsPlaced());
    }

    @Test
    public void testValidate_whenShipCanBePlaced_returnTrue() {
        // arrange
        /*
        Ships placed in order
        1 0 2 2 2 2 0 3 3 3
        1 0 0 0 0 0 0 0 0 0
        1 0 0 0 0 0 0 0 0 0
        0 0 0 0 0 0 0 0 0 0
        4 0 5 5 0 0 0 0 0 0
        0 0 0 0 0 0 0 0 0 0
        6 0 7 0 8 0 9 0 0 0
        6 0 7 0 8 0 0 0 0 0
        6 0 7 0 0 0 0 0 0 0
        6 0 0 0 0 0 0 0 0 0
         */

        List<Ship> shipsToPlace = getShipsToPlace();
        List<Ship> placedShips = new ArrayList<>();
        Board board = new Board(placedShips);
        ShipPlacementValidator.Builder builder = new ShipPlacementValidator.Builder();
        ShipPlacementValidator shipPlacementValidator = builder
                .withShipsOfLength4(2)
                .withShipsOfLength3(3)
                .withShipsOfLength2(2)
                .withShipsOfLength1(2)
                .withGuaranteedMissGenerator(new ShipNeighbouringFieldsGenerator())
                .forBoard(board);

        // assert
        for (Ship ship : shipsToPlace) {
            assertTrue(shipPlacementValidator.validate(ship), String.format("Tried to place ship: %s", ship.toString()));
            shipPlacementValidator.placeNewShip(ship);
            placedShips.add(ship);
        }
    }


    @Test
    public void testValidate_whenPlacedTooManyShipsOfLength4_returnFalse() {
        // arrange
        /*
        Ships placed in order
        1 0 2 2 2 2 0 3 3 3
        1 0 0 0 0 0 0 0 0 0
        1 0 0 0 0 0 0 0 0 0
        0 0 0 0 0 0 0 0 0 0
        4 0 5 5 0 0 0 0 0 0
        0 0 0 0 0 0 0 0 0 0
        6 0 7 0 8 0 9 0 0 0
        6 0 7 0 8 0 0 0 0 0
        6 0 7 0 0 0 0 0 0 0
        6 0 0 0 0 0 0 0 0 0
         */

        List<Ship> shipsToPlace = getShipsToPlace();
        Board board = new Board(new ArrayList<>(shipsToPlace));
        ShipPlacementValidator.Builder builder = new ShipPlacementValidator.Builder();
        ShipPlacementValidator shipPlacementValidator = builder
                .withShipsOfLength4(2)
                .withShipsOfLength3(3)
                .withShipsOfLength2(2)
                .withShipsOfLength1(2)
                .withGuaranteedMissGenerator(new ShipNeighbouringFieldsGenerator())
                .forBoard(board);

        for (Ship ship : shipsToPlace) {
            shipPlacementValidator.placeNewShip(ship);
        }

        Ship redundantShip = new Ship(Arrays.asList(99, 98, 97, 96));
        // act
        boolean redundantCanBePlaced = shipPlacementValidator.validate(redundantShip);
        // assert
        assertFalse(redundantCanBePlaced);

    }

    @Test
    public void testValidate_whenPlacedTooManyShipsOfLength3_returnFalse() {
        // arrange
        /*
        Ships placed in order
        1 0 2 2 2 2 0 3 3 3
        1 0 0 0 0 0 0 0 0 0
        1 0 0 0 0 0 0 0 0 0
        0 0 0 0 0 0 0 0 0 0
        4 0 5 5 0 0 0 0 0 0
        0 0 0 0 0 0 0 0 0 0
        6 0 7 0 8 0 9 0 0 0
        6 0 7 0 8 0 0 0 0 0
        6 0 7 0 0 0 0 0 0 0
        6 0 0 0 0 0 0 0 0 0
         */

        List<Ship> shipsToPlace = getShipsToPlace();
        Board board = new Board(new ArrayList<>(shipsToPlace));
        ShipPlacementValidator.Builder builder = new ShipPlacementValidator.Builder();
        ShipPlacementValidator shipPlacementValidator = builder
                .withShipsOfLength4(2)
                .withShipsOfLength3(3)
                .withShipsOfLength2(2)
                .withShipsOfLength1(2)
                .withGuaranteedMissGenerator(new ShipNeighbouringFieldsGenerator())
                .forBoard(board);

        for (Ship ship : shipsToPlace) {
            shipPlacementValidator.placeNewShip(ship);
        }

        Ship redundantShip = new Ship(Arrays.asList(99, 98, 97));
        // act
        boolean redundantCanBePlaced = shipPlacementValidator.validate(redundantShip);
        // assert
        assertFalse(redundantCanBePlaced);

    }

    @Test
    public void testValidate_whenPlacedTooManyShipsOfLength2_returnFalse() {
        // arrange
        /*
        Ships placed in order
        1 0 2 2 2 2 0 3 3 3
        1 0 0 0 0 0 0 0 0 0
        1 0 0 0 0 0 0 0 0 0
        0 0 0 0 0 0 0 0 0 0
        4 0 5 5 0 0 0 0 0 0
        0 0 0 0 0 0 0 0 0 0
        6 0 7 0 8 0 9 0 0 0
        6 0 7 0 8 0 0 0 0 0
        6 0 7 0 0 0 0 0 0 0
        6 0 0 0 0 0 0 0 0 0
         */

        List<Ship> shipsToPlace = getShipsToPlace();
        Board board = new Board(new ArrayList<>());
        ShipPlacementValidator.Builder builder = new ShipPlacementValidator.Builder();
        ShipPlacementValidator shipPlacementValidator = builder
                .withShipsOfLength4(2)
                .withShipsOfLength3(3)
                .withShipsOfLength2(2)
                .withShipsOfLength1(2)
                .withGuaranteedMissGenerator(new ShipNeighbouringFieldsGenerator())
                .forBoard(board);

        for (Ship ship : shipsToPlace) {
            shipPlacementValidator.placeNewShip(ship);
        }

        Ship redundantShip = new Ship(Arrays.asList(99, 98));
        // act
        boolean redundantCanBePlaced = shipPlacementValidator.validate(redundantShip);
        // assert
        assertFalse(redundantCanBePlaced);

    }

    @Test
    public void testValidate_whenPlacedTooManyShipsOfLength1_returnFalse() {
        // arrange
        /*
        Ships placed in order
        1 0 2 2 2 2 0 3 3 3
        1 0 0 0 0 0 0 0 0 0
        1 0 0 0 0 0 0 0 0 0
        0 0 0 0 0 0 0 0 0 0
        4 0 5 5 0 0 0 0 0 0
        0 0 0 0 0 0 0 0 0 0
        6 0 7 0 8 0 9 0 0 0
        6 0 7 0 8 0 0 0 0 0
        6 0 7 0 0 0 0 0 0 0
        6 0 0 0 0 0 0 0 0 0
         */

        List<Ship> shipsToPlace = getShipsToPlace();
        Board board = new Board(new ArrayList<>());
        ShipPlacementValidator.Builder builder = new ShipPlacementValidator.Builder();
        ShipPlacementValidator shipPlacementValidator = builder
                .withShipsOfLength4(2)
                .withShipsOfLength3(3)
                .withShipsOfLength2(2)
                .withShipsOfLength1(2)
                .withGuaranteedMissGenerator(new ShipNeighbouringFieldsGenerator())
                .forBoard(board);

        for (Ship ship : shipsToPlace) {
            shipPlacementValidator.placeNewShip(ship);
        }

        Ship redundantShip = new Ship(Collections.singletonList(99));
        // act
        boolean redundantCanBePlaced = shipPlacementValidator.validate(redundantShip);
        // assert
        assertFalse(redundantCanBePlaced);

    }

    @Test
    public void testValidate_whenPlacedShipOnAnotherShip_returnFalse() {
        // arrange
        /*
        Ships placed in order
        1 0 2 2 2 2 0 3 3 3
        1 0 0 0 0 0 0 0 0 0
        1 0 0 0 0 0 0 0 0 0
        0 0 0 0 0 0 0 0 0 0
        4 0 5 5 0 0 0 0 0 0
        0 0 0 0 0 0 0 0 0 0
        6 0 7 0 8 0 9 0 0 0
        6 0 7 0 8 0 0 0 0 0
        6 0 7 0 0 0 0 0 0 0
        6 0 0 0 0 0 0 0 0 0
         */

        List<Ship> shipsToPlace = getShipsToPlace();
        ArrayList<Ship> placedShips = new ArrayList<>();
        Board board = new Board(placedShips);
        ShipPlacementValidator.Builder builder = new ShipPlacementValidator.Builder();
        ShipPlacementValidator shipPlacementValidator = builder
                .withShipsOfLength4(3)
                .withShipsOfLength3(3)
                .withShipsOfLength2(2)
                .withShipsOfLength1(2)
                .withGuaranteedMissGenerator(new ShipNeighbouringFieldsGenerator())
                .forBoard(board);

        for (Ship ship : shipsToPlace) {
            shipPlacementValidator.placeNewShip(ship);
            placedShips.add(ship);
        }

        Ship redundantShip = new Ship(Arrays.asList(2, 3, 4, 5)); // copy of ship 2
        // act
        boolean redundantCanBePlaced = shipPlacementValidator.validate(redundantShip);
        // assert
        assertFalse(redundantCanBePlaced);

    }

    @Test
    public void testValidate_whenPlacedShipAdjacentAnotherShip_returnFalse() {
        // arrange
        /*
        Ships placed in order
        1 0 2 2 2 2 0 3 3 3
        1 0 0 0 0 0 0 0 0 0
        1 0 0 0 0 0 0 0 0 0
        0 0 0 0 0 0 0 0 0 0
        4 0 5 5 0 0 0 0 0 0
        0 0 0 0 0 0 0 0 0 0
        6 0 7 0 8 0 9 0 0 0
        6 0 7 0 8 0 0 0 0 0
        6 0 7 0 0 0 0 0 0 0
        6 0 0 0 0 0 0 0 0 0
         */

        List<Ship> shipsToPlace = getShipsToPlace();
        List<Ship> placedShips = new ArrayList<>();
        Board board = new Board(placedShips);
        ShipPlacementValidator.Builder builder = new ShipPlacementValidator.Builder();
        ShipPlacementValidator shipPlacementValidator = builder
                .withShipsOfLength4(3)
                .withShipsOfLength3(3)
                .withShipsOfLength2(2)
                .withShipsOfLength1(3)
                .withGuaranteedMissGenerator(new ShipNeighbouringFieldsGenerator())
                .forBoard(board);

        for (Ship ship : shipsToPlace) {
            shipPlacementValidator.placeNewShip(ship);
            placedShips.add(ship);
        }

        Ship redundantShip = new Ship(Collections.singletonList(1)); // ship of length 1 between ships '1' and '2'
        // act
        boolean redundantCanBePlaced = shipPlacementValidator.validate(redundantShip);
        // assert
        assertFalse(redundantCanBePlaced);

    }

    private List<Ship> getShipsToPlace() {
        List<Ship> shipsToPlace = new LinkedList<>();
        shipsToPlace.add(new Ship(Arrays.asList(0, 10, 20)));
        shipsToPlace.add(new Ship(Arrays.asList(2, 3, 4, 5)));
        shipsToPlace.add(new Ship(Arrays.asList(7, 8, 9)));
        shipsToPlace.add(new Ship(Collections.singletonList(40)));
        shipsToPlace.add(new Ship(Arrays.asList(42, 43)));
        shipsToPlace.add(new Ship(Arrays.asList(60, 70, 80, 90)));
        shipsToPlace.add(new Ship(Arrays.asList(62, 72, 82)));
        shipsToPlace.add(new Ship(Arrays.asList(64, 74)));
        shipsToPlace.add(new Ship(Collections.singletonList(66)));
        return shipsToPlace;
    }
}
