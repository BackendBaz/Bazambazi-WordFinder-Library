package io.github.backendbaz.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record Word(String word, long point) {

    @JsonCreator
    public Word(@JsonProperty("word") String word,
                @JsonProperty("point") long point) {
        this.word = word;
        this.point = point;
    }

}
