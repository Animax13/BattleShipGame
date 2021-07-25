package player;

import player.Player;
import strategy.FireStrategy;

public class ComputerPlayer extends Player
{
    public ComputerPlayer(String playerID, FireStrategy fireStrategy) {
        super(playerID, fireStrategy);
    }
}
