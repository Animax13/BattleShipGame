package player;

import player.Player;
import strategy.FireStrategy;

public class HumanPlayer extends Player {

    private String name;
    private int numberOfWins;
    private int numberOfGames;


    public HumanPlayer(String playerID, FireStrategy fireStrategy) {
        super(playerID, fireStrategy);
    }
}
