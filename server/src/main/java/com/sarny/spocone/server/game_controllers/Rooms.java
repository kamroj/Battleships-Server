package com.sarny.spocone.server.game_controllers;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * @author Wojciech Makiela
 */
@Component
class Rooms {

    private final Object lock = new Object();
    private int nextRoomId = 0;
    private Map<Integer, Room> rooms = new HashMap<>();

    Optional<Room> register(int playerId) {
        int roomId;
        synchronized (lock) {
            roomId = nextRoomId++;
        }
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

    Set<Integer> available() {
        return rooms.keySet();
    }

    Integer getRoomId() {
        synchronized (lock) {
            return nextRoomId;
        }
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
