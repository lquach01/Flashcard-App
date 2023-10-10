package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CardDeck {
    private ArrayList<FlashCard> allCards;
    private ArrayList<FlashCard> cardsToTest;
    private int numCorrect;
    private int numTested;

    public CardDeck() {
        allCards = new ArrayList<>();
        cardsToTest = new ArrayList<>();
        numTested = 0;
        numCorrect = 0;
    }

    // MODIFIES: this
    // EFFECTS: adds the given card to the list of flashcards in the order of addition.
    public void addCard(FlashCard card) {
        allCards.add(card);
    }

    public List<FlashCard> getAllCards() {
        return allCards;
    }

    public List<FlashCard> getCardsToTest() {
        return cardsToTest;
    }

    public int getNumCorrect() {
        return numCorrect;
    }

    public int getNumTested() {
        return numTested;
    }

    // MODIFIES: this
    // EFFECTS: Returns a list of all the English words of the flashcards all the cards in this deck in the order
    //          they were added
    public ArrayList<String> getEnglishWords() {
        ArrayList<String> englishWords = new ArrayList<>();
        for (FlashCard card: allCards) {
            englishWords.add(card.getEnglishWord());
        }
        return englishWords;
    }

    // MODIFIES: this
    // EFFECTS: Returns a list of all the translated words of the flashcards all the cards in this deck in the order
    //          they were added
    public ArrayList<String> getTranslations() {
        ArrayList<String> translations = new ArrayList<>();
        for (FlashCard card: cardsToTest) {
            translations.add(card.getTranslation());
        }
        return translations;
    }

    // REQUIRES: language is one of: "English" or "Translation", index >= 0
    // MODIFIES: this
    // EFFECTS: Checks if the given guess is equal to the card of given index. If the given language is English, then
    //          checks if it is equal to the translated word. If the given language is "Translation," then checks if it
    //          is equal to the English word.
    //          If the guess is correct, then moves the proper flashcard to correctCards.
    //          Increases the number of cards tested.
    public boolean quiz(String guess, String language, int index) {
        if (language.equals("Translation")) {
            if (guess.equals(getEnglishWords().get(index))) {
                numTested++;
                numCorrect++;
                return true;
            } else {
                numTested++;
                return false;
            }
        } else {
            if (guess.equals(getTranslations().get(index))) {
                numTested++;
                numCorrect++;
                return true;
            } else {
                numTested++;
                return false;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Sets untestedCards to be the cards where the part of speech matches the given part of speech.
    //          If there are no cards in allCards with the same partOfSpeech, returns false. Else, returns true.
    public boolean filterByPartOfSpeech(String partOfSpeech) {
        ArrayList<FlashCard> eligibleCards = new ArrayList<>();
        for (FlashCard card: allCards) {
            if (Objects.equals(card.getPartOfSpeech(), partOfSpeech)) {
                eligibleCards.add(card);
            }
        }
        if (eligibleCards.size() == 0) {
            return false;
        }
        cardsToTest = eligibleCards;
        return true;
    }

    // MODIFIES: this
    // EFFECTS: Resets untestedCards to be allCards and makes correctCards an empty list of FlashCards
    public void resetUntestedCards() {
        cardsToTest = new ArrayList<>();
        for (FlashCard card: allCards) {
            cardsToTest.add(card);
        }
    }


}
