package com.sarny.spocone.server.game;

import com.sarny.spocone.publicclasses.shot.Shot;
import com.sarny.spocone.publicclasses.shot.ShotOutcome;
import com.sarny.spocone.publicclasses.shot.ShotResult;
import com.sarny.spocone.server.game.computer_players.AI;
import com.sarny.spocone.server.game.support_class.PlayerDisconnectedException;

import java.util.Set;

/**
 * @author Kamil Rojek
 */
class GamePlayerVSComputer extends Game {
    private AI computer;

    GamePlayerVSComputer(ActiveBoards activeBoards, int firstPlayerID, AI computer) {
        super(activeBoards, firstPlayerID, computer.getID());
        this.computer = computer;
    }

    @Override
    public ShotResult handleShot(Shot shot) {
        ShotResult shotResult = super.handleShot(shot);

        if (shotResult.getShotOutcome() == ShotOutcome.MISS) {
            ShotResult aiShot;
            do {
                shot = computer.generateShot();
                aiShot = activeRound.handleShot(shot);
            } while (aiShot.getShotOutcome() != ShotOutcome.MISS);
        }

        rounds.add(activeRound);
        createNewRound();

        return shotResult;
    }

    @Override
    public Set<Integer> getGuaranteedMisses(int playerID) {
        Ship ship = getLastSunkShip(playerID);
        return new ShipNeighbouringFieldsGenerator().generateNeighbours(ship);
    }

    /**
     * Informs about whether or not the player with the given ID can shot.
     *
     * @param playerID who needs to be checked
     * @return current active players ID is equal to provided playerID
     */
    public boolean isPlayerRound(int playerID) {
        return activeRound.isPlayerRound(playerID);
    }

    private Ship getLastSunkShip(int playerID) {
        int oppositePlayerID = activeRound.oppositePlayerId(playerID);
        Integer fieldNumber = rounds.peek().getLastSunkField(playerID);
        return activeBoards.getShipOnField(fieldNumber, oppositePlayerID);
    }
}
