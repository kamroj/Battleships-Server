package com.sarny.spocone.battleships.prototype;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Makiela Wojciech
 */
@RestController
class PrototypeController {

    @Autowired
    private Board board;

    @PostMapping(path = "/place")
    private void placeNewShipOnBoard(@RequestBody ShipPlacementData shipPlacementData) {
        board.placeShip(shipPlacementData);
        System.out.println(board);
    }

    @GetMapping(path = "/place")
    private void soutShipPlacementJSON() {
        System.out.println(new Gson().toJson(new ShipPlacementData(4, true, 0)));
    }

    @PostMapping(path = "/shot")
    private String handlePlayerShot(@RequestBody int fieldNumber) {
        String result = board.markShot(fieldNumber);
        System.out.println(board);
        return result;
    }
}
