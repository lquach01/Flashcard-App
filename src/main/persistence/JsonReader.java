package persistence;

import model.CardDeck;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import model.FlashCard;
import org.json.*;

// Represents a reader that reads workroom from JSON data stored in file
// This class was inspired by the given demo, JsonSerializationDemo-WorkRoom.java
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public CardDeck read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseCardDeck(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // MODIFIES: CardDeck
    // EFFECTS: parses cardDeck from JSON object and returns it
    private CardDeck parseCardDeck(JSONObject jsonObject) {
        int numCorrect = jsonObject.getInt("numCorrect");
        int numTested = jsonObject.getInt("numTested");
        JSONArray allCardsJson = (JSONArray) jsonObject.get("allCards");
        JSONArray cardsToTestJson = (JSONArray) jsonObject.get("cardsToTest");

        ArrayList<FlashCard> allCards = parseArrayOfCard(allCardsJson);
        ArrayList<FlashCard> cardsToTest = parseArrayOfCard(cardsToTestJson);

        CardDeck cd = new CardDeck(allCards, cardsToTest, numCorrect, numTested);

        return cd;
    }

    // MODIFIES: FlashCard
    // EFFECTS: Parses cardsJson to create an ArrayList of the cards given in JSONArray
    private ArrayList<FlashCard> parseArrayOfCard(JSONArray cardsJson) {
        ArrayList<FlashCard> flashCards = new ArrayList<>();
        for (int i = 0; i < cardsJson.length(); i++) {
            JSONObject card = cardsJson.getJSONObject(i);

            String englishWord = (String) card.get("englishWord");
            String translation = (String) card.get("translation");
            String partOfSpeech = (String) card.get("partOfSpeech");
            ArrayList<String> pastGuesses = parsePastGuesses(card.getJSONArray("pastGuesses"));

            FlashCard fc = new FlashCard(englishWord, translation, partOfSpeech, pastGuesses);


            flashCards.add(fc);
        }
        return flashCards;
    }

    // EFFECTS: Returns an ArrayList of the Strings in pastGuessesJson
    private ArrayList<String> parsePastGuesses(JSONArray pastGuessesJson) {
        ArrayList<String> pastGuesses = new ArrayList<>();
        for (Object pastGuess: pastGuessesJson) {
            pastGuesses.add((String) pastGuess);
        }

        return pastGuesses;
    }
}
