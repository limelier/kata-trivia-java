package trivia;

public interface IGame {

	void add(String playerName);

	void roll(int roll);

	boolean rightAnswer();

	boolean wrongAnswer();

}