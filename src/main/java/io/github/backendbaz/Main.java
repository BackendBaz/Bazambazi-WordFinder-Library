package io.github.backendbaz;

import io.github.backendbaz.core.Dictionary;
import io.github.backendbaz.core.Finder;
import io.github.backendbaz.core.Word;
import io.github.backendbaz.exceptions.InvalidLettersException;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main implements Runnable {

    private static List<Word> words;

    public static void main(String[] args) {
        Main main = new Main();
        Thread thread = new Thread(main);
        thread.start();
        while (thread.isAlive()) {
            System.out.println("loading...");
        }
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
                System.out.println(words.getFirst().getWord()); // todo
            } catch (InvalidLettersException e) {
                System.out.println("Invalid letters Error -> " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Something went wrong! Try again.");
            } finally {
                System.out.println("=".repeat(50));
            }
        }
    }

    @Override
    public void run() {
        try {
            words = new Dictionary()
                    .getWords("./src/main/resources/dictionary/words.json");
        } catch (IOException | ParseException e) {
            System.out.println(e.getMessage());
        }
    }
}
