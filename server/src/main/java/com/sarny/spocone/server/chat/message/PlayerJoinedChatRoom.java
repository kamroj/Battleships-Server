package com.sarny.spocone.server.chat.message;

/**
 * @author Wojciech Makiela
 */
class PlayerJoinedChatRoom extends Message {

    private final String playerId;

    public PlayerJoinedChatRoom(String sentBy, String playerId) {
        super(sentBy, "[%s]: Player %s joined!");
        this.playerId = playerId;
    }

    @Override
    public String asString() {
        return String.format(format, sentBy, playerId);
    }
}
