package model;

import java.util.ArrayList;

public class FlashCard {
    private String englishWord;
    private String translation;
    private String partOfSpeech;
    private ArrayList<String> pastGuesses;

    // REQUIRES: partOfSpeech must be one of: "noun", "pronoun", "verb", "adjective", "adverb", "article",
    //           "conjunction", "preposition"
    // EFFECTS: Creates a flashcard with given English word, translation, and part of speech
    public FlashCard(String englishWord, String translation, String partOfSpeech) {
        this.englishWord = englishWord;
        this.translation = translation;
        this.partOfSpeech = partOfSpeech;
        this.pastGuesses = new ArrayList<>();
    }

    // EFFECTS: Returns the card's englishWord (English word)
    public String getEnglishWord() {
        return englishWord;
    }

    // EFFECTS: Returns the card's translation (word in the other language)
    public String getTranslation() {
        return translation;
    }

    // EFFECTS: Returns the card's partOfSpeech (part of speech)
    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    // EFFECTS: Returns the card's pastGuesses (all past guesses with no duplicates)
    public ArrayList<String> getPastGuesses() {
        return pastGuesses;
    }

    // MODIFIES: this
    //EFFECTS: add the given string to pastGuesses with NO duplicates in order of addition, returns true if the string
    //         added to pastGuesses, false otherwise.
    public boolean addToPastGuesses(String guess) {
        if (pastGuesses.contains(guess)) {
            return false;
        } else {
            pastGuesses.add(guess);
            return true;
        }
    }

    public void setEnglishWord(String word) {
        this.englishWord = word;
    }

    public void setTranslation(String word) {
        this.translation = word;
    }

    public void setPartOfSpeech(String newPartOfSpeech) {
        this.partOfSpeech = newPartOfSpeech;
    }
}
