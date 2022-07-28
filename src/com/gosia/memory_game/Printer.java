package com.gosia.memory_game;

import java.util.List;

public class Printer {

    static void printEndGameText(GameStats gameStats) {
        if (gameStats.isWin()) {
            System.out.println("Congratulations you have won! You had " + gameStats.getUserChanceLeft() + " chances left and it took you " + gameStats.getTimeOfTheGame() + " seconds");
        }
    }

    static void printHelloScreen() {
        clearConsole();
        printLine();
        System.out.println("Select difficulty level:");
        System.out.println("EASY = " + MemoryGame.EASY_WORD_COUNT + " words, " + MemoryGame.EASY_CHANCES + " chances");
        System.out.println("HARD = " + MemoryGame.HARD_WORD_COUNT + " words, " + MemoryGame.HARD_CHANCES + " chances");
        printLine();
        System.out.println();
    }

    static void clearConsole() {
        //Just few new lines to have clear console and this also works in IDE. It looks nicer
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    static void printLine() {
        System.out.println("—-----------------------------------");
    }

    static void printMatrixScreen(String level, List<String> randomWords, List<Integer> visibleWordNumbers, int userChances) {
        clearConsole();

        printLine();
        System.out.println("Level: " + level);
        System.out.println("Guess chances: " + userChances);
        System.out.println();

        printMatrix(level, randomWords, visibleWordNumbers);
        printLine();

    }

    static void printMatrix(String level, List<String> randomWords, List<Integer> visibleWordNumbers) {
        if (level.equals(MemoryGame.EASY)) {
            System.out.println("  1 2 3 4");
            System.out.print("A ");
            printWordMatrix(randomWords, visibleWordNumbers, 0);
            System.out.print("B ");
            printWordMatrix(randomWords, visibleWordNumbers, 1);
        }
        if (level.equals(MemoryGame.HARD)) {
            System.out.println("  1 2 3 4");
            System.out.print("A ");
            printWordMatrix(randomWords, visibleWordNumbers, 0);
            System.out.print("B ");
            printWordMatrix(randomWords, visibleWordNumbers, 1);
            System.out.print("C ");
            printWordMatrix(randomWords, visibleWordNumbers, 2);
            System.out.print("D ");
            printWordMatrix(randomWords, visibleWordNumbers, 3);
        }
    }

    static void printWordMatrix(List<String> randomWords, List<Integer> visibleWordNumbers, int row) {
        for (int i = 0; i < MemoryGame.ROW_WORD_COUNT; i++) {
            int wordNumber = i + (row * MemoryGame.ROW_WORD_COUNT);
            if (visibleWordNumbers.contains(wordNumber)) {
                System.out.print(randomWords.get(wordNumber) + " ");
            } else {
                System.out.print("X ");
            }
        }
        System.out.println();
    }
}
