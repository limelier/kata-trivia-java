package trivia;

import trivia.game.Game;

import java.util.Random;
import java.util.Scanner;

public class GamePlayer {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();

    public static void main(String[] args) {
        System.out.println("*** Welcome to Trivia Game ***\n");

        int playerCount;
        do {
            System.out.println("Enter number of players: 2-6");
            playerCount = Integer.parseInt(scanner.nextLine());
        } while (playerCount < 2 || playerCount > 6);

        System.out.println("Reading names for " + playerCount + " players:");

        Game game = new Game();

        for (int i = 1; i <= playerCount; i++) {
            System.out.print("Player " + i + " name: ");
            String playerName = scanner.nextLine();
            game.addPlayer(playerName);
        }

        System.out.println("\n\n--Starting game--");


        boolean gameContinues = true;
        while (gameContinues) {
            int roll = readRoll();
            game.roll(roll);

            System.out.print(">> Was the answer correct? [y/n] ");
            boolean correct = readYesNo();
            if (correct) {
                gameContinues = game.rightAnswer();
            } else {
                gameContinues = game.wrongAnswer();
            }

        }
        System.out.println(">> Game won!");
    }

    private static boolean readYesNo() {
        String yn = scanner.nextLine().trim().toUpperCase();
        if (!yn.matches("[YN]")) {
            System.err.println("y or n please");
            return readYesNo();
        }
        return yn.equalsIgnoreCase("Y");
    }

    private static int readRoll() {
        System.out.print(">> Throw a die and input roll, or [ENTER] to generate a random roll: ");
        String rollStr = scanner.nextLine().trim();
        if (rollStr.isEmpty()) {
            int roll = random.nextInt(6) + 1;
            System.out.println(">> Random roll: " + roll);
            return roll;
        }
        if (!rollStr.matches("\\d+")) {
            System.err.println("Not a number: '" + rollStr + "'");
            return readRoll();
        }
        int roll = Integer.parseInt(rollStr);
        if (roll < 1 || roll > 6) {
            System.err.println("Invalid roll");
            return readRoll();
        }
        return roll;
    }
}
