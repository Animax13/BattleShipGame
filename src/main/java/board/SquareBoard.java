package board;

import dto.Cell;
import dto.Ship;
import exception.ApplicationException;
import player.Player;

import java.util.List;

import static constant.ExceptionMessageConstant.*;
import static constant.GameConstant.*;

public class SquareBoard implements Board {

    private Cell[][] board;
    private int dimension;
    private boolean isInitialized;
    private int numberOfPlayers;

    //PUBLIC METHODS
    @Override
    public void initialize(List<Integer> dimensions, List<Player> players) {
        if (isInitialized)
            throw new ApplicationException(BOARD_ALREADY_INITIALIZED_MESSAGE);
        int dimension = dimensions.get(0);
        if (isValidConfiguration(dimension, players.size())) {
            board = new Cell[dimension][dimension];
            this.dimension = dimension;
            divideAndAssignBoard(dimension, players);
            isInitialized = true;
            numberOfPlayers = players.size();
        }
        else
            throw new ApplicationException(INVALID_BOARD_CONFIGURATION_MESSAGE);
    }

    @Override
    public void assignShip (Ship ship, int xPosition, int yPosition, int playerIndex) {
        if (!isInitialized)
            throw new ApplicationException(BOARD_NOT_INITIALIZED_MESSAGE);
        if (isValidCoordinateForPlayer(xPosition, yPosition, playerIndex)) {
            int startX = xPosition - ship.getSize()/2;
            int endX = xPosition + ship.getSize()/2;
            int startY = yPosition - ship.getSize()/2;
            int endY = yPosition + ship.getSize()/2;
            for (int  i = startX; i< endX; i++)
                for (int j = startY; j < endY; j++)
                    checkAndAssignShip(ship, playerIndex, i, j);
        } else
            throw new ApplicationException(INVALID_PLAYER_COORDINATE_MESSAGE);
    }

    @Override
    public void printBoard () {
        for (int i = 0; i< dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                Cell cell = board[i][j];
                if (cell.isActive() && cell.getShip() != null)
                    System.out.print(cell.getPlayer().getPlayerID() + UNDERSCORE + cell.getShip().getId() + SPACE);
                else
                    System.out.print(NO_SHIP_PLACEHOLDER);
            }
            System.out.println();
        }
    }

    @Override
    public boolean isAnyShipLeftForPlayer (int playerIndex) {
        boolean isAnyShipLeft = false;
        int numberOfColumnsForEachPlayer = dimension/numberOfPlayers;
        int yStartIndex = playerIndex * numberOfColumnsForEachPlayer;
        int yEndIndex = yStartIndex + numberOfColumnsForEachPlayer - 1;
        for (int i = 0; i< dimension; i++) {
            for (int  j = yStartIndex; j<= yEndIndex; j++) {
                Cell cell = board[i][j];
                if (cell.isActive() && cell.getShip()!=null) {
                    isAnyShipLeft = true;
                    break;
                }
            }
            if (isAnyShipLeft)
                break;
        }
        return isAnyShipLeft;
    }

    @Override
    public boolean isValidCoordinateToFire (int x, int y) {
        Cell cell = board[x][y];
        return cell.isActive();
    }

    @Override
    public void fireAtCoordinate (int x, int y, int oppositionPlayerIndex) {
        Cell cell = board[x][y];
        Ship ship = cell.getShip();
        if (ship == null)
            System.out.println(FIRE_AT_EMPTY_PLACE);
        else
            destroyShip(x, y, ship, oppositionPlayerIndex);
    }

    @Override
    public int getNumberOfColumns() {
        return dimension;
    }

    // PRIVATE METHODS
    private boolean isValidConfiguration (int dimension, int numberOfPlayers) {
        return dimension % numberOfPlayers == 0;
    }

    private void divideAndAssignBoard(int dimension, List<Player> players) {
        int playerColumnSize = dimension / players.size();
        for (int playerIndex = 0 ;playerIndex < players.size(); playerIndex++) {
            int startColumn = (playerIndex) * playerColumnSize;
            int endColumn = startColumn + playerColumnSize;

            for (int rowIndex=0; rowIndex<dimension; rowIndex++) {
                for (int columnIndex = startColumn; columnIndex < endColumn; columnIndex++)
                    board[rowIndex][columnIndex] = new Cell(players.get(playerIndex));
            }
        }
    }

    private boolean isValidCoordinateForPlayer (int xPosition, int yPosition, int playerIndex) {
        int numberOfColumnsForEachPlayer = dimension/numberOfPlayers;
        int yStartIndex = playerIndex * numberOfColumnsForEachPlayer;
        int yEndIndex = yStartIndex + numberOfColumnsForEachPlayer - 1;
        return (xPosition >=0) && (xPosition < dimension) && (yStartIndex <= yPosition) && (yPosition<= yEndIndex) ;
    }

    private boolean isCoordinateWithinBoardRange(int x, int y) {
        return (x >=0 && x < dimension) && (y>=0 && y< dimension);
    }

    private void checkAndAssignShip(Ship ship, int playerIndex, int xPosition, int yPosition) {
        if (isValidCoordinateForPlayer(xPosition, yPosition, playerIndex)) {
            Cell cell = board[xPosition][yPosition];
            if (cell.getShip() != null) {
                throw new ApplicationException(COLLISION_WITH_OTHER_SHIP_MESSAGE);
            }
            cell.setShip(ship);
        } else
            throw new ApplicationException(SHIP_POSITION_OUTSIDE_VALID_RANGE_MESSAGE);
    }

    private void destroyShip(int x, int y, Ship ship, int oppositionPlayerIndex) {
        System.out.printf((SHIP_DESTROY_MESSAGE) + "%n", ship.getId());
        int numberOfColumnsForEachPlayer = dimension/numberOfPlayers;

        int startX = Math.min(x - ship.getSize(), 0);
        if (startX < 0)
            startX = 0;

        int endX = Math.max(dimension-1, x + ship.getSize());
        if (endX >= dimension)
            endX = dimension - 1;

        int yStartIndex = oppositionPlayerIndex * numberOfColumnsForEachPlayer;
        int yEndIndex = yStartIndex + numberOfColumnsForEachPlayer - 1;

        int startY = Math.min(y- ship.getSize(), yStartIndex);
        if (startY < 0)
            startY = 0;

        int endY = Math.max( y+ ship.getSize(), yEndIndex);
        if (endY >= dimension)
            endY = dimension-1;

        for (int  i = startX; i<= endX; i++) {
            for (int j = startY; j <= endY; j++) {
                if (isCoordinateWithinBoardRange(i, j)) {
                    Cell currentCell = board[i][j];
                    if (currentCell.getShip() != null && currentCell.getShip().getId().equalsIgnoreCase(ship.getId()))
                        currentCell.setActive(false);
                }
            }
        }
    }
}
