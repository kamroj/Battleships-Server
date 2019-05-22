package com.sarny.spocone.server.chat.message;

/**
 * @author Wojciech Makiela
 */
public abstract class Message {

    String sentBy;
    String format;

    public Message(String sentBy, String format) {
        this.sentBy = sentBy;
        this.format = format;
    }

    public abstract String asString();
}
