package com.gosia.memory_game;

import java.util.*;

public class MemoryGame {

    public static final String EASY = "easy";
    public static final String HARD = "hard";
    public static final int EASY_CHANCES = 10;
    public static final int HARD_CHANCES = 15;
    public static final int EASY_WORD_COUNT = 4;
    public static final int HARD_WORD_COUNT = 8;
    public static final int ROW_WORD_COUNT = 4;
    public static final String YES = "y";
    public static final String NO = "n";


    public static void main(String[] args) {
        List<String> availableWords = FileLoader.loadFile();

        String yesNo = "";
        while (!yesNo.equals(NO)) {
            Printer.printHelloScreen();

            Scanner scanner = new Scanner(System.in);
            String level = UserInputs.getLevelFromUser(scanner);
            List<String> randomWords = RandomWordsProvider.getRandomWords(availableWords, level);
            int userChances = getGuessChances(level);

            GameStats gameStats = playGame(scanner, level, randomWords, userChances);
            yesNo = afterGame(scanner, gameStats);
        }

    }

    private static int getGuessChances(String level) {
        switch (level) {
            case EASY:
                return EASY_CHANCES;
            case HARD:
                return HARD_CHANCES;
        }
        return 0;
    }

    private static GameStats playGame(Scanner scanner, String level, List<String> randomWords, int userChances) {
        List<Integer> visibleWordNumbers = new ArrayList<>();
        CoordinatesGenerator coordinatesGenerator = new CoordinatesGenerator(level);
        List<String> availableCoordinatesValues = coordinatesGenerator.generateAvailableCoordinatesValues();
        long start = System.currentTimeMillis();

        while (userChances > 0 && visibleWordNumbers.size() != randomWords.size()) {
            Printer.printMatrixScreen(level, randomWords, visibleWordNumbers, userChances);

            int firstWordNumber = UserInputs.getUserCoordinates(scanner, visibleWordNumbers, availableCoordinatesValues);
            visibleWordNumbers.add(firstWordNumber);
            String firstWord = randomWords.get(firstWordNumber);
            Printer.printMatrixScreen(level, randomWords, visibleWordNumbers, userChances);

            int secondWordNumber = UserInputs.getUserCoordinates(scanner, visibleWordNumbers, availableCoordinatesValues);
            visibleWordNumbers.add(secondWordNumber);
            String secondWord = randomWords.get(secondWordNumber);
            Printer.printMatrixScreen(level, randomWords, visibleWordNumbers, userChances);

            userChances = checkMatchedResult(userChances, visibleWordNumbers, firstWordNumber, firstWord, secondWordNumber, secondWord);

            System.out.println("Press enter");
            scanner.nextLine();
            Printer.printMatrixScreen(level, randomWords, visibleWordNumbers, userChances);
        }
        long finish = System.currentTimeMillis();
        long timeElapsedInSeconds = (finish - start) / 1000;
        boolean win = visibleWordNumbers.size() == randomWords.size();
        return new GameStats(userChances, timeElapsedInSeconds, win);
    }

    private static String afterGame(Scanner scanner, GameStats gameStats) {

        Printer.printEndGameText(gameStats);
        return UserInputs.getYesOrNoFromUser(scanner);
    }

    private static int checkMatchedResult(int userChances, List<Integer> visibleWordNumbers, int firstWordNumber, String firstWord, int secondWordNumber, String secondWord) {
        if (firstWord.equals(secondWord)) {
            System.out.println("Matched!");
        } else {
            System.out.println("Wrong!");
            visibleWordNumbers.remove(Integer.valueOf(firstWordNumber));
            visibleWordNumbers.remove(Integer.valueOf(secondWordNumber));
            userChances--;
        }
        return userChances;
    }


}
