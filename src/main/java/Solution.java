import board.Board;
import player.HumanPlayer;
import player.Player;
import board.SquareBoard;
import game.BattleShipGame;
import strategy.RandomCoordinateFireStrategy;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Solution {

    public static void main(String[] args) {
        List<Player> players = new LinkedList<>(Arrays.asList(
                new HumanPlayer("A", new RandomCoordinateFireStrategy()),
                new HumanPlayer("B", new RandomCoordinateFireStrategy())));
        Board board = new SquareBoard();
        BattleShipGame game = new BattleShipGame(board, players);

        game.initGame(10);
        game.addShip("SH-1", 4, 2, 2, 7, 7);
        game.viewBattleField();
        game.startGame();
    }
}
