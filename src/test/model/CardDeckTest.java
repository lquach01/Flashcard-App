package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CardDeckTest {
    private CardDeck testCardDeck;
    private FlashCard cardDog;
    private FlashCard cardCat;
    private FlashCard cardEat;

    @BeforeEach
     public void runBefore() {
        testCardDeck = new CardDeck();
        cardDog = new FlashCard("dog", "chien", "noun");
        cardCat = new FlashCard("cat", "chat", "noun");
        cardEat = new FlashCard("eat", "manger", "verb");

    }
    // TEST CONSTRUCTOR
    @Test
    public void testConstructor() {
        assertEquals(0, testCardDeck.getNumTested());
        assertEquals(0, testCardDeck.getNumCorrect());
        assertEquals(new ArrayList<>(), testCardDeck.getAllCards());
        assertEquals(new ArrayList<>(), testCardDeck.getCardsToTest());
    }

    // TESTS FOR ADDING CARDS

    @Test
    public void testAddOne() {
        testCardDeck.addCard(cardDog);
        List<FlashCard> result = new ArrayList<>();
        result.add(cardDog);

        assertEquals(result, testCardDeck.getAllCards());
        assertEquals(1, testCardDeck.getAllCards().size());
        assertEquals(new ArrayList<FlashCard>(), testCardDeck.getCardsToTest());

        assertEquals("dog", testCardDeck.getAllCards().get(0).getEnglishWord());
        assertEquals("chien", testCardDeck.getAllCards().get(0).getTranslation());
    }


    @Test
    public void testAddMultiple() {
        testCardDeck.addCard(cardDog);
        testCardDeck.addCard(cardCat);

        List<FlashCard> result = new ArrayList<>();
        result.add(cardDog);
        result.add(cardCat);

        assertEquals(result, testCardDeck.getAllCards());
        assertEquals(new ArrayList<FlashCard>(), testCardDeck.getCardsToTest());
        assertEquals(2, testCardDeck.getAllCards().size());

        assertEquals("dog", testCardDeck.getAllCards().get(0).getEnglishWord());
        assertEquals("chien", testCardDeck.getAllCards().get(0).getTranslation());

        assertEquals("cat", testCardDeck.getAllCards().get(1).getEnglishWord());
        assertEquals("chat", testCardDeck.getAllCards().get(1).getTranslation());
    }

    // TESTS FOR GETTING ENGLISH WORDS
    @Test
    public void getEnglishWordsNone() {
        assertEquals(new ArrayList<String>(), testCardDeck.getEnglishWords());
    }

    @Test
    public void getEnglishWordsOne() {
        testCardDeck.addCard(cardDog);

        testCardDeck.resetUntestedCards();

        assertEquals(1, testCardDeck.getEnglishWords().size());
        assertEquals("dog", testCardDeck.getEnglishWords().get(0));
    }

    @Test
    public void getEnglishWordMult() {
        testCardDeck.addCard(cardDog);
        testCardDeck.addCard(cardCat);

        testCardDeck.resetUntestedCards();

        assertEquals(2, testCardDeck.getEnglishWords().size());
        assertEquals("dog", testCardDeck.getEnglishWords().get(0));
        assertEquals("cat", testCardDeck.getEnglishWords().get(1));
    }

    // TESTS FOR GET TRANSLATIONS
    @Test
    public void getTranslationsNone() {
        assertEquals(new ArrayList<String>(), testCardDeck.getTranslations());
    }

    @Test
    public void getTranslationsOne() {
        testCardDeck.addCard(cardDog);

        testCardDeck.resetUntestedCards();

        assertEquals(1, testCardDeck.getTranslations().size());
        assertEquals("chien", testCardDeck.getTranslations().get(0));
    }

    @Test
    public void getTranslationsMult() {
        testCardDeck.addCard(cardDog);
        testCardDeck.addCard(cardCat);

        testCardDeck.resetUntestedCards();

        assertEquals(2, testCardDeck.getTranslations().size());
        assertEquals("chien", testCardDeck.getTranslations().get(0));
        assertEquals("chat", testCardDeck.getTranslations().get(1));
    }


    // TESTS FOR FILTERING CARDS BY PART OF SPEECH
    @Test
    public void testFilterGetNoneBack() {
        testCardDeck.addCard(cardDog);
        testCardDeck.addCard(cardCat);
        testCardDeck.addCard(cardEat);

        assertFalse(testCardDeck.filterByPartOfSpeech("adjective"));
        assertEquals(new ArrayList<FlashCard>(), testCardDeck.getCardsToTest());
        assertEquals(3, testCardDeck.getAllCards().size());
    }

    @Test
    public void testFilterGetSomeBack() {
        testCardDeck.addCard(cardDog);
        testCardDeck.addCard(cardCat);
        testCardDeck.addCard(cardEat);

        List<FlashCard> result = new ArrayList<>();
        result.add(cardDog);
        result.add(cardCat);

        assertTrue(testCardDeck.filterByPartOfSpeech("noun"));
        assertEquals(result, testCardDeck.getCardsToTest());
        assertEquals(2, testCardDeck.getCardsToTest().size());
        assertEquals(cardDog, testCardDeck.getCardsToTest().get(0));
        assertEquals(cardCat, testCardDeck.getCardsToTest().get(1));
        result.add(cardEat);
        assertEquals(result, testCardDeck.getAllCards());
    }

    @Test
    public void testFilterGetAllBack() {
        testCardDeck.addCard(cardDog);
        testCardDeck.addCard(cardCat);

        List<FlashCard> result = new ArrayList<>();
        result.add(cardDog);
        result.add(cardCat);

        assertTrue(testCardDeck.filterByPartOfSpeech("noun"));
        assertEquals(result, testCardDeck.getCardsToTest());
        assertEquals(2, testCardDeck.getCardsToTest().size());

        assertEquals(result, testCardDeck.getAllCards());
    }

    //TESTS FOR RESET UNTESTED CARDS
    @Test
    public void testResetNoAllCards() {
        testCardDeck.resetUntestedCards();
        assertEquals(0, testCardDeck.getAllCards().size());
        assertEquals(0, testCardDeck.getCardsToTest().size());
    }


    @Test
    public void testResetOneAllCards() {
        testCardDeck.addCard(cardDog);
        assertEquals(1, testCardDeck.getAllCards().size());
        assertEquals(0, testCardDeck.getCardsToTest().size());

        testCardDeck.resetUntestedCards();
        assertEquals(1, testCardDeck.getAllCards().size());
        assertEquals(1, testCardDeck.getCardsToTest().size());
    }

    @Test
    public void testResetMultAllCards() {
        testCardDeck.addCard(cardDog);
        testCardDeck.addCard(cardCat);

        assertEquals(2, testCardDeck.getAllCards().size());
        assertEquals(0, testCardDeck.getCardsToTest().size());

        testCardDeck.resetUntestedCards();
        assertEquals(2, testCardDeck.getAllCards().size());
        assertEquals(2, testCardDeck.getCardsToTest().size());
    }

    @Test
    public void testQuizEnglishWrong() {
        testCardDeck.addCard(cardDog);
        testCardDeck.resetUntestedCards();

        assertFalse(testCardDeck.checkIfCorrect("chat", "English", cardDog));
        assertEquals(1, testCardDeck.getNumTested());
        assertEquals(0, testCardDeck.getNumCorrect());

        ArrayList<String> previousGuesses = new ArrayList<>();
        previousGuesses.add("chat");
        assertEquals(previousGuesses, cardDog.getPastGuesses());
    }

    @Test
    public void testQuizEnglishRight() {
        testCardDeck.addCard(cardDog);
        testCardDeck.resetUntestedCards();

        assertTrue(testCardDeck.checkIfCorrect("chien", "English", cardDog));
        assertEquals(1, testCardDeck.getNumTested());
        assertEquals(1, testCardDeck.getNumCorrect());

        ArrayList<String> previousGuesses = new ArrayList<>();
        previousGuesses.add("chien");
        assertEquals(previousGuesses, cardDog.getPastGuesses());
    }

    @Test
    public void testQuizEnglishRightSkip1() {
        testCardDeck.addCard(cardCat);
        testCardDeck.addCard(cardDog);
        testCardDeck.resetUntestedCards();

        assertTrue(testCardDeck.checkIfCorrect("chien", "English", cardDog));
        assertEquals(1, testCardDeck.getNumTested());
        assertEquals(1, testCardDeck.getNumCorrect());

        ArrayList<String> previousGuesses = new ArrayList<>();
        previousGuesses.add("chien");
        assertEquals(previousGuesses, cardDog.getPastGuesses());
    }

    @Test
    public void testQuizTranslationWrong() {
        testCardDeck.addCard(cardDog);
        testCardDeck.resetUntestedCards();

        assertFalse(testCardDeck.checkIfCorrect("chicken", "Translation", cardDog));
        assertEquals(1, testCardDeck.getNumTested());
        assertEquals(0, testCardDeck.getNumCorrect());

        ArrayList<String> previousGuesses = new ArrayList<>();
        previousGuesses.add("chicken");
        assertEquals(previousGuesses, cardDog.getPastGuesses());
    }

    @Test
    public void testQuizTranslationRight() {
        testCardDeck.addCard(cardDog);
        testCardDeck.resetUntestedCards();

        assertTrue(testCardDeck.checkIfCorrect("dog", "Translation", cardDog));
        assertEquals(1, testCardDeck.getNumTested());
        assertEquals(1, testCardDeck.getNumCorrect());

        ArrayList<String> previousGuesses = new ArrayList<>();
        previousGuesses.add("dog");
        assertEquals(previousGuesses, cardDog.getPastGuesses());
    }

    @Test
    public void testQuizMultipleRight() {
        testCardDeck.addCard(cardDog);
        testCardDeck.addCard(cardCat);
        testCardDeck.resetUntestedCards();

        assertTrue(testCardDeck.checkIfCorrect("dog", "Translation", cardDog));
        assertEquals(1, testCardDeck.getNumTested());
        assertEquals(1, testCardDeck.getNumCorrect());

        assertTrue(testCardDeck.checkIfCorrect("cat", "Translation", cardCat));
        assertEquals(2, testCardDeck.getNumTested());
        assertEquals(2, testCardDeck.getNumCorrect());

        ArrayList<String> previousGuessesDog = new ArrayList<>();
        previousGuessesDog.add("dog");
        assertEquals(previousGuessesDog, cardDog.getPastGuesses());

        ArrayList<String> previousGuessesCat = new ArrayList<>();
        previousGuessesCat.add("cat");
        assertEquals(previousGuessesCat, cardCat.getPastGuesses());
    }

    @Test
    public void testQuizMultipleWrong() {
        testCardDeck.addCard(cardDog);
        testCardDeck.addCard(cardCat);
        testCardDeck.resetUntestedCards();

        assertFalse(testCardDeck.checkIfCorrect("chicken", "Translation", cardDog));
        assertEquals(1, testCardDeck.getNumTested());
        assertEquals(0, testCardDeck.getNumCorrect());

        assertFalse(testCardDeck.checkIfCorrect("potato", "Translation", cardCat));
        assertEquals(2, testCardDeck.getNumTested());
        assertEquals(0, testCardDeck.getNumCorrect());

        ArrayList<String> previousGuessesDog = new ArrayList<>();
        previousGuessesDog.add("chicken");
        assertEquals(previousGuessesDog, cardDog.getPastGuesses());

        ArrayList<String> previousGuessesCat = new ArrayList<>();
        previousGuessesCat.add("potato");
        assertEquals(previousGuessesCat, cardCat.getPastGuesses());

    }

    @Test
    public void testQuizMultipleWrongAndRight() {
        testCardDeck.addCard(cardDog);
        testCardDeck.addCard(cardCat);
        testCardDeck.resetUntestedCards();

        assertFalse(testCardDeck.checkIfCorrect("chicken", "Translation", cardDog));
        assertEquals(1, testCardDeck.getNumTested());
        assertEquals(0, testCardDeck.getNumCorrect());

        assertTrue(testCardDeck.checkIfCorrect("cat", "Translation", cardCat));
        assertEquals(2, testCardDeck.getNumTested());
        assertEquals(1, testCardDeck.getNumCorrect());

        ArrayList<String> previousGuessesDog = new ArrayList<>();
        previousGuessesDog.add("chicken");
        assertEquals(previousGuessesDog, cardDog.getPastGuesses());

        ArrayList<String> previousGuessesCat = new ArrayList<>();
        previousGuessesCat.add("cat");
        assertEquals(previousGuessesCat, cardCat.getPastGuesses());
    }

    @Test
    public void testQuizMultipleRightAndWrong() {
        testCardDeck.addCard(cardDog);
        testCardDeck.addCard(cardCat);
        testCardDeck.resetUntestedCards();

        assertTrue(testCardDeck.checkIfCorrect("dog", "Translation", cardDog));
        assertEquals(1, testCardDeck.getNumTested());
        assertEquals(1, testCardDeck.getNumCorrect());

        assertFalse(testCardDeck.checkIfCorrect("chicken", "Translation", cardCat));
        assertEquals(2, testCardDeck.getNumTested());
        assertEquals(1, testCardDeck.getNumCorrect());

        ArrayList<String> previousGuessesDog = new ArrayList<>();
        previousGuessesDog.add("dog");
        assertEquals(previousGuessesDog, cardDog.getPastGuesses());

        ArrayList<String> previousGuessesCat = new ArrayList<>();
        previousGuessesCat.add("chicken");
        assertEquals(previousGuessesCat, cardCat.getPastGuesses());
    }

}
