package com.sarny.spocone.server.chat.message;

/**
 * @author Wojciech Makiela
 */
class PlayerDisconnectedMessage extends Message {

    private final int disconnectedPlayerId;

    PlayerDisconnectedMessage(String sentBy, int disconnectedPlayerId) {
        super(sentBy, "DISCONNECT");
        this.disconnectedPlayerId = disconnectedPlayerId;
    }

    @Override
    public String asString(String language) {
        return String.format(formatForLanguage(language), sentBy, disconnectedPlayerId);
    }
}
