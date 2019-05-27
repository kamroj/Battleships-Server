package com.sarny.spocone.server.chat;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author Wojciech Makiela
 */
public class ChatControllerTest {

    @Test
    public void testGetChatMessages_whenChatIsCreated_returnMessageAboutJoining(){
        // arrange
        ChatController chatController = new ChatController(new ChatService());
        int gameId = 1;
        int playerId = 20;
        String language = "en";
        String expectedMessage = "[SERVER]: Player 20 joined!";

        // act
        String responseAsString = chatController.getChatMessages(playerId, gameId, language).toString();

        // assert
        assertTrue(responseAsString.contains(expectedMessage));
    }
}