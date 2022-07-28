package com.gosia.memory_game;

public class GameStats {

    int userChanceLeft;
    long timeOfTheGame;
    boolean win;

    public GameStats(int userChanceLeft, long timeOfTheGame, boolean win) {
        this.userChanceLeft = userChanceLeft;
        this.timeOfTheGame = timeOfTheGame;
        this.win = win;
    }

    public int getUserChanceLeft() {
        return userChanceLeft;
    }

    public boolean isWin() {
        return win;
    }

    public long getTimeOfTheGame() {
        return timeOfTheGame;
    }
}
