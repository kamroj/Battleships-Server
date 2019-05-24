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
 * Groups {@link Chat} objects in a {@link Map}, where key is corresponding {@link com.sarny.spocone.server.game.Game} id.
 * Provides set of methods that allow adding new {@link Message} objects.
 *
 * @author Wojciech Makiela
 */
@Component
public class ChatService {

    private final MessageFactory defaultMessageFactory;
    private Map<Integer, Chat> chatRooms;

    ChatService() {
        this.chatRooms = new HashMap<>();
        defaultMessageFactory = new DefaultMessageFactory();
    }

    /**
     * Add Server message about player loosing connection
     * in {@link Chat} corresponding to game with provided id.
     * @param gameId corresponding game id.
     * @param disconnectedPlayerId message provides information about player who disconnected
     */
    public void playerDisconnected(Integer gameId, int disconnectedPlayerId) {
        Message message = defaultMessageFactory.playerDisconnected(disconnectedPlayerId);
        putNewServerMessage(message, gameId);
    }

    /**
     * Add Server message about player {@link ShotResult}
     * in {@link Chat} corresponding to game with provided id.
     * @param playerId player who took a shot.
     * @param result shot outcome.
     * @param gameId corresponding game id.
     */
    public void addPlayerShotMessage(int playerId, ShotResult result, int gameId) {
        Message message = defaultMessageFactory.playerShot(playerId, result);
        putNewServerMessage(message, gameId);
    }

    /**
     * Add Server message about end of players turn
     * in {@link Chat} corresponding to game with provided id.
     * @param playerId player who finished his turn.
     * @param gameId corresponding game id.
     */
    public void addTurnEndedMessage(int playerId, int gameId) {
        Message message = defaultMessageFactory.playersTurnEnded(playerId);
        putNewServerMessage(message, gameId);
    }

    /**
     * Add Server message about end of a game
     * in {@link Chat} corresponding to game with provided id.
     * @param gameId game that has ended.
     */
    public void addGameEndedMessage(int gameId) {
        Message message = defaultMessageFactory.gameEnded();
        putNewServerMessage(message, gameId);
    }

    /**
     * Get chat with given id. Extract its content in provided language
     * @param playerId if player asks for first time, add message about him joining chat room
     * @param gameId corresponding game id.
     * @param language in which player runs application.
     * @return translated chat content as list of strings.
     */
    List<String> getChatMessagesAsStrings(int playerId, int gameId, String language) {
        Chat chat = getChatOrCreateIfNotOpenedYet(gameId);
        addInfoPlayerJoinsRoomIfRequired(playerId, chat);
        return chat.asListOfStrings(language);
    }

    /**
     * Add players message to chat room.
     * @param chatMessage message details
     * @return translated chat content as list of strings.
     */
    List<String> addUserMessageAndGetChat(ChatMessage chatMessage) {
        Message message = defaultMessageFactory
                .textMessage(chatMessage.playerId, chatMessage.textMessage);
        return putNewUserMessage(message, chatMessage.gameId, chatMessage.language);
    }

    private Message playerJoined(int playerId) {
        return defaultMessageFactory.playerJoined(playerId);
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

    private List<String> putNewUserMessage(Message message, int gameId, String language) {
        Chat chat = chatRooms.get(gameId);
        if (chat == null) {
            throw new IllegalArgumentException("Put message in nonexistent chat room");
        }
        chat.addNewMessage(message);
        return chat.asListOfStrings(language);
    }

    private void putNewServerMessage(Message message, int gameId) {
        Chat chat = chatRooms.get(gameId);
        if (chat == null) {
            throw new IllegalArgumentException("Put message in nonexistent chat room");
        }
        chat.addNewMessage(message);
    }

}
