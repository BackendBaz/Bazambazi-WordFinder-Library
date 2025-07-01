package io.github.backendbaz.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record WordDto(String word, long point) {

    @JsonCreator
    public WordDto(@JsonProperty("word") String word,
                @JsonProperty("point") long point) {
        this.word = word;
        this.point = point;
    }

}
