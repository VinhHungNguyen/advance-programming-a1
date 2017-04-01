package com.hung;

import java.util.Scanner;

/**
 * Created by hungnguyen on 4/1/17.
 */
public class Ozlympic {

    public static void main(String[] args) {
        Ozlympic ozlympic = new Ozlympic();
        ozlympic.start();
    }

    /**
     * Start the Ozlympic game
     */
    private void start() {
        Scanner scanner = new Scanner(System.in);
        int selection = 0;

        do {
            printMenu();

            // Handle when the input selection is not an integer.
            if (!scanner.hasNextInt()) {
                alertInvalidSelection();
                scanner.next();
                continue;
            }

            selection = scanner.nextInt();

            // If the selection is not within 1 to 6
            if (selection < 1 || selection > 6) {
                alertInvalidSelection();
                continue;
            }

            if (selection == 1) {
                selectGameToRun();
            } else if (selection == 2) {
                predictWinner();
            } else if (selection == 3) {
                startGame();
            } else if (selection == 4) {
                displayResults();
            } else if (selection == 5) {
                displayAthletePoints();
            } else if (selection == 6) {
                break;
            }

            System.out.println();
        } while (true);
    }

    /**
     * Select a game to run
     */
    private void selectGameToRun() {

    }

    /**
     * Allow the user to predict the winner of that game
     */
    private void predictWinner() {

    }

    /**
     * Start the game
     */
    private void startGame() {

    }

    /**
     * Display the final results of all games
     */
    private void displayResults() {

    }

    /**
     * Display the points of all athletes
     */
    private void displayAthletePoints() {

    }

    /**
     * Print the main menu
     */
    private void printMenu() {
        System.out.print("Ozlympic Game\n" +
                "===================================\n" +
                "1. Select a game to run\n" +
                "2. Predict the winner of the game\n" +
                "3. Start the game\n" +
                "4. Display the final results of all games\n" +
                "5. Display the points of all athletes\n" +
                "6. Exit\n" +
                "Enter an option: ");
    }

    /**
     * Print alert message when the selection is invalid
     */
    private void alertInvalidSelection() {
        System.out.println("The selection must be an integer within 1 to 6.");
    }
}
