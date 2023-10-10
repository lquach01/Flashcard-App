package model;

public class FlashCard {
    private String englishWord;
    private String translation;
    private String partOfSpeech;

    // REQUIRES: partOfSpeech must be one of: "noun", "pronoun", "verb", "adjective", "adverb", "article",
    //           "conjunction", "preposition"
    // MODIFIES: this
    // EFFECTS: Creates a flashcard with given English word, translation, and part of speech
    public FlashCard(String englishWord, String translation, String partOfSpeech) {
        this.englishWord = englishWord;
        this.translation = translation;
        this.partOfSpeech = partOfSpeech;
    }

    public String getEnglishWord() {
        return englishWord;
    }

    public String getTranslation() {
        return translation;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
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
