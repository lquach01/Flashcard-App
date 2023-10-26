package persistence;

import model.CardDeck;
import model.FlashCard;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest{
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            CardDeck cd = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyCardDeck() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyCardDeck.json");
        try {
            CardDeck cd1 = new CardDeck(new ArrayList<>(), new ArrayList<>(), 0, 0);
            CardDeck cd2 = reader.read();
            checkSameCardDeck(cd1, cd2);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralCardDeck() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralCardDeck.json");
        try {
            ArrayList<FlashCard> cards = new ArrayList<>();
            FlashCard catCard = new FlashCard("cat", "chat", "noun");
            catCard.addToPastGuesses("cat");
            catCard.addToPastGuesses("wow");
            cards.add(catCard);

            cards.add(new FlashCard("dog", "chien", "noun"));

            CardDeck cd1 = new CardDeck(cards, cards, 0, 0);

            CardDeck cd2 = reader.read();

            checkSameCardDeck(cd1, cd2);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }


}
