package com.sarny.spocone.server.chat;

import com.sarny.spocone.publicclasses.chat.ChatMessage;
import com.sarny.spocone.publicclasses.shot.ShotOutcome;
import com.sarny.spocone.publicclasses.shot.ShotResult;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

/**
 * @author Wojciech Makiela
 */
public class ChatServiceTest {

    @Test
    public void testChatService_whenPassingDifferentMessages_returnLast20OrLess(){
        // arrange
        ChatService service = new ChatService();
        int gameId = 1;
        int firstPlayerId = 1;
        int secondPlayerId = 2;
        String language = "EN";
        List<String> expectedMessages = new ArrayList<>();
        expectedMessages.add("[SERVER]: Player 1 joined!");
        expectedMessages.add("[SERVER]: Player 2 joined!");
        expectedMessages.add("[1]: Hello");
        expectedMessages.add("[2]: Hello");
        expectedMessages.add("[SERVER]: Player 1 shot field 0 : Miss!");
        expectedMessages.add("[SERVER]: Turn of player 1 ended.");
        expectedMessages.add("[2]: My next shot will end this game");
        expectedMessages.add("[SERVER]: Player 2 shot field 0 : Game over!");
        expectedMessages.add("[SERVER]: ~~~Game Over~~~");

        // act
        service.getChatMessagesAsStrings(firstPlayerId, gameId, language);
        service.getChatMessagesAsStrings(secondPlayerId, gameId, language);
        service.addUserMessageAndGetChat(new ChatMessage(firstPlayerId, gameId, "Hello", language));
        service.addUserMessageAndGetChat(new ChatMessage(secondPlayerId, gameId, "Hello", language));
        service.addPlayerShotMessage(firstPlayerId, new ShotResult(ShotOutcome.MISS, 0), gameId);
        service.addTurnEndedMessage(firstPlayerId, gameId);
        service.addUserMessageAndGetChat(new ChatMessage(secondPlayerId, gameId, "My next shot will end this game", language));
        service.addPlayerShotMessage(secondPlayerId, new ShotResult(ShotOutcome.WIN, 0), gameId);
        service.addGameEndedMessage(gameId);

        // assert
        assertEquals(expectedMessages, service.getChatMessagesAsStrings(firstPlayerId, gameId, language));
    }
}