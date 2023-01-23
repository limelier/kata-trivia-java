package trivia.game;

import lombok.Data;

@Data
public class Player {
    private String name;
    private int purse;
    private int location;
    private boolean inPenaltyBox;

    public Player(String name) {
        this.name = name;
    }

    public void addToPurse(int coins) {
        purse += coins;
    }
}