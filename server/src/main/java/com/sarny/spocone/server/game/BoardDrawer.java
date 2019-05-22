package com.sarny.spocone.server.game;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kamil Rojek
 */
class BoardDrawer {
    private ActiveBoards activeBoards;

    BoardDrawer(ActiveBoards activeBoards) {
        this.activeBoards = activeBoards;
    }

    String draw() {
        return iterateBoards();
    }

    private String iterateBoards() {
        List<Integer> playersId = new ArrayList<>(activeBoards.getBoards().keySet());
        Board firstPlayerBoard = activeBoards.getBoards().get(playersId.get(0));
        Board secondPlayerBoard = activeBoards.getBoards().get(playersId.get(1));
        return iterateRows(firstPlayerBoard, secondPlayerBoard);
    }

    private String iterateRows(Board firstPlayerBoard, Board secondPlayerBoard) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 10; i++) {
            sb.append("\n");
            sb.append(iterateColumns(firstPlayerBoard, i)).append(iterateColumns(secondPlayerBoard, i));
            sb.append("\n");
        }
        return sb.toString();
    }

    private String iterateColumns(Board board, int column) {
        StringBuilder sb = new StringBuilder();
        int startField = column == 1 ? 0 : (column - 1) * 10;
        int endField = 10 * column;

        sb.append(" | ");
        for (int i = startField; i < endField; i++) {
            Ship ship = board.getShipFromField(i);

            if (ship == null)
                sb.append(" 0 ");
            else if (board.ships.contains(ship))
                sb.append(" S ");
            else if (board.sunkShips.contains(ship))
                sb.append(" X ");
        }
        sb.append(" | ");
        return sb.toString();
    }
}
