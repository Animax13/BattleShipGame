package strategy;

import board.Board;

public interface FireStrategy {

    void fire(Board board, int startColumn, int endColumn, int oppositionPlayerIndex);
}
