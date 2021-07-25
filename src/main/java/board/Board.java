package board;

import dto.Ship;
import player.Player;

import java.util.List;

public interface Board {

    void initialize(List<Integer> dimensions, List<Player> players);

    void assignShip(Ship ship, int xPosition, int yPosition, int playerIndex);

    void printBoard();

    boolean isAnyShipLeftForPlayer(int playerIndex);

    boolean isValidCoordinateToFire(int x, int y);

    void fireAtCoordinate(int x, int y, int oppositionPlayerIndex);

    int getNumberOfColumns();
}
