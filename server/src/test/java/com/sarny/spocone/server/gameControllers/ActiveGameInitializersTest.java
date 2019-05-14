package com.sarny.spocone.server.gameControllers;

import com.sarny.spocone.server.game.GameInitializer;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

/**
 * @author Wojciech Makiela
 */
public class ActiveGameInitializersTest {

    @Test
    public void testAddNewActiveGameInitializer_whenPassedValidData_persistData() {
        // arrange
        ActiveGameInitializers activeGameInitializers = new ActiveGameInitializers();
        GameInitializer initializer = new GameInitializer(1, 2);

        // act
        activeGameInitializers.addNewActiveGameInitializer(initializer, 1, 2);
        // assert
        assertEquals(initializer, activeGameInitializers.getInitializerForPlayer(1));
        assertEquals(initializer, activeGameInitializers.getInitializerForPlayer(2));
    }

    @Test
    public void testRemoveInitializerForPlayers_whenPassedValidData() {
        // arrange
        ActiveGameInitializers activeGameInitializers = new ActiveGameInitializers();
        GameInitializer initializer = new GameInitializer(1, 2);
        GameInitializer initializer2 = new GameInitializer(3, 4);
        activeGameInitializers.addNewActiveGameInitializer(initializer, 1, 2);
        activeGameInitializers.addNewActiveGameInitializer(initializer2, 3, 4);

        // act
        activeGameInitializers.removeInitializerForPlayers(1, 2, 3, 4);

        // assert
        assertNull(activeGameInitializers.getInitializerForPlayer(1));
        assertNull(activeGameInitializers.getInitializerForPlayer(2));
        assertNull(activeGameInitializers.getInitializerForPlayer(3));
        assertNull(activeGameInitializers.getInitializerForPlayer(4));
    }
}