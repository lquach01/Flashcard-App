package persistence;

import model.CardDeck;
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
            CardDeck cd = reader.read();
            assertEquals(0, cd.getAllCards().size());
            assertEquals(0, cd.getCardsToTest().size());
            assertEquals(0, cd.getNumTested());
            assertEquals(0, cd.getNumCorrect());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralCardDeck() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralCardDeck.json");
        try {
            CardDeck cd = reader.read();
            assertEquals(2, cd.getAllCards().size());
            assertEquals(2, cd.getCardsToTest().size());
            assertEquals("cat", cd.getAllCards().get(0).getEnglishWord());
            assertEquals("chat", cd.getAllCards().get(0).getTranslation());
            assertEquals("noun", cd.getAllCards().get(0).getPartOfSpeech());

            assertEquals(2, cd.getAllCards().get(0).getPastGuesses().size());
            assertEquals("cat", cd.getAllCards().get(0).getPastGuesses().get(0));
            assertEquals("wow", cd.getAllCards().get(0).getPastGuesses().get(1));

            assertEquals("cat", cd.getCardsToTest().get(0).getEnglishWord());
            assertEquals("chat", cd.getCardsToTest().get(0).getTranslation());
            assertEquals("noun", cd.getCardsToTest().get(0).getPartOfSpeech());

            assertEquals(2, cd.getCardsToTest().get(0).getPastGuesses().size());
            assertEquals("cat", cd.getCardsToTest().get(0).getPastGuesses().get(0));
            assertEquals("wow", cd.getCardsToTest().get(0).getPastGuesses().get(1));

            assertEquals("dog", cd.getAllCards().get(1).getEnglishWord());
            assertEquals("chien", cd.getAllCards().get(1).getTranslation());
            assertEquals("noun", cd.getAllCards().get(1).getPartOfSpeech());
            assertEquals(new ArrayList<String>(), cd.getAllCards().get(1).getPastGuesses());

            assertEquals("dog", cd.getCardsToTest().get(1).getEnglishWord());
            assertEquals("chien", cd.getCardsToTest().get(1).getTranslation());
            assertEquals("noun", cd.getCardsToTest().get(1).getPartOfSpeech());
            assertEquals(new ArrayList<String>(), cd.getCardsToTest().get(1).getPastGuesses());

            assertEquals(0, cd.getNumTested());
            assertEquals(0, cd.getNumCorrect());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }


}
