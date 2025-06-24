package io.github.backendbaz;

import io.github.backendbaz.core.Finder;
import io.github.backendbaz.exceptions.InvalidLettersException;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        runApplication();
    }

    public static void runApplication() {
        System.out.println("Welcome to Bazambazi Word Finder CLI!");
        while (true) {
            try {
                System.out.println("Enter your Persian letters separated by a space " +
                        "or insert 'e' to exit the program:");
                Scanner scan = new Scanner(System.in);
                String letters = scan.nextLine();
                if (letters.equalsIgnoreCase("e")) {
                    System.out.println("\nBye!");
                    break;
                }
                Finder finder = new Finder(letters);
                String[][] gridOfLetters = finder.getLetters();
            } catch (InvalidLettersException e) {
                System.out.println("Invalid letters Error -> " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Something went wrong! Try again.");
            } finally {
                System.out.println("=".repeat(50));
            }
        }
    }

}
