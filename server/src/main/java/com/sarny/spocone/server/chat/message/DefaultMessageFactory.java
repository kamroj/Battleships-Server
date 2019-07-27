package com.sarny.spocone.server.chat.message;

import com.sarny.spocone.publicclasses.shot.ShotResult;

/**
 * @author Wojciech Makiela
 */
public class DefaultMessageFactory implements MessageFactory {

    private static final String SERVER = "SERVER";

    @Override
    public Message playerShot(int playerId, ShotResult result) {
        String playerIdAsString = String.valueOf(playerId);
        return new PlayerShot(SERVER, playerIdAsString, result);
    }

    @Override
    public Message playersTurnEnded(int playerId) {
        return new PlayersTurn(SERVER, playerId);
    }

    @Override
    public Message textMessage(int playerId, String textMessageContent) {
        String playerIdAsString = String.valueOf(playerId);
        return new TextMessage(playerIdAsString, textMessageContent);
    }

    @Override
    public Message playerJoined(int playerId) {
        String playerIdAsString = String.valueOf(playerId);
        return new PlayerJoinedChatRoom(SERVER, playerIdAsString);
    }

    @Override
    public Message gameEnded() {
        return new GameEndedMessage(SERVER);
    }

    @Override
    public Message playerDisconnected(int disconnectedPlayerId) {
        return new PlayerDisconnectedMessage(SERVER, disconnectedPlayerId);

    }
}
