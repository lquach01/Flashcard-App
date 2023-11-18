package ui;

import model.CardDeck;
import model.FlashCard;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// FlashCardApp represents an application that allows users to add flashcards to a card deck, see all added flashcards,
// quiz themselves, and see their statistics.
public class FlashCardApp extends JFrame {
    private static final String JSON_STORE = "./data/cardDeck.json";
    CardDeck cards;
    Scanner scanner = new Scanner(System.in);
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    private ArrayList<Component> components;

    // EFFECTS: Creates new FlashCardApp
    public FlashCardApp() {
        super("FlashCard App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 650));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13) );
        ((JPanel) getContentPane()).setBackground(Color.pink);
        setLayout(new FlowLayout());
        pack();

        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        cards = new CardDeck();
        System.out.println("Welcome to the Language FlashCards :)");
        components = new ArrayList<>();

        persistenceMenu();

    }


    private void filterByPartOfSpeech() {
        clearFrame();
        JLabel askWhatPartOfSpeech = new JLabel("What is the part of speech you want to filter by?");
        JRadioButton nounButton = new JRadioButton("Noun");
        JRadioButton pronounButton = new JRadioButton("Pronoun");
        JRadioButton verbButton = new JRadioButton("Verb");
        JRadioButton adjectiveButton = new JRadioButton("Adjective");
        JRadioButton articleButton = new JRadioButton("Article");
        JRadioButton conjunctionButton = new JRadioButton("Conjunction");
        JRadioButton prepositionButton = new JRadioButton("Preposition");

        add(askWhatPartOfSpeech);
        add(nounButton);
        add(pronounButton);
        add(verbButton);
        add(adjectiveButton);
        add(articleButton);
        add(conjunctionButton);
        add(prepositionButton);

        components.add(askWhatPartOfSpeech);
        components.add(nounButton);
        components.add(pronounButton);
        components.add(verbButton);
        components.add(adjectiveButton);
        components.add(articleButton);
        components.add(conjunctionButton);
        components.add(prepositionButton);

        JButton submitButton = new JButton("Submit");
        add(submitButton);
        components.add(submitButton);
        pack();
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String partOfSpeech;
                if (nounButton.isSelected()) {
                    partOfSpeech = "noun";
                } else if (pronounButton.isSelected()) {
                    partOfSpeech = "pronoun";
                } else if (verbButton.isSelected()) {
                    partOfSpeech = "verb";
                } else if (adjectiveButton.isSelected()) {
                    partOfSpeech = "adjective";
                } else if (articleButton.isSelected()) {
                    partOfSpeech = "article";
                } else if (conjunctionButton.isSelected()) {
                    partOfSpeech = "conjunction";
                } else {
                    partOfSpeech = "preposition";
                }
                if (cards.filterByPartOfSpeech(partOfSpeech)) {
                    quiz();
                } else {
                    JLabel warning = new
                            JLabel("Sorry, there are no cards that match the selected part(s) of speech");
                    add(warning);
                    components.add(warning);
                    pack();
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: If cards has no flashcards in it, asks user to add cards before quizzing
    //          If there are 1+ flashcards in cards, asks user if they want to start with the English word or
    //          the translation and asks the question.
    private void quiz() {
        clearFrame();
        if (cards.getCardsToTest().size() == 0) {
            // System.out.println("Please add cards before quizzing");
            JLabel noCardsWarning = new JLabel("Please add cards before quizzing");
            add(noCardsWarning);
            components.add(noCardsWarning);
        } else {
            JLabel welcome = new JLabel("Welcome to the quiz");
            JLabel startWithWhatLabel =
                    new JLabel("Which would you like to start with?");
            JButton startWithEnglish = new JButton("English");
            JButton startWithTranslation = new JButton("Translation");

            startWithEnglish.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    askQuestion("English");
                }
            });
            startWithTranslation.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    askQuestion("Translation");
                }
            });
            add(welcome);
            add(startWithWhatLabel);
            add(startWithEnglish);
            add(startWithTranslation);

            components.add(welcome);
            components.add(startWithEnglish);
            components.add(startWithWhatLabel);
            components.add(startWithTranslation);
        }
        pack();
    }

    // REQUIRES: Language must be "English" or "Translation"
    // MODIFIES: this
    // EFFECTS: Prints the question (the word that matches the given language) and prompts the user for their guess,
    //          and calls the checkIsRight method to check if the guess is correct
    private void askQuestion(String language) {
        clearFrame();
        for (int i = 0; i < cards.getCardsToTest().size(); i++) {
            if (language.equals("English")) {
                // System.out.println(cards.getEnglishWords().get(i));
                JLabel englishWord = new JLabel(cards.getEnglishWords().get(i));
                add(englishWord);
                components.add(englishWord);
            } else {
                // System.out.println(cards.getTranslations().get(i));
                JLabel translation = new JLabel(cards.getTranslations().get(i));
                add(translation);
                components.add(translation);
            }
            //String guess = scanner.nextLine();
            JTextField getGuess = new JTextField(20);
            JButton checkIfCorrect = new JButton("Check if correct");
            add(getGuess);
            add(checkIfCorrect);
            components.add(getGuess);
            components.add(checkIfCorrect);
            pack();
            int finalI = i;
            checkIfCorrect.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    checkIsRight(getGuess.getText(), language, finalI);
                }
            });

        }
    }

    // REQUIRES: index >= 0, language is one of: "English" or "Translation"
    // EFFECTS: Checks if the guess is correct
    private void checkIsRight(String guess, String language, int index) {
        JLabel result;
        if (cards.quiz(guess, language, index)) {
            //System.out.println("YOU GOT IT");
            result = new JLabel("YOU GOT IT");
        } else {
            //System.out.println("WRONG");
            result = new JLabel("WRONG");
        }
        add(result);
        components.add(result);
        pack();
    }

    // EFFECTS: Prints the statistics, including the number of cards that the user got correct, the number of cards
    //          tested, the % of cards the user got correct, and the guesses for each card
    private void seeStats() {
        double numCorrect = cards.getNumCorrect();
        double numAttempts = cards.getNumTested();

        JLabel numCorrectLabel = new JLabel("Number correct: " + numCorrect);
        JLabel numAttemptsLabel = new JLabel("Number tried: " + numAttempts);

        add(numCorrectLabel);
        add(numAttemptsLabel);
        components.add(numAttemptsLabel);
        components.add(numCorrectLabel);
        if (numAttempts > 0) {
            JLabel percentCorrect = new
                    JLabel("% of questions correct: " + (numCorrect / numAttempts) * 100 + "%");
            add(percentCorrect);
            components.add(percentCorrect);
        }
        JLabel previousGuessesLabel = new JLabel("All previous guesses per word:");
        add(previousGuessesLabel);
        components.add(previousGuessesLabel);
        for (FlashCard flashCard: cards.getAllCards()) {
            String guessList = flashCard.getEnglishWord() + ": ";
            for (String guess: flashCard.getPastGuesses()) {
                guessList = guessList + guess + ", ";
            }
            JLabel guessesLabel = new JLabel(guessList);
            add(guessesLabel);
            components.add(guessesLabel);
        }
        pack();
    }

    // EFFECTS: provides user with a menu that allows them to choose to load saved cardDeck or create a new cardDeck
    public void persistenceMenu() {
        JLabel text = new JLabel("Would you like to:");
        JButton loadOldDeckOfCards = new JButton(new LoadOldCardsAction());
        JButton startNewDeckOfCards = new JButton(new StartFlashCardApp());

        components.add(text);
        components.add(loadOldDeckOfCards);
        components.add(startNewDeckOfCards);
        add(text);
        add(loadOldDeckOfCards);
        add(startNewDeckOfCards);
        pack();
    }

    // MODIFIES: this
    // EFFECTS: loads cards from file with name JSON_STORE
    private void loadCardDeck() {
        try {
            cards = jsonReader.read();
            JLabel loadedConfirmation = new JLabel("Loaded card deck from " + JSON_STORE);
            add(loadedConfirmation);
            components.add(loadedConfirmation);
        } catch (IOException e) {
            JLabel loadedConfirmation = new JLabel("Unable to read from file: " + JSON_STORE);
            add(loadedConfirmation);
            components.add(loadedConfirmation);
        }
    }

    // MODIFIES: this
    // EFFECTS: save cards to file with name JSON_STORE
    private void save() {
        String label;
        try {
            jsonWriter.open();
            jsonWriter.write(cards);
            jsonWriter.close();
            label = "Saved to " + JSON_STORE;
        } catch (FileNotFoundException e) {
            label = "Unable to write to file: " + JSON_STORE;
        }
        JLabel returnLabel = new JLabel(label);
        add(returnLabel);
        components.add(returnLabel);
    }

    private class LoadOldCardsAction extends AbstractAction {

        LoadOldCardsAction() {
            super("load old cards");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            clearFrame();
            loadCardDeck();
            makeMenuBar();
        }
    }

    private class StartFlashCardApp extends AbstractAction {
        StartFlashCardApp() {
            super("start Flash Card App");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            clearFrame();
            makeMenuBar();
        }
    }

    private void clearFrame() {
        for (Component component: components) {
            component.setVisible(false);
            ((JPanel) getContentPane()).remove(component);
        }
        components.clear();

        this.revalidate();
        this.repaint();
    }

    private void makeMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu flashCards = new JMenu("Flashcards");
        JMenu quizMyself = new JMenu("Quiz myself");
        JMenu seeStatistics = new JMenu("See statistics");
        JMenu save = new JMenu("Save");

        JMenuItem addAFlashcard = new JMenuItem(new AddAFlashcardAction());
        JMenuItem seeAllAddedFlashcards = new JMenuItem(new SeeAllEnglishWordsAction());
        JMenuItem quizAllWords = new JMenuItem(new QuizAction());
        JMenuItem filterByPartOfSpeech = new JMenuItem(new FilterQuizAction());
        JMenuItem seeStatsItem = new JMenuItem(new SeeStatisticsAction());
        JMenuItem saveItem = new JMenuItem(new SaveAction());

        flashCards.add(addAFlashcard);
        flashCards.add(seeAllAddedFlashcards);

        quizMyself.add(quizAllWords);
        quizMyself.add(filterByPartOfSpeech);

        seeStatistics.add(seeStatsItem);
        save.add(saveItem);

        menuBar.add(flashCards);
        menuBar.add(quizMyself);
        menuBar.add(seeStatistics);
        menuBar.add(save);

        this.setJMenuBar(menuBar);
    }

    private class AddAFlashcardAction extends AbstractAction {
        AddAFlashcardAction() {
            super("Add a new flashcard");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            clearFrame();

            JTextField englishWord = new JTextField(30);
            JTextField translation = new JTextField(30);
            JTextField partOfSpeech = new JTextField(30);
            JButton button = new JButton("Save");


            JLabel resultEnglish = new JLabel("WILL BE HERE");
            JLabel resultTranslation = new JLabel("WILL BE HERE");
            JLabel resultPartOfSpeech = new JLabel("WILL BE HERE");
            resultEnglish.setVisible(false);
            resultTranslation.setVisible(false);
            resultPartOfSpeech.setVisible(false);

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String enteredText = englishWord.getText();
                    String enteredTranslation = translation.getText();
                    String enteredPartOfSpeech = partOfSpeech.getText();
                    resultEnglish.setText("English Word: " + enteredText);
                    resultTranslation.setText("Translation: " + enteredTranslation);
                    resultPartOfSpeech.setText("Part of Speech: " + enteredPartOfSpeech);
                    resultEnglish.setVisible(true);
                    resultTranslation.setVisible(true);
                    resultPartOfSpeech.setVisible(true);

                    FlashCard flashCard = new FlashCard(enteredText, enteredTranslation, enteredPartOfSpeech);
                    cards.addCard(flashCard);
                }
            });

            add(englishWord);
            add(translation);
            add(partOfSpeech);
            add(resultEnglish);
            add(resultTranslation);
            add(resultPartOfSpeech);
            add(button);
            pack();
            components.add(englishWord);
            components.add(translation);
            components.add(partOfSpeech);
            components.add(resultEnglish);
            components.add(resultTranslation);
            components.add(resultPartOfSpeech);
            components.add(button);

        }
    }


    private class SeeAllEnglishWordsAction extends AbstractAction {
        SeeAllEnglishWordsAction() {
            super("See all English words");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            clearFrame();
            if (cards.getAllCards().size() == 0) {
                JLabel noCardsAddedLabel = new JLabel("There are no cards added to the deck");
                components.add(noCardsAddedLabel);
                add(noCardsAddedLabel);
            } else {
                cards.resetUntestedCards();
                JLabel allWordsLabel = new JLabel("All words in deck of card: ");
                components.add(allWordsLabel);
                for (String englishWord : cards.getEnglishWords()) {
                    JLabel englishWordLabel = new JLabel(englishWord);
                    add(englishWordLabel);
                    components.add(englishWordLabel);
                }
            }
            pack();
        }
    }

    private class QuizAction extends AbstractAction {
        QuizAction() {
            super("Quiz Myself");
        }


        @Override
        public void actionPerformed(ActionEvent e) {
            clearFrame();
            cards.resetUntestedCards();
            quiz();
        }
    }

    private class FilterQuizAction extends AbstractAction {
        FilterQuizAction() {
            super("Filter by Part Of Speech");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            filterByPartOfSpeech();
        }
    }

    private class SeeStatisticsAction extends AbstractAction {
        SeeStatisticsAction() {
            super("See statistics");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            clearFrame();
            seeStats();
        }
    }

    private class SaveAction extends AbstractAction {
        SaveAction() {
            super("Save");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            clearFrame();
            save();
        }
    }

}

