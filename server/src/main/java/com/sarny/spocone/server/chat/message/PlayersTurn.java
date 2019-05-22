package com.sarny.spocone.server.chat.message;

/**
 * @author Wojciech Makiela
 */
class PlayersTurn extends Message {

    private final int playerId;

    public PlayersTurn(String sentBy, int playerId) {
        super(sentBy, "[%s]: Now it's players %d turn");
        this.playerId = playerId;
    }

    @Override
    public String asString() {
        return String.format(this.format, sentBy, playerId);
    }
}
