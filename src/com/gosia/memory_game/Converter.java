package com.gosia.memory_game;

public class Converter {

    static int convertUserCoordinateToWordNumber(String userCoordinates) {
        int secondNumber = Integer.parseInt(String.valueOf(userCoordinates.charAt(1)));
        return convertCharToNumber(userCoordinates.charAt(0)) + secondNumber - 1;
    }

    static int convertCharToNumber(char value) {
        switch (value) {
            case 'A':
                return 0;
            case 'B':
                return MemoryGame.ROW_WORD_COUNT;
            case 'C':
                return MemoryGame.ROW_WORD_COUNT * 2;
            case 'D':
                return MemoryGame.ROW_WORD_COUNT * 3;
        }
        return 0;
    }
}
