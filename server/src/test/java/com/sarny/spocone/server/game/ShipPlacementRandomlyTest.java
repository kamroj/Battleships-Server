package com.sarny.spocone.server.game;

import org.mockito.ArgumentMatchers;
import org.mockito.invocation.Invocation;
import org.testng.annotations.Test;

import java.util.Collection;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

/**
 * @author Agnieszka Trzewik
 */
public class ShipPlacementRandomlyTest {

    @Test
    public void testPlaceAllShips_whenPlaceAllShipsAllShipsPlaced_returnTrue() {
        // arrange
        ShipPlacementValidator.Builder builder = new ShipPlacementValidator.Builder();
        ShipPlacementValidator shipPlacementValidator = builder
                .withGuaranteedMissGenerator(new ShipNeighbouringFieldsGenerator())
                .forBoard(new Board());

        ShipPlacementRandomly shipPlacementRandomly = new ShipPlacementRandomly(shipPlacementValidator);

        // act
        shipPlacementRandomly.placeAllShipsRandomly();

        // assert
        assertTrue(shipPlacementValidator.allShipsPlaced());

    }

    @Test
    public void testPlaceAllShips_whenPlaceAllBoardHave10Ships_returnTrue() {
        // arrange
        ShipPlacementValidator shipPlacementValidator = mock(ShipPlacementValidator.class);

        when(shipPlacementValidator.validate(ArgumentMatchers.any(Ship.class))).thenReturn(true);


        ShipPlacementRandomly shipPlacementRandomly = new ShipPlacementRandomly(shipPlacementValidator);

        // act
        shipPlacementRandomly.placeAllShipsRandomly();

        // assert
        Collection<Invocation> invocations = mockingDetails(shipPlacementValidator).getInvocations();
        long validationCalls = invocations.stream().filter(invocation -> invocation.toString().contains("validate")).count();
        assertEquals(validationCalls, 10);
    }
}