package com.sarny.spocone.server.chat.message;

/**
 * @author Wojciech Makiela
 */
class PlayerJoinedChatRoom extends Message {

    private final String playerId;

    public PlayerJoinedChatRoom(String sentBy, String playerId) {
        super(sentBy, "MESSAGE_JOIN");
        this.playerId = playerId;
    }

    @Override
    public String asString(String language) {
        return String.format(formatForLanguage(language), sentBy, playerId);
    }
}
