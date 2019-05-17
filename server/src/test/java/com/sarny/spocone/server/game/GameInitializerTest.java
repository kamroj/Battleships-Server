package com.sarny.spocone.server.game;

import com.sarny.spocone.publicclasses.ship.ShipDTO;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.testng.Assert.*;

/**
 * @author Kamil Rojek
 */
public class GameInitializerTest {
    private final int FIRST_PLAYER_ID = 1;
    private final int SECOND_PLAYER_ID = 2;
    private GameInitializer gameInitializer;

    @BeforeMethod
    public void setUp() {
        gameInitializer = new GameInitializer(FIRST_PLAYER_ID, SECOND_PLAYER_ID);
    }


    @Test
    public void testPlaceShip_validPlacement_returnShip() throws InvalidShipPlacementException {
        //Arrange
        Ship ship = new Ship(Arrays.asList(1, 2, 3));

        //Act
        ShipDTO shipDTO = gameInitializer.placeShip(FIRST_PLAYER_ID, ship);

        //Assert
        assertEquals(shipDTO, new ShipDTO(Arrays.asList(1, 2, 3)));
    }

    @Test
    public void testAreBothPlayersDone_secondPlayerHasNotFinishedBoardInitialization_returnFalse() throws InvalidShipPlacementException {
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
            gameInitializer.placeShip(FIRST_PLAYER_ID, ship);
        }

        gameInitializer.placeShip(SECOND_PLAYER_ID, new Ship(Arrays.asList(1, 2, 3)));

        boolean result = gameInitializer.areBothPlayersDone();

        //Assert
        assertFalse(result);
    }

    @Test
    public void testGenerateGame_BothPlayersAreReady_returnGame() throws InvalidShipPlacementException, InvalidBoardCreationException {
        //Arrange
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
            gameInitializer.placeShip(FIRST_PLAYER_ID, ship);
            gameInitializer.placeShip(SECOND_PLAYER_ID, ship);
        }

        Game game = gameInitializer.generateGame();

        //Assert
        assertNotNull(game);
    }
}