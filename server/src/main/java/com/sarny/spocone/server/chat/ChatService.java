package com.sarny.spocone.server.chat;

import com.sarny.spocone.publicclasses.chat.ChatMessage;
import com.sarny.spocone.publicclasses.shot.ShotResult;
import com.sarny.spocone.server.chat.message.DefaultMessageFactory;
import com.sarny.spocone.server.chat.message.Message;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Wojciech Makiela
 */
@Component
public class ChatService {

    private final DefaultMessageFactory defaultMessageFactory;
    private Map<Integer, Chat> chatRooms; // Key is corresponding Game id;

    public ChatService() {
        this.chatRooms = new HashMap<>();
        defaultMessageFactory = new DefaultMessageFactory();
    }

    List<String> getChatMessagesAsStrings(int playerId, int roomId, String language) {
        Chat chat = getChatOrCreateIfNotOpenedYet(roomId);
        addInfoPlayerJoinsRoomIfRequired(playerId, chat);
        return chat.asListOfStrings(language);
    }

    private void addInfoPlayerJoinsRoomIfRequired(int playerId, Chat chat) {
        if (!chat.hasUser(playerId)) {
            Message message = playerJoined(playerId);
            chat.addNewMessage(message);
            chat.addUser(playerId);
        }
    }

    private Chat getChatOrCreateIfNotOpenedYet(int roomId) {
        Chat chat = chatRooms.get(roomId);
        if (chat == null) {
            chat = new Chat();
            chatRooms.put(roomId, chat);
        }
        return chat;
    }

    List<String> putNewUserMessage(Message message, int gameId, String language) {
        Chat chat = chatRooms.get(gameId);
        if (chat == null) {
            throw new IllegalArgumentException("Put message in nonexistent chat room");
        }
        chat.addNewMessage(message);
        return chat.asListOfStrings(language);
    }

    void putNewServerMessage(Message message, int gameId) {
        Chat chat = chatRooms.get(gameId);
        if (chat == null) {
            throw new IllegalArgumentException("Put message in nonexistent chat room");
        }
        chat.addNewMessage(message);
    }


    public void addPlayerShotMessage(int playerId, ShotResult result, int gameId) {
        Message message = defaultMessageFactory.playerShot(playerId, result);
        putNewServerMessage(message, gameId);
    }

    public void addTurnEndedMessage(int playerId, int gameId) {
        Message message = defaultMessageFactory.playersTurnEnded(playerId);
        putNewServerMessage(message, gameId);
    }

    public List<String> addUserMessageAndGetChat(ChatMessage chatMessage) {
        Message message = defaultMessageFactory.textMessage(chatMessage.playerId, chatMessage.textMessage);
        return putNewUserMessage(message, chatMessage.gameId, chatMessage.language);
    }

    private Message playerJoined(int playerId) {
        return defaultMessageFactory.playerJoined(playerId);
    }

    public void addGameEndedMessage(int gameId) {
        Message message = defaultMessageFactory.gameEnded();
        putNewServerMessage(message, gameId);
    }
}
