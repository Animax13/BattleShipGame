package strategy;

import board.Board;

import java.util.concurrent.ThreadLocalRandom;

public class RandomCoordinateFireStrategy implements FireStrategy{

    @Override
    public void fire(Board board, int startColumn, int endColumn, int oppositionPlayerIndex) {
        int x,y;
        do {
            x = ThreadLocalRandom.current().nextInt(0, board.getNumberOfColumns());
            y = ThreadLocalRandom.current().nextInt(startColumn, endColumn +1);
        }
        while (!board.isValidCoordinateToFire(x,y));
        board.fireAtCoordinate(x, y, oppositionPlayerIndex);
    }
}
