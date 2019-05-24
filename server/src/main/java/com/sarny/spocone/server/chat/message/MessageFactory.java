package com.sarny.spocone.server.chat.message;

import com.sarny.spocone.publicclasses.shot.ShotResult;

/**
 * @author Wojciech Makiela
 */
public interface MessageFactory {
    Message playerShot(int playerId, ShotResult result);

    Message playersTurnEnded(int playerId);

    Message textMessage(int playerId, String textMessageContent);

    Message playerJoined(int playerId);

    Message gameEnded();

    Message playerDisconnected(int disconnectedPlayerId);
}
