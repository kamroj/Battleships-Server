package com.sarny.spocone.server.chat;

import com.sarny.spocone.publicclasses.chat.ChatMessage;
import com.sarny.spocone.publicclasses.shot.ShotResult;
import com.sarny.spocone.server.chat.message.DefaultMessageFactory;
import com.sarny.spocone.server.chat.message.Message;
import com.sarny.spocone.server.chat.message.MessageFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Wojciech Makiela
 */
@Component
public class ChatService implements MessageFactory {

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

    List<String> putNewMessage(ChatMessage messageFromUser) {
        Message message = textMessage(messageFromUser.playerId, messageFromUser.textMessage);
        Chat chat = chatRooms.get(messageFromUser.gameId);
        if (chat == null) {
            throw new IllegalArgumentException("Put message in nonexistent chat room");
        }
        chat.addNewMessage(message);
        return chat.asListOfStrings(messageFromUser.language);
    }

    @Override
    public Message playerShot(int playerId, ShotResult result) {
        return defaultMessageFactory.playerShot(playerId, result);
    }

    @Override
    public Message playersTurnEnded(int playerId) {
        return defaultMessageFactory.playersTurnEnded(playerId);
    }

    @Override
    public Message textMessage(int playerId, String textMessageContent) {
        return defaultMessageFactory.textMessage(playerId, textMessageContent);
    }

    @Override
    public Message playerJoined(int playerId) {
        return defaultMessageFactory.playerJoined(playerId);
    }
}
