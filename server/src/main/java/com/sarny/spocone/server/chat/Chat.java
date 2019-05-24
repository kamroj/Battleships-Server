package com.sarny.spocone.server.chat;

import com.sarny.spocone.server.chat.message.Message;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Representation of chat messages.
 * Stores up to {@value MAX_NUMBER_OF_MESSAGES} messages.
 * Since both players might post message in the same time, used collection is thread safe.
 *
 * @author Wojciech Makiela
 */
class Chat {

    private static final int MAX_NUMBER_OF_MESSAGES = 20;
    private Deque<Message> messages = new LinkedBlockingDeque<>();
    private List<Integer> usersInChatRoom = new ArrayList<>();

    /**
     * Persist message, and remove oldest if reached storage limit.
     * @param message to be saved.
     */
    void addNewMessage(Message message) {
        messages.add(message);
        if (messages.size() >= MAX_NUMBER_OF_MESSAGES) {
            messages.removeFirst();
        }
    }

    /**
     * Returns messages as list of strings in provided language.
     *
     * @param language code (ISO 639-1 standard)
     * @return translated messages
     */
    List<String> asListOfStrings(String language) {
        List<String> asList = new ArrayList<>();
        for (Message message : messages) {
            asList.add(message.asString(language));
        }
        return asList;
    }

    boolean hasUser(int playerId) {
        return usersInChatRoom.contains(playerId);
    }

    void addUser(int playerId) {
        usersInChatRoom.add(playerId);
    }
}
