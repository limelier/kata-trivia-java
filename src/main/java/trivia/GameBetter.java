package trivia;

import java.text.MessageFormat;
import java.util.*;

public class GameBetter implements IGame {
    private static final int BOARD_LENGTH = 12;
    private static final int PURSE_WIN_THRESHOLD = 6;
    
    List<Player> players = new ArrayList<>();
    Map<Category, Deque<String>> questionDecks = new EnumMap<>(Category.class);
    int currentPlayerIndex = 0;

    public GameBetter() {
        // initialize with fake sample questions
        for (var category : Category.values()) {
            Deque<String> questions = new LinkedList<>();

            String pattern = category + " Question {0}";
            for (var i = 0; i < 50; i++) {
                questions.add(MessageFormat.format(pattern, i));
            }

            questionDecks.put(category, questions);
        }
    }

    public void addPlayer(String playerName) {
        players.add(new Player(playerName));

        println(playerName + " was added");
        println("They are player number " + players.size());
    }

    public void roll(int roll) {
        Player player = players.get(currentPlayerIndex);

        println(player.getName() + " is the current player");
        println("They have rolled a " + roll);

        if (player.isInPenaltyBox()) {
            if (roll % 2 != 0) { // escape penalty box on odd rolls
                println(player.getName() + " is getting out of the penalty box");
                player.setInPenaltyBox(false);
            } else {
                println(player.getName() + " is not getting out of the penalty box");
                return;
            }
        }


        player.setLocation((player.getLocation() + roll) % BOARD_LENGTH);
        println(player.getName() + "'s new location is " + player.getLocation());

        Category category = getCategory(player.getLocation());
        println("The category is " + category);
        println(questionDecks.get(category).pop());
    }

    public boolean rightAnswer() {
        Player player = players.get(currentPlayerIndex);
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();

        if (player.isInPenaltyBox()) {
            return true;
        } else {

            println("Answer was correct!!!!");
            player.addToPurse(1);
            println(player.getName() + " now has " + player.getPurse() + " Gold Coins.");

            return player.getPurse() <= PURSE_WIN_THRESHOLD;
        }
    }

    public boolean wrongAnswer() {
        Player player = players.get(currentPlayerIndex);

        println("Question was incorrectly answered");
        println(player.getName() + " was sent to the penalty box");
        player.setInPenaltyBox(true);

        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        return true;
    }

    /** Print a line. */
    private void println(String x) {
        System.out.println(x);
    }
    
    /** Get the category corresponding to a location. */
    private Category getCategory(int location) {
        Category[] categories = Category.values();
        return categories[location % categories.length];
    }
}
