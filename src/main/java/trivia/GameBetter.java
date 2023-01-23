package trivia;

import java.text.MessageFormat;
import java.util.*;

public class GameBetter implements IGame {
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

        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());
    }

    public void roll(int roll) {
        Player player = players.get(currentPlayerIndex);

        System.out.println(player.getName() + " is the current player");
        System.out.println("They have rolled a " + roll);

        if (player.isInPenaltyBox()) {
            if (roll % 2 != 0) {
                System.out.println(player.getName() + " is getting out of the penalty box");
                player.setInPenaltyBox(false);
            } else {
                System.out.println(player.getName() + " is not getting out of the penalty box");
                return;
            }
        }


        player.setLocation((player.getLocation() + roll) % 12);
        System.out.println(player.getName() + "'s new location is " + player.getLocation());

        Category category = getCategory(player.getLocation());
        System.out.println("The category is " + category);
        System.out.println(questionDecks.get(category).pop());
    }

    public boolean rightAnswer() {
        Player player = players.get(currentPlayerIndex);
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();

        if (player.isInPenaltyBox()) {
            return true;
        } else {

            System.out.println("Answer was correct!!!!");
            player.addToPurse(1);
            System.out.println(player.getName() + " now has " + player.getPurse() + " Gold Coins.");

            return !hasPlayerWon(player);
        }
    }

    public boolean wrongAnswer() {
        Player player = players.get(currentPlayerIndex);

        System.out.println("Question was incorrectly answered");
        System.out.println(player.getName() + " was sent to the penalty box");
        player.setInPenaltyBox(true);

        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        return true;
    }

    /** Get the category corresponding to a location. */
    private Category getCategory(int location) {
        Category[] categories = Category.values();
        return categories[location % categories.length];
    }

    /** Check if a player has met the win condition. */
    private boolean hasPlayerWon(Player player) {
        return player.getPurse() >= 6;
    }
}
