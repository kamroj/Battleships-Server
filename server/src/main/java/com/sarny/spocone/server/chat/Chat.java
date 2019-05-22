package com.sarny.spocone.server.chat;

import com.sarny.spocone.server.chat.message.Message;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Wojciech Makiela
 */
class Chat {

    private static final int MAX_NUMBER_OF_MESSAGES = 10;
    private Deque<Message> messages = new LinkedList<>();
    private List<Integer> usersInChatRoom = new ArrayList<>();

    void addNewMessage(Message message) {
        messages.add(message);
        if (messages.size() >= MAX_NUMBER_OF_MESSAGES) {
            messages.removeFirst();
        }
    }

    List<String> asListOfStrings() {
        List<String> asList = new ArrayList<>();
        for (Message message : messages) {
            asList.add(message.asString());
        }
        return asList;
    }

    public boolean hasUser(int playerId) {
        return usersInChatRoom.contains(playerId);
    }

    public void addUser(int playerId) {
        usersInChatRoom.add(playerId);
    }
}
