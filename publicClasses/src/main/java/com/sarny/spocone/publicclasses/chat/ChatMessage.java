package com.sarny.spocone.publicclasses.chat;

import java.util.Objects;

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

    public ChatMessage() {
    }

    public ChatMessage(int playerId, int gameId, String textMessage, String language) {
        this.playerId = playerId;
        this.gameId = gameId;
        this.textMessage = textMessage;
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatMessage that = (ChatMessage) o;
        return playerId == that.playerId &&
                gameId == that.gameId &&
                Objects.equals(textMessage, that.textMessage) &&
                Objects.equals(language, that.language);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerId, gameId, textMessage, language);
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "playerId=" + playerId +
                ", gameId=" + gameId +
                ", textMessage='" + textMessage + '\'' +
                ", language='" + language + '\'' +
                '}';
    }
}
