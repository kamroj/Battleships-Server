package com.sarny.spocone.server.chat.message;

/**
 * @author Wojciech Makiela
 */
class GameEndedMessage extends Message {
    public GameEndedMessage(String sentBy) {
        super(sentBy, "GAME_OVER");
    }

    @Override
    public String asString(String language) {
        return String.format(formatForLanguage(language), sentBy);
    }
}
