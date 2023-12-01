package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// CardDeck is a deck of cards that contains all flashcards added to the deck, all flashcards to test, the number of
// cards that were tested and the user got correct, and the number of cards that were tested.
public class CardDeck implements Writable {
    private ArrayList<FlashCard> allCards;
    private ArrayList<FlashCard> cardsToTest;
    private int numCorrect;
    private int numTested;

    // EFFECTS: Creates a new CardDeck with empty ArrayLists for allCards and cardsToTest. Sets numTested and numCorrect
    //          to 0.
    public CardDeck() {
        allCards = new ArrayList<>();
        cardsToTest = new ArrayList<>();
        numTested = 0;
        numCorrect = 0;
    }

    // MODIFIES: this, EventLog
    // EFFECTS: adds the given card to the list of flashcards (allCards) in the order of addition.
    public void addCard(FlashCard card) {
        allCards.add(card);
        EventLog.getInstance().logEvent(new Event("The card for '" + card.getEnglishWord()
                + "' was added to the deck of cards."));
    }

    // EFFECTS: returns all the flashcards that have been added to the deck of flashcards
    public List<FlashCard> getAllCards() {
        EventLog.getInstance().logEvent(new Event("All cards were displayed."));
        return allCards;
    }

    // EFFECTS: returns all the cards in cardsToTest, which stores all the cards that the user will be quizzed on
    public List<FlashCard> getCardsToTest() {
        return cardsToTest;
    }

    // EFFECTS: returns the number of cards that the user has gotten right so far
    public int getNumCorrect() {
        return numCorrect;
    }

    // EFFECTS: returns the number of cards the user has been quizzed on
    public int getNumTested() {
        return numTested;
    }

    // MODIFIES: this, EventLog
    // EFFECTS: Returns a list of all the English words of the flashcards all the cards in this deck in the order
    //          they were added
    public ArrayList<String> getEnglishWords() {
        EventLog.getInstance().logEvent(new Event("Quizzed on English words."));
        ArrayList<String> englishWords = new ArrayList<>();
        for (FlashCard card: cardsToTest) {
            englishWords.add(card.getEnglishWord());
        }
        return englishWords;
    }

    // MODIFIES: this, EventLog
    // EFFECTS: Returns a list of all the translated words of the flashcards all the cards in this deck in the order
    //          they were added
    public ArrayList<String> getTranslations() {
        EventLog.getInstance().logEvent(new Event("Quizzed on translations."));
        ArrayList<String> translations = new ArrayList<>();
        for (FlashCard card: cardsToTest) {
            translations.add(card.getTranslation());
        }
        return translations;
    }

    // REQUIRES: language is one of: "English" or "Translation"
    // MODIFIES: this
    // EFFECTS: Checks if the given guess is equal to the word of given language of fc.
    //          If the given language is English, then checks if it is equal to the translated word.
    //          If the given language is "Translation," then checks if it is equal to the English word.
    //          If the guess is correct, then increases numCorrect (number of correct cards) by 1.
    //          Increases numTested (number of cards tested) by 1.
    public boolean checkIfCorrect(String guess, String language, FlashCard fc) {
        fc.addToPastGuesses(guess);
        if (language.equals("Translation")) {
            if (guess.equals(fc.getEnglishWord())) {
                numTested++;
                numCorrect++;
                return true;
            } else {
                numTested++;
                return false;
            }
        } else {
            if (guess.equals(fc.getTranslation())) {
                numTested++;
                numCorrect++;
                return true;
            } else {
                numTested++;
                return false;
            }
        }
    }

    // MODIFIES: this, EventLog
    // EFFECTS: Sets cardsToTest to be the cards where the part of speech matches the given part of speech.
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
        EventLog.getInstance().logEvent(new Event("Cards were filtered by " + partOfSpeech + "."));
        cardsToTest = eligibleCards;
        return true;

    }

    // MODIFIES: this
    // EFFECTS: Sets cardsToTest to have all the cards in allCards
    public void resetUntestedCards() {
        cardsToTest = new ArrayList<>();
        for (FlashCard card: allCards) {
            cardsToTest.add(card);
        }
    }

    public CardDeck(ArrayList<FlashCard> allCards, ArrayList<FlashCard> cardsToTest, int numCorrect, int numTested) {
        this.allCards = allCards;
        this.cardsToTest = cardsToTest;
        this.numCorrect = numCorrect;
        this.numTested = numTested;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        JSONArray allCardsJson = new JSONArray();

        for (FlashCard fc: allCards) {
            JSONObject jsonFC = fc.toJson();
            allCardsJson.put(jsonFC);
        }

        JSONArray cardsToTestJson = new JSONArray();

        for (FlashCard fc: cardsToTest) {
            JSONObject jsonFC = fc.toJson();
            cardsToTestJson.put(jsonFC);
        }

        json.put("allCards", allCardsJson);
        json.put("cardsToTest", cardsToTestJson);
        json.put("numCorrect", numCorrect);
        json.put("numTested", numTested);
        return json;
    }

}


