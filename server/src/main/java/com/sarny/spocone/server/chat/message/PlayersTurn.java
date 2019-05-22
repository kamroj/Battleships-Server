package com.sarny.spocone.server.chat.message;

/**
 * @author Wojciech Makiela
 */
class PlayersTurn extends Message {

    private final int playerId;

    public PlayersTurn(String sentBy, int playerId) {
        super(sentBy, "MESSAGE_TURN");
        this.playerId = playerId;
    }

    @Override
    public String asString(String language) {
        return String.format(formatForLanguage(language), sentBy, playerId);
    }
}
