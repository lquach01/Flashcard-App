package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FlashCardTest {
    private FlashCard flashCard;

    @BeforeEach
    public void runBefore() {
        flashCard = new FlashCard("dog", "chien", "noun");
    }

    @Test
    public void testConstructor() {
        assertEquals("dog", flashCard.getEnglishWord());
        assertEquals("chien", flashCard.getTranslation());
        assertEquals("noun", flashCard.getPartOfSpeech());
    }

    @Test
    public void testSetEnglish() {
        flashCard.setEnglishWord("cat");
        assertEquals("cat", flashCard.getEnglishWord());
    }

    @Test
    public void testSetTranslation() {
        flashCard.setTranslation("chat");
        assertEquals("chat", flashCard.getTranslation());
    }

    @Test
    public void testSetPartOfSpeech() {
        flashCard.setPartOfSpeech("verb");
        assertEquals("verb", flashCard.getPartOfSpeech());

    }

}