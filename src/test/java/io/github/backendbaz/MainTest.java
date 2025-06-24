package io.github.backendbaz;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class MainTest {

    @Test
    @Tag("Main:runApplication")
    @DisplayName("Exits the app when 'e' is entered")
    public void runApplication_WhenTypeEToExitTheApp_ShouldReturnsGoodbyeMessage() {
        // save main streams:
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        try {
            // simulate the user input:
            String simulatedInput = "e";
            ByteArrayInputStream testIn =
                    new ByteArrayInputStream(simulatedInput.getBytes());
            System.setIn(testIn);
            // get the output:
            ByteArrayOutputStream testOut = new ByteArrayOutputStream();
            System.setOut(new PrintStream(testOut));
            // run the app:
            Main.runApplication();
            // check the output:
            String output = testOut.toString();
            assertThat(output).contains("\nBye!");
        } finally {
            // reset streams:
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
    }
}