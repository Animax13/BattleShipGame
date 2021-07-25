package player;

import board.Board;
import strategy.FireStrategy;

public abstract class Player {

    private final String playerID;
    private final FireStrategy fireStrategy;

    public Player(String playerID, FireStrategy fireStrategy) {
        this.playerID = playerID;
        this.fireStrategy = fireStrategy;
    }

    public void fire (Board board, int startColumn, int endColumn, int oppositionPlayerIndex) {
        fireStrategy.fire(board, startColumn, endColumn, oppositionPlayerIndex);
    }

    public String getPlayerID() {
        return playerID;
    }
}
