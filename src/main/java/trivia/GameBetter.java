package trivia;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GameBetter implements IGame {
    List<Player> players = new ArrayList<>();

    LinkedList<String> popQuestions = new LinkedList<>();
    LinkedList<String> scienceQuestions = new LinkedList<>();
    LinkedList<String> sportsQuestions = new LinkedList<>();
    LinkedList<String> rockQuestions = new LinkedList<>();

    int currentPlayerIndex = 0;

    public GameBetter() {
        for (int i = 0; i < 50; i++) {
            popQuestions.add("Pop Question " + i);
            scienceQuestions.add(("Science Question " + i));
            sportsQuestions.add(("Sports Question " + i));
            rockQuestions.add("Rock Question " + i);
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
        String category = getCategory(player.getLocation());
        System.out.println("The category is " + category);
        askQuestion(category);
    }

    private void askQuestion(String category) {
        switch (category) {
            case "Pop" -> System.out.println(popQuestions.removeFirst());
            case "Science" -> System.out.println(scienceQuestions.removeFirst());
            case "Sports" -> System.out.println(sportsQuestions.removeFirst());
            case "Rock" -> System.out.println(rockQuestions.removeFirst());
            default -> throw new IllegalArgumentException("Unexpected category: " + category);
        }
    }


    private String getCategory(int location) {
        return switch (location % 4) {
            case 0 -> "Pop";
            case 1 -> "Science";
            case 2 -> "Sports";
            case 3 -> "Rock";
            default -> throw new IllegalStateException("Unexpected value: " + location % 4); // should be impossible
        };
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

    private boolean hasPlayerWon(Player player) {
        return player.getPurse() >= 6;
    }
}
