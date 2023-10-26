package persistence;

import model.CardDeck;
import model.FlashCard;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    // EFFECTS: Checks that the cards in both cardDecks are the same
    protected void checkSameCardDeck(CardDeck cardDeck1, CardDeck cardDeck2) {
        assertEquals(cardDeck1.getAllCards().size(), cardDeck2.getAllCards().size());
        assertEquals(cardDeck1.getCardsToTest().size(), cardDeck2.getCardsToTest().size());

        for (int i = 0; i < cardDeck1.getAllCards().size(); i++) {
            checkSameCards(cardDeck1.getAllCards().get(i), cardDeck1.getAllCards().get(i));
        }

        for (int i = 0; i < cardDeck1.getCardsToTest().size(); i++) {
            checkSameCards(cardDeck1.getCardsToTest().get(i), cardDeck1.getCardsToTest().get(i));
        }

        assertEquals(cardDeck1.getNumCorrect(), cardDeck2.getNumCorrect());
        assertEquals(cardDeck1.getNumTested(), cardDeck2.getNumTested());
    }

    // EFFECTS: tests that fc1 has the same value as fc2
    private void checkSameCards(FlashCard fc1, FlashCard fc2) {
        assertEquals(fc1.getPastGuesses().size(), fc2.getPastGuesses().size());
        assertEquals(fc1.getEnglishWord(), fc2.getEnglishWord());
        assertEquals(fc1.getPartOfSpeech(), fc2.getPartOfSpeech());
        assertEquals(fc1.getTranslation(), fc2.getTranslation());
    }
}
