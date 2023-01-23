package trivia;

public interface IGame {
    /**
     * Add a player to the game.
     *
     * @param playerName the name of the new player
     */
    void addPlayer(String playerName);

    /**
     * Roll the dice, and ask a question.
     *
     * @param roll the value of the rolled die
     */
    void roll(int roll);

    /**
     * Execute actions for a right answer.
     *
     * @return whether the game should continue
     */
    boolean rightAnswer();

    /**
     * Execute actions for a wrong answer.
     *
     * @return whether the game should continue
     */
    boolean wrongAnswer();

}