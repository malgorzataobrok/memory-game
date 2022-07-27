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
            printHeloScreen();
            Scanner scanner = new Scanner(System.in);
            String level;
            while (true) {
                level = scanner.nextLine();
                if (level.equalsIgnoreCase(EASY) || level.equalsIgnoreCase(HARD)) {
                    level = level.toLowerCase();
                    break;
                } else {
                    printHeloScreen();
                    System.out.println("Please input easy or hard");
                }
            }

            List<String> randomWords = getRandomWords(availableWords, level);
            List<Integer> visibleWordNumbers = new ArrayList<>();
            int userChances = getGuessChances(level);

            long start = System.currentTimeMillis();
            while (userChances > 0 && visibleWordNumbers.size() != randomWords.size()) {
                showMatrixScreen(level, randomWords, visibleWordNumbers, userChances);
                int firstWordNumber = getUserCoordinates(scanner, level, visibleWordNumbers);
                visibleWordNumbers.add(firstWordNumber);
                String firstWord = randomWords.get(firstWordNumber);
                showMatrixScreen(level, randomWords, visibleWordNumbers, userChances);
                int secondWordNumber = getUserCoordinates(scanner, level, visibleWordNumbers);
                visibleWordNumbers.add(secondWordNumber);
                String secondWord = randomWords.get(secondWordNumber);
                showMatrixScreen(level, randomWords, visibleWordNumbers, userChances);
                if (firstWord.equals(secondWord)) {
                    System.out.println("Matched!");
                } else {
                    System.out.println("Wrong!");
                    visibleWordNumbers.remove(Integer.valueOf(firstWordNumber));
                    visibleWordNumbers.remove(Integer.valueOf(secondWordNumber));
                    userChances--;
                }

                System.out.println("Press enter");
                scanner.nextLine();
                showMatrixScreen(level, randomWords, visibleWordNumbers, userChances);
            }
            long finish = System.currentTimeMillis();
            long timeElapsedInSeconds = (finish - start) / 1000;

            if (visibleWordNumbers.size() == randomWords.size()) {
                System.out.println("Congratulations you have won! You had " + userChances + " chances left and it took you " + timeElapsedInSeconds + " seconds");
            }
            System.out.println("Do you want to try again? Input Y or N");

            while (true) {
                yesNo = scanner.nextLine();
                if (yesNo.equalsIgnoreCase(YES) || yesNo.equalsIgnoreCase(NO)) {
                    yesNo = yesNo.toLowerCase();
                    break;
                } else {
                    System.out.println("Please input Y or N");
                }
            }
        }

    }

    private static int convertUserCoordinateToWordNumber(String userCoordinates) {
        int secondNumber = Integer.parseInt(String.valueOf(userCoordinates.charAt(1)));
        return convertCharToNumber(userCoordinates.charAt(0)) + secondNumber - 1;
    }

    private static int convertCharToNumber(char value) {
        switch (value) {
            case 'A':
                return 0;
            case 'B':
                return ROW_WORD_COUNT;
            case 'C':
                return ROW_WORD_COUNT * 2;
            case 'D':
                return ROW_WORD_COUNT * 3;
        }
        return 0;
    }

    private static int getUserCoordinates(Scanner scanner, String level, List<Integer> visibleWordNumbers) {
        System.out.println("Please input coordinates example: A1");
        String coordinates;
        List<String> availableCoordinatesValues = generateAvailableCoordinatesValues(level);
        while (true) {
            coordinates = scanner.nextLine();
            if (availableCoordinatesValues.contains(coordinates)) {
                int userCoordinateNumber = convertUserCoordinateToWordNumber(coordinates);
                if (visibleWordNumbers.contains(userCoordinateNumber)) {
                    System.out.println("Coordinate " + coordinates + " is already uncovered, please input another one");
                } else {
                    return userCoordinateNumber;
                }

            } else {
                System.out.println("Please input correct coordinates example: A1");
            }
        }
    }

    private static List<String> generateAvailableCoordinatesValues(String level) {
        List<String> availableCoordinatesValues = new ArrayList<>();
        availableCoordinatesValues.addAll(generateAvailableCoordinatesValuesForChar("A"));
        availableCoordinatesValues.addAll(generateAvailableCoordinatesValuesForChar("B"));
        if (level.equals(HARD)) {
            availableCoordinatesValues.addAll(generateAvailableCoordinatesValuesForChar("C"));
            availableCoordinatesValues.addAll(generateAvailableCoordinatesValuesForChar("D"));
        }
        return availableCoordinatesValues;
    }

    private static Collection<String> generateAvailableCoordinatesValuesForChar(String value) {
        List<String> availableCoordinatesValues = new ArrayList<>();
        for (int i = 0; i < ROW_WORD_COUNT; i++) {
            availableCoordinatesValues.add(value + (i + 1));
        }
        return availableCoordinatesValues;
    }


    private static List<String> getRandomWords(List<String> availableWords, String level) {
        List<String> userWords = new ArrayList<>();

        if (level.equals(EASY)) {
            userWords = getRandomWordsBasedOnWordCount(availableWords, EASY_WORD_COUNT);
        }
        if (level.equals(HARD)) {
            userWords = getRandomWordsBasedOnWordCount(availableWords, HARD_WORD_COUNT);
        }
        userWords.addAll(new ArrayList<>(userWords));
        Collections.shuffle(userWords);
        return userWords;
    }

    private static List<String> getRandomWordsBasedOnWordCount(List<String> availableWords, int wordCount) {
        List<String> userWords = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < wordCount; i++) {
            while (true) {
                String newWord = getRandomWord(availableWords, random);
                if (!userWords.contains(newWord)) {
                    userWords.add(newWord);
                    break;
                }
            }
        }
        return userWords;
    }

    private static String getRandomWord(List<String> availableWords, Random random) {
        return availableWords.get(random.nextInt(availableWords.size()));
    }

    private static void showMatrixScreen(String level, List<String> randomWords, List<Integer> visibleWordNumbers, int userChances) {
        clearConsole();

        printLine();
        System.out.println("Level: " + level);
        System.out.println("Guess chances: " + userChances);
        System.out.println();

        showMatrix(level, randomWords, visibleWordNumbers);
        printLine();

    }

    private static void showMatrix(String level, List<String> randomWords, List<Integer> visibleWordNumbers) {
        if (level.equals(EASY)) {
            System.out.println("  1 2 3 4");
            System.out.print("A ");
            printMatrix(randomWords, visibleWordNumbers, 0);
            System.out.print("B ");
            printMatrix(randomWords, visibleWordNumbers, 1);
        }
        if (level.equals(HARD)) {
            System.out.println("  1 2 3 4");
            System.out.print("A ");
            printMatrix(randomWords, visibleWordNumbers, 0);
            System.out.print("B ");
            printMatrix(randomWords, visibleWordNumbers, 1);
            System.out.print("C ");
            printMatrix(randomWords, visibleWordNumbers, 2);
            System.out.print("D ");
            printMatrix(randomWords, visibleWordNumbers, 3);
        }
    }

    private static void printMatrix(List<String> randomWords, List<Integer> visibleWordNumbers, int row) {
        for (int i = 0; i < ROW_WORD_COUNT; i++) {
            int wordNumber = i + (row * ROW_WORD_COUNT);
            if (visibleWordNumbers.contains(wordNumber)) {
                System.out.print(randomWords.get(wordNumber) + " ");
            } else {
                System.out.print("X ");
            }
        }
        System.out.println();
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

    private static void printHeloScreen() {
        clearConsole();
        printLine();
        System.out.println("Select difficulty level:");
        System.out.println("EASY = " + EASY_WORD_COUNT + " words, " + EASY_CHANCES + " chances");
        System.out.println("HARD = " + HARD_WORD_COUNT + " words, " + HARD_CHANCES + " chances");
        printLine();
        System.out.println();
    }

    private static void printLine() {
        System.out.println("—-----------------------------------");
    }


    private static void clearConsole() {
        //Just few new lines to have clear console and this also works in IDE. It looks nicer
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
}
