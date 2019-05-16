package com.sarny.spocone.server.gameControllers;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Wojciech Makiela
 */
@Component
class Rooms {

    private int nextRoomId = 0;
    private Map<Integer, Room> rooms = new HashMap<>();

    Optional<Room> register(int playerId) {
        int roomId = nextRoomId++;
        Room newRoom = new Room(roomId);
        newRoom.players.addPlayer(playerId);
        rooms.put(roomId, newRoom);
        return Optional.of(newRoom);
    }

    Optional<Room> register(int playerId, int roomToJoin) {
        Room room = rooms.get(roomToJoin);
        if (room == null) {
            return Optional.empty();
        }
        boolean isComplete = room.players.addPlayer(playerId);
        if (isComplete) {
            rooms.remove(roomToJoin);
            return Optional.of(room);
        }
        return Optional.empty();
    }

    class Room {
        int id;
        PairOfPlayers players;

        Room(int id) {
            this.id = id;
            players = new PairOfPlayers();
        }
    }

    class PairOfPlayers {
        Integer firstPlayerId;
        Integer secondPlayerId;

        boolean addPlayer(int id) {
            if (firstPlayerId == null) {
                firstPlayerId = id;
                return false;
            }
            secondPlayerId = id;
            return true;
        }
    }
}
