package com.sarny.spocone.server.chat.message;

import com.sarny.spocone.publicclasses.shot.ShotResult;

/**
 * @author Wojciech Makiela
 */
public interface MessageFactory {
    Message playerShot(int playerId, ShotResult result);

    Message playersTurn(int playerId);

    Message textMessage(int playerId, String textMessageContent);

    Message playerJoined(int playerId);
}
