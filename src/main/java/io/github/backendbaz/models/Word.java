package io.github.backendbaz.models;

import io.github.backendbaz.core.Point;
import java.util.List;

public record Word(String word, long point, List<Point> path) {}
