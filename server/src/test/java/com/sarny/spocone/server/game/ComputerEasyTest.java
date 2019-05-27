package com.sarny.spocone.server.game;

import com.sarny.spocone.publicclasses.shot.Shot;
import com.sarny.spocone.server.game.computer_players.AI;
import com.sarny.spocone.server.game.computer_players.ComputerEasy;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

import static org.testng.Assert.assertFalse;

/**
 * @author Kamil Rojek
 */
public class ComputerEasyTest {

    private AI computer;

    @BeforeMethod
    public void setUp() {
        computer = new ComputerEasy(AI.generateID());
    }

    @Test(invocationCount = 100)
    public void testGenerateFieldToShot_whenCalledMultipleTimes_returnUniqueShotObjects() {
        // arrange
        Set<Shot> generatedShots = new HashSet<>();
        boolean duplicatesFound = false;
        int shotsToGenerate = 50;

        // act
        for (int i = shotsToGenerate; i > 0; i--) {
            Shot shot = computer.generateShot();
            if (!generatedShots.add(shot)) {
                duplicatesFound = true;
            }
        }

        // assert
        assertFalse(duplicatesFound);
    }
}