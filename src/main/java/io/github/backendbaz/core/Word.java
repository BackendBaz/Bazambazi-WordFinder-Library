package io.github.backendbaz.core;

import java.util.Objects;

public class Word {

    private String word;
    private long point;

    public Word(String word, long point) {
        this.word = word;
        this.point = point;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public long getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word wrd = (Word) o;
        return point == wrd.point && Objects.equals(word, wrd.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, point);
    }

}
