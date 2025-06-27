package io.github.backendbaz.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record Word(String word, long point, List<Point> path) {

    @JsonCreator
    public Word(@JsonProperty("word") String word,
                @JsonProperty("point") long point,
                List<Point> path) {
        this.word = word;
        this.point = point;
        this.path = path;
    }

}
