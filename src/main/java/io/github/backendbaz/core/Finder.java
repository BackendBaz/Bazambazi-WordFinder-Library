package io.github.backendbaz.core;

import io.github.backendbaz.exceptions.InvalidLettersException;

import java.util.Arrays;

public class Finder {

    private int rows = 4;
    private int cols = 4;
    private final String input;

    public Finder(String input) {
        this.input = input;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public String[][] getLetters() throws InvalidLettersException {
        if (input == null || input.isBlank())
            throw new InvalidLettersException("Input is null or blank");
        String[] letters = input.split(" ");
        if (letters.length != rows * cols || !Arrays.stream(letters)
                .allMatch(letter -> letter.matches("[ا-ی]")))
            throw new InvalidLettersException("Some letters are not valid");
        return new String[][]{};
    }

}
