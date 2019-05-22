package com.sarny.spocone.server.chat.message;

import com.sarny.spocone.publicclasses.shot.ShotOutcome;
import com.sarny.spocone.publicclasses.shot.ShotResult;

/**
 * @author Wojciech Makiela
 */
class PlayerShot extends Message {

    private final int field;
    private final ShotOutcome shotOutcome;
    private final String playerId;

    public PlayerShot(String sentBy, String playerId, ShotResult result) {
        super(sentBy, "[%s]: Player %s shot field %d : %s");
        field = result.getField();
        shotOutcome = result.getShotOutcome();
        this.playerId = playerId;
    }

    @Override
    public String asString() {
        return String.format(this.format, sentBy, playerId, field, shotOutcome);
    }
}
