package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// FlashCard is a class that represents a flashcard with an English word, a translation, a part of speech, and a list of
// past guesses.
public class FlashCard implements Writable {
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

    // REQUIRES: partOfSpeech must be one of: "noun", "pronoun", "verb", "adjective", "adverb", "article",
    //           "conjunction", "preposition"
    // EFFECTS: Creates a flashcard with given English word, translation, part of speech, and ArrayList of pastGuesses
    public FlashCard(String englishWord, String translation, String partOfSpeech, ArrayList<String> pastGuesses) {
        this.englishWord = englishWord;
        this.translation = translation;
        this.partOfSpeech = partOfSpeech;
        this.pastGuesses = pastGuesses;
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

    // MODIFIES: this
    // EFFECTS: Changes the flashCard's englishWord to be word
    public void setEnglishWord(String word) {
        this.englishWord = word;
    }

    // MODIFIES: this
    // EFFECTS: Changes the flashCard's translation to be word
    public void setTranslation(String word) {
        this.translation = word;
    }

    // MODIFIES: this
    // EFFECTS: Changes the flashCard's part of speech to be newPartOfSpeech
    public void setPartOfSpeech(String newPartOfSpeech) {
        this.partOfSpeech = newPartOfSpeech;
    }

    @Override
    // EFFECTS: returns things in this flashCard as a JSONObject
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        JSONArray pastGuessesJson = new JSONArray();

        for (String pastGuess: pastGuesses) {
            pastGuessesJson.put(pastGuess);
        }

        json.put("englishWord", englishWord);
        json.put("translation", translation);
        json.put("partOfSpeech", partOfSpeech);
        json.put("pastGuesses", pastGuessesJson);
        return json;
    }

}
