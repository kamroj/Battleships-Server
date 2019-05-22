package com.sarny.spocone.server.chat.message;

/**
 * @author Wojciech Makiela
 */
class TextMessage extends Message {

    private final String messageContent;

    TextMessage(String sentBy, String messageContent) {
        super(sentBy, "MESSAGE_TEXT_MESSAGE");
        this.messageContent = messageContent;
    }

    @Override
    public String asString(String language) {
        return String.format(formatForLanguage(language), sentBy, messageContent);
    }

}
