package com.sarny.spocone.server.game;

import com.sarny.spocone.publicclasses.ship.ShipDTO;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * @author Kamil Rojek
 */
public class BoardInitializerTest {
    private BoardInitializer boardInitializer;

    @BeforeMethod
    public void setUp() {
        boardInitializer = new BoardInitializer();
    }

    @Test
    public void testPlaceShip_validShipPlacement_returnShipThatWerePlaced() throws InvalidShipPlacementException {
        //Arrange
        Ship ship = new Ship(Arrays.asList(1, 2, 3));

        //Act
        ShipDTO shipDTO = boardInitializer.placeShip(ship);

        //Assert
        assertEquals(shipDTO, new ShipDTO(Arrays.asList(1, 2, 3)));
    }

    @Test(expectedExceptions = InvalidShipPlacementException.class)
    public void testPlaceShip_invalidShipPlacement_throwInvalidShipPlacement() throws InvalidShipPlacementException {
        //Arrange
        Ship ship = new Ship(Arrays.asList(1, 2, 3));
        Ship ship2 = new Ship(Arrays.asList(1, 2, 3));

        //Act
        boardInitializer.placeShip(ship);
        boardInitializer.placeShip(ship2);
    }

    @Test
    public void testGenerate_validGenerationOfBoard_returnBoard() throws InvalidBoardCreationException, InvalidShipPlacementException {
        //Arrange
        /*
        1 O 2 2 O O 3 O O 3
        O O O O O O 3 O O 3
        1 O O O O O 3 O O 3
        O O O O O O O O O O
        O O O O O O O O O O
        4 4 4 4 O O O O O 2
        O O O O O O O O O 2
        O O O O O O O O O O
        O O O O O O O O 1 O
        2 2 O O O O 1 O O O
         */
        List<Ship> defaultShips = Arrays.asList(
                //One mast
                new Ship(Collections.singletonList(0)),
                new Ship(Collections.singletonList(20)),
                new Ship(Collections.singletonList(88)),
                new Ship(Collections.singletonList(96)),

                //Two masts
                new Ship(Arrays.asList(2, 3)),
                new Ship(Arrays.asList(59, 69)),
                new Ship(Arrays.asList(90, 91)),

                //Three masts
                new Ship(Arrays.asList(6, 16, 26)),
                new Ship(Arrays.asList(9, 19, 29)),

                //Four mast
                new Ship(Arrays.asList(60, 61, 62, 63))
        );

        //Act
        for (Ship ship : defaultShips) {
            boardInitializer.placeShip(ship);
        }
        Board board = boardInitializer.generateBoard();

        //Assert
        assertNotNull(board);
    }

    @Test(expectedExceptions = InvalidBoardCreationException.class)
    public void testGenerate_invalidGenerationOfBoard_returnBoard() throws InvalidBoardCreationException, InvalidShipPlacementException {
        //Arrange
        /*
        1 O 2 2 O O 3 O O 3
        O O O O O O 3 O O 3
        1 O O O O O 3 O O 3
        O O O O O O O O O O
        O O O O O O O O O O
        4 4 4 4 O O O O O 2
        O O O O O O O O O 2
        O O O O O O O O O O
        O O O O O O O O 1 O
        2 2 O O O O 1 O O O
         */
        List<Ship> defaultShips = Arrays.asList(
                //One mast
                new Ship(Collections.singletonList(0)),
                new Ship(Collections.singletonList(20))
        );

        //Act
        for (Ship ship : defaultShips) {
            boardInitializer.placeShip(ship);
        }
        boardInitializer.generateBoard();
    }

}