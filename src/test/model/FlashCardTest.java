package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FlashCardTest {
    private FlashCard testFlashCard;

    @BeforeEach
    public void runBefore() {
        testFlashCard = new FlashCard("dog", "chien", "noun");
    }

    @Test
    public void testConstructor() {
        assertEquals("dog", testFlashCard.getEnglishWord());
        assertEquals("chien", testFlashCard.getTranslation());
        assertEquals("noun", testFlashCard.getPartOfSpeech());
    }

    @Test
    public void testAddToPastGuessesOnce() {
        assertTrue(testFlashCard.addToPastGuesses("chien"));
        ArrayList<String> answer = new ArrayList<>();
        answer.add("chien");
        assertEquals(answer, testFlashCard.getPastGuesses());
    }

    @Test
    public void testAddToPastGuessesMultNoRepeat() {
        assertTrue(testFlashCard.addToPastGuesses("chien"));
        assertTrue(testFlashCard.addToPastGuesses("chat"));
        ArrayList<String> answer = new ArrayList<>();
        answer.add("chien");
        answer.add("chat");
        assertEquals(answer, testFlashCard.getPastGuesses());
    }

    @Test
    public void testAddToPastGuessesMultWithRepeat() {
        assertTrue(testFlashCard.addToPastGuesses("chien"));
        assertFalse(testFlashCard.addToPastGuesses("chien"));
        ArrayList<String> answer = new ArrayList<>();
        answer.add("chien");
        assertEquals(answer, testFlashCard.getPastGuesses());
    }


    @Test
    public void testSetEnglish() {
        testFlashCard.setEnglishWord("cat");
        assertEquals("cat", testFlashCard.getEnglishWord());
    }

    @Test
    public void testSetTranslation() {
        testFlashCard.setTranslation("chat");
        assertEquals("chat", testFlashCard.getTranslation());
    }

    @Test
    public void testSetPartOfSpeech() {
        testFlashCard.setPartOfSpeech("verb");
        assertEquals("verb", testFlashCard.getPartOfSpeech());

    }



}