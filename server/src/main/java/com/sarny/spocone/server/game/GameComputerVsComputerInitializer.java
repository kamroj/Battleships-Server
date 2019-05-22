package com.sarny.spocone.server.game;

import com.sarny.spocone.server.game.computer_players.AI;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Kamil Rojek
 */
class GameComputerVsComputerInitializer extends GameInitializer {
    private AI firstComputer;
    private AI secondComputer;
    private Board firstComputerBoard = new Board();
    private Board secondComputerBoard = new Board();

    GameComputerVsComputerInitializer(AI firstComputer, AI secondComputer) {
        super(firstComputer.getID(), secondComputer.getID());
        this.firstComputer = firstComputer;
        this.secondComputer = secondComputer;

        generateBoardForComputer(firstComputerBoard);
        generateBoardForComputer(secondComputerBoard);
    }

    public GameComputerVsComputer generateGame() {
        Map<Integer, Board> boardsInGame = new LinkedHashMap<>();

        boardsInGame.put(firstComputer.getID(), firstComputerBoard);
        boardsInGame.put(secondComputer.getID(), secondComputerBoard);
        System.out.println("Board AI 1: ");
        firstComputerBoard.toString();
        System.out.println("Board AI 2: ");
        secondComputerBoard.toString();

        ActiveBoards activeBoards = new ActiveBoards(boardsInGame);
        return new GameComputerVsComputer(activeBoards, firstComputer, secondComputer);
    }

    private void generateBoardForComputer(Board board) {
        ShipPlacementValidator.Builder spvBuilder = new ShipPlacementValidator.Builder();
        ShipPlacementValidator defaultBoardValidator = spvBuilder
                .withGuaranteedMissGenerator(new ShipNeighbouringFieldsGenerator())
                .forBoard(board);
        new ShipPlacementRandomly(defaultBoardValidator).placeAllShipsRandomly();
    }
}

/*
0123456789
   s    ss
     s
     s s
s    s s
       s
       s
  sss    s
       s s
    ss
s
 */