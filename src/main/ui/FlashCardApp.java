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

// FlashCardApp represents an application that allows users to add flashcards to a card deck, see all added flashcards,
// quiz themselves, and see their statistics.
public class FlashCardApp extends JFrame {
    private static final String JSON_STORE = "./data/cardDeck.json";
    private static final Color BGCOLOR = new Color(220, 234, 247);

    CardDeck cards;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    private ArrayList<Component> components;


    // EFFECTS: Creates new FlashCardApp
    public FlashCardApp() {
        super("FlashCard App");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(800, 650));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(13, 13, 13, 13));
        ((JPanel) getContentPane()).setBackground(BGCOLOR);
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



    // MODIFIES: this
    // EFFECTS: If cards has no flashcards in it, asks user to add cards before quizzing
    //          If there are 1+ flashcards in cards, asks user if they want to start with the English word or
    //          the translation and asks the question.
    private void quiz() {
        clearFrame();
        if (cards.getCardsToTest().size() == 0) {
            JLabel noCardsWarning = new JLabel("Please add cards before quizzing");
            add(noCardsWarning);
            components.add(noCardsWarning);
        } else {
            makeLanguagesPanel();
        }
        pack();
    }

    private void makeLanguagesPanel() {
        JPanel panel = new JPanel(new GridLayout(4,1));
        panel.add(new JLabel("Welcome to the quiz"));
        panel.add(new JLabel("Which would you like to start with?"));
        JPanel languagesPanel = makeLanguageButtonPanel();
        panel.add(languagesPanel);

        languagesPanel.setBackground(BGCOLOR);
        panel.setBackground(BGCOLOR);
        add(panel);
        components.add(panel);
    }

    private JPanel makeLanguageButtonPanel() {
        JPanel languagesPanel = new JPanel(new GridLayout(1, 2));
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
        languagesPanel.add(startWithEnglish);
        languagesPanel.add(startWithTranslation);
        return languagesPanel;
    }

    // REQUIRES: Language must be "English" or "Translation"
    // MODIFIES: this
    // EFFECTS: Prints the question (the word that matches the given language) and prompts the user for their guess,
    //          and calls the checkIsRight method to check if the guess is correct
    private void askQuestion(String language) {
        clearFrame();
        JPanel questionPanel = new JPanel(new GridLayout(cards.getCardsToTest().size(), 3));
        JLabel resultLabel = new JLabel();
        add(resultLabel);
        components.add(resultLabel);
        for (int i = 0; i < cards.getCardsToTest().size(); i++) {
            if (language.equals("English")) {
                JLabel englishWord = new JLabel(cards.getEnglishWords().get(i) + ":");
                questionPanel.add(englishWord);
            } else {
                JLabel translation = new JLabel(cards.getTranslations().get(i) + ":");
                questionPanel.add(translation);
            }
            JTextField getGuess = new JTextField(20);
            questionPanel.add(getGuess);
            questionPanel.setBackground(BGCOLOR);
            add(questionPanel);
            addCheckIfCorrectButton(questionPanel, language, i, getGuess, resultLabel);
            components.add(questionPanel);
            pack();

        }
    }


    // REQUIRES: language is one of: "English" or "Translation"
    // EFFECTS: Checks if the guess is correct
    private void addCheckIfCorrectButton(JPanel questionPanel, String language, int i, JTextField getGuess,
                                         JLabel resultLabel) {
        JButton checkIfCorrect = new JButton("Check if correct");
        questionPanel.add(checkIfCorrect);
        checkIfCorrect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cards.quiz(getGuess.getText(), language, cards.getCardsToTest().get(i))) {
                    resultLabel.setText("Previous Guess was Correct!");
                    checkIfCorrect.setVisible(false);
                } else {
                    resultLabel.setText("Previous Guess was False");
                }
            }
        });
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
        JTextField englishWord;
        JTextField translation;
        JTextField partOfSpeech;
        JButton button;

        AddAFlashcardAction() {
            super("Add a new flashcard");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            clearFrame();
            addTextFields();
            addButton();
            pack();
        }

        private void addTextFields() {
            JPanel panel = new JPanel(new GridLayout(3,2));
            JLabel englishWordLabel = new JLabel("English Word: ");
            englishWord = new JTextField(30);
            JLabel translationLabel = new JLabel("Translation: ");
            translation = new JTextField(30);
            JLabel partOfSpeechLabel = new JLabel("Part of Speech: ");
            partOfSpeech = new JTextField(30);

            panel.add(englishWordLabel);
            panel.add(englishWord);
            panel.add(translationLabel);
            panel.add(translation);
            panel.add(partOfSpeechLabel);
            panel.add(partOfSpeech);

            panel.setBackground(BGCOLOR);
            add(panel);
            components.add(panel);
        }

        private void addButton() {
            button = new JButton("Save");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String enteredEnglishWord = englishWord.getText();
                    String enteredTranslation = translation.getText();
                    String enteredPartOfSpeech = partOfSpeech.getText();

                    FlashCard flashCard = new FlashCard(enteredEnglishWord, enteredTranslation, enteredPartOfSpeech);
                    cards.addCard(flashCard);

                    clearFrame();
                    makeSuccessLabel(enteredEnglishWord, enteredTranslation, enteredPartOfSpeech);
                }
            });
            add(button);
            components.add(button);
        }

        private void makeSuccessLabel(String enteredEnglishWord,
                                      String enteredTranslation,
                                      String enteredPartOfSpeech) {

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            JLabel englishLabel = new JLabel("English word: " + enteredEnglishWord);
            JLabel translationLabel = new JLabel("Translation: " + enteredTranslation);
            JLabel partOfSpeechLabel = new JLabel("Part of Speech: " + enteredPartOfSpeech);

            panel.add(englishLabel);
            panel.add(translationLabel);
            panel.add(partOfSpeechLabel);

            panel.setBackground(BGCOLOR);

            add(panel);
            components.add(panel);
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
                addAllCards();
            }
            pack();
        }

        private void addAllCards() {
            JLabel allWordsLabel = new JLabel("All words in deck of card: ");
            components.add(allWordsLabel);
            for (FlashCard card : cards.getAllCards()) {
                JPanel flashCard = new JPanel();
                flashCard.setLayout(new BoxLayout(flashCard, BoxLayout.Y_AXIS));
                JLabel englishWordLabel = new JLabel("English: " + card.getEnglishWord());
                JLabel translationWord = new JLabel("Translation: " + card.getTranslation());
                JLabel partOfSpeech = new JLabel("Part of Speech: " + card.getPartOfSpeech());
                flashCard.add(englishWordLabel);
                flashCard.add(translationWord);
                flashCard.add(partOfSpeech);
                flashCard.setBorder(new EmptyBorder(10, 10, 10, 10));
                add(flashCard);
                components.add(flashCard);
            }
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
        JRadioButton nounButton;
        JRadioButton pronounButton;
        JRadioButton verbButton;
        JRadioButton adjectiveButton;
        JRadioButton articleButton;
        JRadioButton conjunctionButton;
        JRadioButton prepositionButton;

        FilterQuizAction() {
            super("Filter by Part Of Speech");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            clearFrame();
            addButtonPanel();
            addButtonsToButtonGroup();

            JButton submitButton = new JButton("Submit");
            add(submitButton);
            components.add(submitButton);
            pack();
            submitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String partOfSpeech = getPartOfSpeech();

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

        private void addButtonPanel() {
            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(8,1));

            JLabel askWhatPartOfSpeech = new JLabel("What is the part of speech you want to filter by?");
            nounButton = new JRadioButton("Noun");
            pronounButton = new JRadioButton("Pronoun");
            verbButton = new JRadioButton("Verb");
            adjectiveButton = new JRadioButton("Adjective");
            articleButton = new JRadioButton("Article");
            conjunctionButton = new JRadioButton("Conjunction");
            prepositionButton = new JRadioButton("Preposition");

            panel.add(askWhatPartOfSpeech);
            panel.add(nounButton);
            panel.add(pronounButton);
            panel.add(verbButton);
            panel.add(adjectiveButton);
            panel.add(articleButton);
            panel.add(conjunctionButton);
            panel.add(prepositionButton);

            panel.setBackground(BGCOLOR);
            add(panel);
            components.add(panel);
        }

        private void addButtonsToButtonGroup() {
            ButtonGroup buttonGroup = new ButtonGroup();
            buttonGroup.add(nounButton);
            buttonGroup.add(pronounButton);
            buttonGroup.add(verbButton);
            buttonGroup.add(adjectiveButton);
            buttonGroup.add(articleButton);
            buttonGroup.add(conjunctionButton);
            buttonGroup.add(prepositionButton);

        }

        private String getPartOfSpeech() {
            if (nounButton.isSelected()) {
                return "noun";
            } else if (pronounButton.isSelected()) {
                return "pronoun";
            } else if (verbButton.isSelected()) {
                return "verb";
            } else if (adjectiveButton.isSelected()) {
                return "adjective";
            } else if (articleButton.isSelected()) {
                return "article";
            } else if (conjunctionButton.isSelected()) {
                return "conjunction";
            } else {
                return "preposition";
            }
        }
    }

    // Displays the statistics, including the number of cards that the user got correct, the number of cards tested, the
    // % of cards the user got correct, and the guesses for each card
    private class SeeStatisticsAction extends AbstractAction {
        JPanel panel;

        SeeStatisticsAction() {
            super("See statistics");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            clearFrame();
            panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            double numCorrect = cards.getNumCorrect();
            double numAttempts = cards.getNumTested();

            JLabel numCorrectLabel = new JLabel("Number of correct guesses: " + numCorrect);
            JLabel numAttemptsLabel = new JLabel("Number of questions tried: " + numAttempts);

            panel.add(numCorrectLabel);
            panel.add(numAttemptsLabel);

            if (numAttempts > 0) {
                JLabel percentCorrect = new
                        JLabel("% of questions correct: " + (numCorrect / numAttempts) * 100 + "%");
                panel.add(percentCorrect);
            }
            addPreviousGuesses();
            panel.setBackground(BGCOLOR);
            add(panel);
            pack();
            components.add(panel);
        }

        private void addPreviousGuesses() {
            panel.add(new JLabel("____________________________________________________"));
            JLabel previousGuessesLabel = new JLabel("All previous guesses per word:");
            panel.add(previousGuessesLabel);
            for (FlashCard flashCard: cards.getAllCards()) {
                String guessList = flashCard.getEnglishWord() + ": ";
                for (String guess: flashCard.getPastGuesses()) {
                    guessList = guessList + guess + ", ";
                }
                JLabel guessesLabel = new JLabel(guessList);
                add(guessesLabel);
                panel.add(guessesLabel);
            }
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

