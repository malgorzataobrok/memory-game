package com.gosia.memory_game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CoordinatesGenerator {
    private String level;

    public CoordinatesGenerator(String level) {
        this.level = level;
    }

    public List<String> generateAvailableCoordinatesValues() {
        List<String> availableCoordinatesValues = new ArrayList<>();
        availableCoordinatesValues.addAll(generateAvailableCoordinatesValuesForChar("A"));
        availableCoordinatesValues.addAll(generateAvailableCoordinatesValuesForChar("B"));
        if (level.equals(MemoryGame.HARD)) {
            availableCoordinatesValues.addAll(generateAvailableCoordinatesValuesForChar("C"));
            availableCoordinatesValues.addAll(generateAvailableCoordinatesValuesForChar("D"));
        }
        return availableCoordinatesValues;
    }

    private Collection<String> generateAvailableCoordinatesValuesForChar(String value) {
        List<String> availableCoordinatesValues = new ArrayList<>();
        for (int i = 0; i < MemoryGame.ROW_WORD_COUNT; i++) {
            availableCoordinatesValues.add(value + (i + 1));
        }
        return availableCoordinatesValues;
    }
}
