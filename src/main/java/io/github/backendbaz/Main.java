package io.github.backendbaz;

import io.github.backendbaz.core.Dictionary;
import io.github.backendbaz.core.Finder;
import io.github.backendbaz.core.Word;
import io.github.backendbaz.exceptions.InvalidLettersException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args) throws InterruptedException,
            ExecutionException {
        System.out.println("process -> Loading dictionary ...");
        CompletableFuture<Dictionary> dictionaryFuture = CompletableFuture.supplyAsync(() -> {
            try {
                return new Dictionary().load("dictionary/words.json");
            } catch (Exception e) {
                throw new RuntimeException("File Error -> Failed to load dictionary");
            }
        });
        Dictionary dictionary = dictionaryFuture.get();
        System.out.println("\nOK -> Dictionary loaded successfully!");
        runApplication(dictionary);
    }

    public static void runApplication(Dictionary dictionary) {
        System.out.println("*".repeat(50));
        System.out.println("Welcome to Bazambazi Letter Mash cheating!");
        Scanner scan = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("Enter your Persian letters separated by a " +
                        "space or insert 'e' to exit:");
                String letters = scan.nextLine().trim();
                if (letters.equalsIgnoreCase("e")) {
                    System.out.println("\nBye!");
                    break;
                }
                Finder finder = new Finder(letters);
                List<Word> results = finder.findTopWords(dictionary, 3);
                if (results.isEmpty()) {
                    System.out.println("No words found!");
                    continue;
                }
                results.forEach(word ->
                        System.out.println("Word: " + word.getWord() +
                                " - Point: " + word.getPoint()));
            } catch (InvalidLettersException e) {
                System.out.println("Letters Error -> " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error -> " + e.getMessage());
                // e.printStackTrace();
            } finally {
                System.out.println("=".repeat(50));
            }
        }
        scan.close();
    }

}
