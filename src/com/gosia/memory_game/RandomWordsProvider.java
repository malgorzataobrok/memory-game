package com.gosia.memory_game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomWordsProvider {

    static List<String> getRandomWords(List<String> availableWords, String level) {
        List<String> userWords = new ArrayList<>();

        if (level.equals(MemoryGame.EASY)) {
            userWords = getRandomWordsBasedOnWordCount(availableWords, MemoryGame.EASY_WORD_COUNT);
        }
        if (level.equals(MemoryGame.HARD)) {
            userWords = getRandomWordsBasedOnWordCount(availableWords, MemoryGame.HARD_WORD_COUNT);
        }
        userWords.addAll(new ArrayList<>(userWords));
        Collections.shuffle(userWords);
        return userWords;
    }

    static List<String> getRandomWordsBasedOnWordCount(List<String> availableWords, int wordCount) {
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

    static String getRandomWord(List<String> availableWords, Random random) {
        return availableWords.get(random.nextInt(availableWords.size()));
    }
}
