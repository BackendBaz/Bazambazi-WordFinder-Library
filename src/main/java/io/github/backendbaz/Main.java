package io.github.backendbaz;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
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
            } catch (Exception e) {
                System.out.println("Something went wrong! Try again.");
            } finally {
                System.out.println("=".repeat(50));
            }
        }
    }

}
