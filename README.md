**Bazambazi Word Finder Library**

A package used for cheating the **Letter Mash** game in **[Bazambazi](https://bazambazi.games)**

<img src="src/main/resources/images/help.jpg" alt="HELP" width="400">

# Features

- **Java 21**
- **Maven**
- **`DFS` (Depth-First Search) Algorithm and `Trie` letters nodes management**
- **Unit test**

# Download

**[Maven Central Repository](https://central.sonatype.com/artifact/io.github.backendbaz/bazambazi-wordfinder/overview)**

# Usage

```java
import io.github.backendbaz.core.Dictionary;
import io.github.backendbaz.core.Finder;
import io.github.backendbaz.core.Word;
import io.github.backendbaz.exceptions.InvalidLettersException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("process -> Loading dictionary ...");
        CompletableFuture<Dictionary> dictionaryFuture = CompletableFuture.supplyAsync(() -> {
            try {
                return new Dictionary().load();
            } catch (Exception e) {
                throw new RuntimeException("File Error -> Failed to load dictionary");
            }
        });
        try {
            Dictionary dictionary = dictionaryFuture.get();
            System.out.println("\nOK -> Dictionary loaded successfully!");
            runApplication(dictionary);
        } catch (ExecutionException e) {
            System.err.println("Thread Error -> Could not load dictionary!");
        }
    }

    public static void runApplication(Dictionary dictionary) {
        System.out.println("*".repeat(50));
        System.out.println("Welcome to Bazambazi Letter Mash cheating!");
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in, StandardCharsets.UTF_8))) {
        while (true) {
            try {
                System.out.println("Enter your Persian letters separated by a " +
                        "space or insert 'e' to exit:");
                String letters = reader.readLine().trim();
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
                        System.out.println(
                                "Word: " + word.word() +
                                " - Point: " + word.point() +
                                " - Box: " + word.path()));
            } catch (InvalidLettersException e) {
                System.err.println("Letters Error -> " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Error -> " + e.getMessage());
            } finally {
                System.out.println("=".repeat(50));
            }
        }
        } catch (IOException e) {
            System.err.println("Reading Input Error -> " +
                    "App is unable to read the user input");
        }
    }

}
```

# Example

```text
process -> Loading dictionary ...

OK -> Dictionary loaded successfully!
**************************************************
Welcome to Bazambazi Letter Mash Cheat!
Enter your Persian letters separated by a space or insert 'e' to exit:
ا ت ش ن ی ن ا ش ن ع م ا د س ی ف
Word: اتشنشانی - Point: 10
Word: اتشنشان - Point: 9
Word: شایعات - Point: 8
==================================================
Enter your Persian letters separated by a space or insert 'e' to exit:
س ف ب
Letters Error -> Exactly 16 letters required
==================================================
Enter your Persian letters separated by a space or insert 'e' to exit:
س غ ب ی ق ب ل s ا ب غ ع ت ا ل ب
Letters Error -> Invalid Persian letter: s
==================================================
Enter your Persian letters separated by a space or insert 'e' to exit:
e

Bye!
==================================================
```
