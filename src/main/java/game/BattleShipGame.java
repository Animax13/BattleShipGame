package game;

import board.Board;
import player.Player;
import dto.Ship;
import exception.ApplicationException;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static constant.GameConstant.*;

public class BattleShipGame {

    private final Board board;
    private final List<Player> players;
    private final Map<String, Integer> playerIndexMap;

    public BattleShipGame(Board board, List<Player> players) {
        this.board = board;
        this.players = players;
        playerIndexMap = new HashMap<>();
        int index = 0;
        for (Player player : players) {
            playerIndexMap.put(player.getPlayerID(), index++);
        }
    }

    public void initGame(int boardDimension) {
        try {
            board.initialize(Collections.singletonList(boardDimension), players);
        } catch (ApplicationException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addShip(String id, int size, int xPositionPlayerA, int yPositionPlayerA, int xPositionPlayerB, int yPositionPlayerB) {
        try {
            Ship ship = new Ship(id, size);
            board.assignShip(ship, xPositionPlayerA, yPositionPlayerA, 0);
            board.assignShip(ship, xPositionPlayerB, yPositionPlayerB, 1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void startGame () {
        while (!players.isEmpty()) {
            Player player = players.get(0);
            players.remove(player);
            if (players.isEmpty())
                System.out.printf((PLAYER_WON_MESSAGE) + "%n", player.getPlayerID());
            else if (!board.isAnyShipLeftForPlayer(playerIndexMap.get(player.getPlayerID())))
                System.out.printf((PLAYER_LOST_MESSAGE) + "%n", player.getPlayerID());
            else {
                System.out.printf(PLAYER_TURN, player.getPlayerID());
                int oppositionPlayerIndex = playerIndexMap.get(player.getPlayerID()) == 0 ? 1 : 0;
                int startColumnForOpponent = playerIndexMap.get(player.getPlayerID()) == 0 ? board.getNumberOfColumns()/2 : 0;
                int endColumnForOpponent = startColumnForOpponent + board.getNumberOfColumns()/2 - 1;
                player.fire(board, startColumnForOpponent, endColumnForOpponent, oppositionPlayerIndex);
                players.add(player);
            }
        }
    }

    public void viewBattleField() {
        board.printBoard();
    }
}
