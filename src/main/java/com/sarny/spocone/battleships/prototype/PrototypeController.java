package com.sarny.spocone.battleships.prototype;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author Makiela Wojciech
 */
@RestController
class PrototypeController {

    @Autowired
    private Board board;

    @PostMapping(path = "/place")
    private List<Integer> placeNewShipOnBoard(@RequestBody ShipPlacementData shipPlacementData) {
        board.placeShip(shipPlacementData);
        System.out.println(board);
        return board.getFieldsWhereShipIsPlaced();
    }

    @GetMapping(path = "/place")
    private void soutShipPlacementJSON() {
        System.out.println(new Gson().toJson(new ShipPlacementData(4, true, 0)));
    }

    @PostMapping(path = "/shot")
    private String handlePlayerShot(Shot shot) {
        String result = board.markShot(shot.fieldNumber);
        System.out.println(board);
        soutShipPlacementJSON();
        return result;
    }

    private class Shot {
        int fieldNumber;
        int playerId;

        public Shot(int fieldNumber, int playerId) {
            this.fieldNumber = fieldNumber;
            this.playerId = playerId;
        }
    }
}
