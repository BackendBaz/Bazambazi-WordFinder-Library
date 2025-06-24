package io.github.backendbaz.core;

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

}
