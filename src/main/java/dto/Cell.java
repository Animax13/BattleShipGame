package dto;

import player.Player;

public class Cell {

    private final Player player;
    private Ship ship;
    private boolean isActive = true;

    public Cell(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
