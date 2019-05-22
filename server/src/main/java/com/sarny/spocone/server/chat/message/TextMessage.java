package com.sarny.spocone.server.chat.message;

/**
 * @author Wojciech Makiela
 */
class TextMessage extends Message {

    private final String messageContent;

    public TextMessage(String sentBy, String messageContent) {
        super(sentBy, "[%s]: %s");
        this.messageContent = messageContent;
    }

    @Override
    public String asString() {
        return String.format(this.format, sentBy, messageContent);
    }


}
