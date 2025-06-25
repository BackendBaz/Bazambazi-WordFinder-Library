package io.github.backendbaz.core;

public class Word {

    private String word;
    private int point;

    public Word(String word, int point) {
        this.word = word;
        this.point = point;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

}
