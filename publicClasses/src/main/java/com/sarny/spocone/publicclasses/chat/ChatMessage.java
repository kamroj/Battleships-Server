package com.sarny.spocone.publicclasses.chat;

/**
 * Container for player message.
 * playerId - who sent message.
 * gameId - chat rooms are created for Game objects, thus they sare id.
 * textMessage - content of message itself.
 * language - code of language in which Server communicates should be displayed
 *
 * @author Wojciech Makiela
 */
public class ChatMessage {

    public int playerId;
    public int gameId;
    public String textMessage;
    public String language;
}
