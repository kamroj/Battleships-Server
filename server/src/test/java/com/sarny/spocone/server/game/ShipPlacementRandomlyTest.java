package com.sarny.spocone.server.game;

import org.mockito.ArgumentMatchers;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.invocation.Invocation;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
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

        when(shipPlacementValidator.validateHorizontally(ArgumentMatchers.any(Ship.class))).thenReturn(true);
        when(shipPlacementValidator.validateVertically(ArgumentMatchers.any(Ship.class))).thenReturn(true);


        ShipPlacementRandomly shipPlacementRandomly = new ShipPlacementRandomly(shipPlacementValidator);

        // act
        shipPlacementRandomly.placeAllShipsRandomly();

        // assert
        Collection<Invocation> invocations = mockingDetails(shipPlacementValidator).getInvocations();
        long validationCalls = invocations.stream().filter(invocation -> invocation.toString().contains("validate")).count();
        assertEquals(validationCalls, 10);
    }

    @Test(invocationCount = 20)
    public void testPlaceAllShips_whenShipsCanBePlacedOnlyVertically_returnTrue() {
        // arrange
        ShipPlacementValidator shipPlacementValidator = mock(ShipPlacementValidator.class);

        when(shipPlacementValidator.validateHorizontally(ArgumentMatchers.any(Ship.class)))
                .thenReturn(false)
                .thenReturn(false)
                .thenReturn(false)
                .thenReturn(false)
                .thenReturn(true);
        when(shipPlacementValidator.validateVertically(ArgumentMatchers.any(Ship.class)))
                .thenReturn(false)
                .thenReturn(false)
                .thenReturn(false)
                .thenReturn(false)
                .thenReturn(true);

        ShipPlacementRandomly shipPlacementRandomly = new ShipPlacementRandomly(shipPlacementValidator);

        // act
        shipPlacementRandomly.placeAllShipsRandomly();

        // assert
        Collection<Invocation> invocations = mockingDetails(shipPlacementValidator).getInvocations();
        long verticalValidationCalls = invocations.stream().filter(invocation -> invocation.toString().contains("Vertically")).count();
        long horizontalValidationCalls = invocations.stream().filter(invocation -> invocation.toString().contains("Horizontally")).count();
        assertTrue(verticalValidationCalls == 0 || verticalValidationCalls >= 5);
        assertTrue(horizontalValidationCalls == 0 || horizontalValidationCalls >= 5);
    }

}