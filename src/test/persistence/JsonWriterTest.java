package persistence;

import model.CardDeck;
import model.FlashCard;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest {

    @Test
    void testWriterInvalidFile() {
        try {
            CardDeck cd = new CardDeck();
            JsonWriter writer = new JsonWriter("./data/\0illegal.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyFile() {
        try{
            CardDeck cd = new CardDeck();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyCardDeck.json");
            writer.open();
            writer.write(cd);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyCardDeck.json");
            cd = reader.read();
            assertEquals(0, cd.getAllCards().size());
            assertEquals(0, cd.getCardsToTest().size());
            assertEquals(0, cd.getNumTested());
            assertEquals(0, cd.getNumCorrect());
        } catch (FileNotFoundException e) {
            fail("FileNotFoundException should not have been thrown");
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralCardDeck() {
        try{
            ArrayList<FlashCard> cards = new ArrayList<>();
            FlashCard catCard = new FlashCard("cat", "chat", "noun");
            catCard.addToPastGuesses("cat");
            catCard.addToPastGuesses("wow");
            cards.add(catCard);

            cards.add(new FlashCard("dog", "chien", "noun"));

            CardDeck cd = new CardDeck(cards, cards, 0, 0);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralCardDeck.json");
            writer.open();
            writer.write(cd);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralCardDeck.json");
            cd = reader.read();
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
        } catch (FileNotFoundException e) {
            fail("FileNotFoundException should not have been thrown");
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }


}
