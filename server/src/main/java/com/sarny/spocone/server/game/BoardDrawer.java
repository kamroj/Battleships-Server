package com.sarny.spocone.server.game;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kamil Rojek
 */
class BoardDrawer {
    private static final String SHIP = " S ";
    private static final String SUNK_SHIP = " X ";
    private static final String EMPTY_FIELD = " 0 ";
    private static final String VERTICAL_SEPARATOR = " | ";

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
        sb.append(VERTICAL_SEPARATOR);
        for (int i = startField; i < endField; i++) {
            appendFieldAsString(board, sb, i);
        }
        sb.append(VERTICAL_SEPARATOR);
        return sb.toString();
    }

    private void appendFieldAsString(Board board, StringBuilder sb, int i) {
        Ship ship = board.getShipFromField(i);

        if (ship == null)
            sb.append(EMPTY_FIELD);
        else if (board.ships.contains(ship))
            sb.append(SHIP);
        else if (board.sunkShips.contains(ship))
            sb.append(SUNK_SHIP);
    }
}
