package com.sarny.spocone.server.chat.message;

import com.sarny.spocone.publicclasses.shot.ShotOutcome;
import com.sarny.spocone.publicclasses.shot.ShotResult;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author Wojciech Makiela
 */
public class DefaultMessageFactoryTest {

    DefaultMessageFactory messageFactory = new DefaultMessageFactory();

    @Test
    public void testPlayerShot_whenPassedValidData_returnMessageObjectRepresentingShot(){
        // arrange
        int playerId = 1;
        int field = 20;
        ShotOutcome miss = ShotOutcome.MISS;
        ShotResult result = new ShotResult(miss, field);

        // act
        Message message = messageFactory.playerShot(playerId, result);

        // assert
        boolean containsPlayerId = message.asString("en").contains(String.valueOf(playerId));
        boolean containsFieldNumber = message.asString("pl").contains(String.valueOf(field));
        boolean containsShotOutcome = message.asString("en").toLowerCase().contains(miss.toString().toLowerCase());
        assertTrue(containsPlayerId);
        assertTrue(containsFieldNumber);
        assertTrue(containsShotOutcome);
    }
}