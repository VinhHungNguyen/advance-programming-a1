package com.hung.utils;

import java.util.Scanner;

/**
 * Created by hungnguyen on 4/2/17.
 */
public class Utils {


    public static void runMenuFlow(String menuText, String invalidSelectionMsg, OnMenuOptionSelectedListener... listeners) {
        Scanner scanner = new Scanner(System.in);
        int selection = 0;

        do {
            System.out.print(menuText);

            // Handle when the input selection is not an integer.
            if (!scanner.hasNextInt()) {
                System.out.println(invalidSelectionMsg);
                scanner.next();
                continue;
            }

            selection = scanner.nextInt();

            if (listeners == null || listeners.length == 0) {
                System.out.println();
                return;
            }

            // If the selection is not within 1 to 4
            if (selection < 1 || selection > listeners.length + 1) {
                System.out.println(invalidSelectionMsg);
                continue;
            }

            // Exit the menu when the last option is selected
            if (selection == listeners.length + 1) {
                System.out.println();
                return;
            }

            System.out.println();

            selection--; // Make the selection becomes index-based
            if (listeners[selection].onOptionSelected()) {
                System.out.println();
                return;
            }

        } while (true);
    }

    public interface OnMenuOptionSelectedListener {

        /**
         * Perform a task when an option in a menu is selected
         * @return True if it also exits the menu after selecting.
         */
        boolean onOptionSelected();
    }
}
