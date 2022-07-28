package com.gosia.memory_game;

import java.util.List;
import java.util.Scanner;

public class UserInputs {

    static String getYesOrNoFromUser(Scanner scanner) {
        System.out.println("Do you want to try again? Input Y or N");
        String yesNo;
        while (true) {
            yesNo = scanner.nextLine();
            if (yesNo.equalsIgnoreCase(MemoryGame.YES) || yesNo.equalsIgnoreCase(MemoryGame.NO)) {
                yesNo = yesNo.toLowerCase();
                break;
            } else {
                System.out.println("Please input Y or N");
            }
        }
        return yesNo;
    }

    static String getLevelFromUser(Scanner scanner) {
        String level;
        while (true) {
            level = scanner.nextLine();
            if (level.equalsIgnoreCase(MemoryGame.EASY) || level.equalsIgnoreCase(MemoryGame.HARD)) {
                level = level.toLowerCase();
                break;
            } else {
                Printer.printHelloScreen();
                System.out.println("Please input easy or hard");
            }
        }
        return level;
    }

    static int getUserCoordinates(Scanner scanner, List<Integer> visibleWordNumbers, List<String> availableCoordinatesValues) {
        System.out.println("Please input coordinates example: A1");
        String coordinates;
        while (true) {
            coordinates = scanner.nextLine();
            if (availableCoordinatesValues.contains(coordinates)) {
                int userCoordinateNumber = Converter.convertUserCoordinateToWordNumber(coordinates);
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
}
