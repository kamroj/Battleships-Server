package com.sarny.spocone.server.game;

import com.sarny.spocone.server.game.computer_players.AI;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Class responsible for providing new {@link GamePlayerVSComputer} objects.
 *
 * @author Wojciech Makiela
 */
class GameVsComputerInitializer extends GameInitializer {

    private AI ai;
    private Board aiBoard;

    GameVsComputerInitializer(int firstPlayerID, int aiId, AI ai) {
        super(firstPlayerID, aiId);
        aiBoard = new Board();
        this.ai = ai;
        ShipPlacementValidator.Builder spvBuilder = new ShipPlacementValidator.Builder();
        ShipPlacementValidator defaultBoardValidator = spvBuilder
                .withGuaranteedMissGenerator(new ShipNeighbouringFieldsGenerator())
                .forBoard(aiBoard);
        new ShipPlacementRandomly(defaultBoardValidator).placeAllShipsRandomly();
    }

    @Override
    public boolean areBothPlayersDone() {
        return boardInitializers.get(FIRST_PLAYER_ID).isBoardReady();
    }

    @Override
    public Game generateGame() throws InvalidBoardCreationException {
        Map<Integer, Board> boardsInGame = new LinkedHashMap<>();

        Board playersBoard = boardInitializers.get(FIRST_PLAYER_ID).generateBoard();
        boardsInGame.put(FIRST_PLAYER_ID, playersBoard);
        boardsInGame.put(SECOND_PLAYER_ID, aiBoard);

        ActiveBoards activeBoards = new ActiveBoards(boardsInGame);
        return new GamePlayerVSComputer(activeBoards, FIRST_PLAYER_ID, ai);
    }
}
