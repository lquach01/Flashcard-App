package persistence;

import model.CardDeck;
import model.FlashCard;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTest{

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
            CardDeck cd1 = new CardDeck();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyCardDeck.json");
            writer.open();
            writer.write(cd1);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyCardDeck.json");
            CardDeck cd2 = reader.read();
            checkSameCardDeck(cd1, cd2);
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

            CardDeck cd1 = new CardDeck(cards, cards, 0, 0);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralCardDeck.json");
            writer.open();
            writer.write(cd1);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralCardDeck.json");
            CardDeck cd2 = reader.read();
            checkSameCardDeck(cd1, cd2);
        } catch (FileNotFoundException e) {
            fail("FileNotFoundException should not have been thrown");
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }


}
