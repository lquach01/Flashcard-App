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
    // EFFECTS: If cards has no flashcards in it, asks user to add cards before quizzing. Adds warning label to
    //          components
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

    // MODIFIES: this
    // EFFECTS: Shows user panel that prompts them to choose which language to get quizzed on. Adds the panel to
    //          components
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

    // MODIFIES: this
    // EFFECTS: Creates buttons that users can press to choose to start their quiz with "English" or "Translation"
    //          Calls askQuestion with the selected language
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
    // EFFECTS: Creates question panel that shows the user all the words of the cards of given language, a TextField for
    //          the user's answer, and a button to check if they are correct. Adds all components to components list.
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
    // MODIFIES: this, CardDeck
    // EFFECTS: Adds a button that checks if the String in getGuess matches the correct answer.
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

    // MODIFIES: this
    // EFFECTS: provides user with a menu that allows them to choose to load saved cardDeck or create a new cardDeck.
    //          Adds the menu to components
    public void persistenceMenu() {
        JLabel text = new JLabel("Would you like to:");
        JButton loadOldDeckOfCards = new JButton(new LoadOldCardsAction());
        JButton startNewDeckOfCards = new JButton(new StartNewFlashCardDeck());

        components.add(text);
        components.add(loadOldDeckOfCards);
        components.add(startNewDeckOfCards);
        add(text);
        add(loadOldDeckOfCards);
        add(startNewDeckOfCards);
        pack();
    }

    // MODIFIES: this
    // EFFECTS: loads cards from file with name JSON_STORE and adds it to components
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

    // LoadOldCardsAction represents the action that loads the previous cards of the game and then displays the menu-bar
    private class LoadOldCardsAction extends AbstractAction {
        LoadOldCardsAction() {
            super("Load old cards");
        }

        // EFFECTS: Clears the frame, loads the old CardDeck, and displays the menu-bar
        @Override
        public void actionPerformed(ActionEvent e) {
            clearFrame();
            loadCardDeck();
            makeMenuBar();
        }
    }

    // StartNewFlashCardDeck represents the action that creates a new deck of cards and displays the menu-bar
    private class StartNewFlashCardDeck extends AbstractAction {
        StartNewFlashCardDeck() {
            super("start Flash Card App");
        }

        // EFFECTS: Clears the frame and displays the menu-bar
        @Override
        public void actionPerformed(ActionEvent e) {
            clearFrame();
            makeMenuBar();
        }
    }

    // MODIFIES: this
    // EFFECTS: Clears all the Components in components from the frame and empties components List
    private void clearFrame() {
        for (Component component: components) {
            component.setVisible(false);
            ((JPanel) getContentPane()).remove(component);
        }
        components.clear();

        this.revalidate();
        this.repaint();
    }

    // EFFECTS: Displays a menu-bar with the options to add a flashcard, see all English words, quiz on all cards, quiz
    //          on some cards, see statistics, and save.
    private void makeMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu flashCards = new JMenu("Flashcards");
        JMenu quizMyself = new JMenu("Quiz myself");
        JMenu seeStatistics = new JMenu("See statistics");
        JMenu save = new JMenu("Save");

        JMenuItem addAFlashcard = new JMenuItem(new AddAFlashcardAction());
        JMenuItem seeAllAddedFlashcards = new JMenuItem(new DisplayAllFlashCardsInDeck());
        JMenuItem quizAllWords = new JMenuItem(new QuizAllCardsAction());
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

    // AddAFlashcardAction represents the action of creating a new flashcards and adding it to cards.
    private class AddAFlashcardAction extends AbstractAction {
        JTextField englishWord;
        JTextField translation;
        JTextField partOfSpeech;
        JButton button;

        AddAFlashcardAction() {
            super("Add a new flashcard");
        }

        // EFFECTS: Clears the frame, adds TextFields, and adds save button
        @Override
        public void actionPerformed(ActionEvent e) {
            clearFrame();
            addTextFields();
            addButton();
            pack();
        }

        // MODIFIES: this
        // EFFECTS: Adds labels and TextFields for user to add the new card's English word, translation, and part of
        //          speech
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

        // MODIFIES: this, CardDeck, Card
        // EFFECTS: Creates save button that creates a new flashcard with the inputs of the TextFields and adds it to
        //          cards. Adds the button to components
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

        // MODIFIES: this
        // EFFECTS: creates labels that display the card to the user that they just made. Adds these labels to
        //          components
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

    // SeeAllEnglishWordsAction represents the action of displaying all the elements of flashcards in the card deck.
    private class DisplayAllFlashCardsInDeck extends AbstractAction {
        DisplayAllFlashCardsInDeck() {
            super("See all cards");
        }

        // Effects: If cards has FlashCards, calls addAllCards(). If there are no cards, then displays that there
        //          are no cards in the deck. Adds label to components.
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

        // MODIFIES: this
        // EFFECTS: Displays the English words, translations, and parts of speech in cards to the users.
        //          Adds labels to components.
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

    // QuizAllCardsAction represents the action of quizzing the user on all cards.
    private class QuizAllCardsAction extends AbstractAction {
        QuizAllCardsAction() {
            super("Quiz Myself");
        }

        // MODIFIES: CardDeck
        // EFFECTS: Clears the frame, resets the untested cards in CardDeck to include all cards, calls quiz()
        @Override
        public void actionPerformed(ActionEvent e) {
            clearFrame();
            cards.resetUntestedCards();
            quiz();
        }
    }

    // FilterQuizAction represents the action of quizzing the user on certain cards, depending on the user's given part
    // of speech
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

        // EFFECTS: Creates panel that asks user to choose a part of speech and quizzes the user based on the part of
        //          speech they select.
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

        // MODIFIES: this
        // EFFECTS: Creates a panel of buttons with the different parts of speech for the user to choose from
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

        // EFFECTS: Makes it so that the user can only choose 1 part of speech
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

        // EFFECTS: Returns the part of speech that correlates to the button pressed
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

    // SeeStatisticsAction represents the action of displaying the statistics, including the number of cards that the
    // user got correct, the number of cards tested, the % of cards the user got correct, and the guesses for each card
    private class SeeStatisticsAction extends AbstractAction {
        JPanel panel;

        SeeStatisticsAction() {
            super("See statistics");
        }

        // MODIFIES: this
        // EFFECTS: Displays the user's statistics, including:
        //          - the number of correct guesses
        //          - the number of questions tried
        //          - the % of questions they got correct
        //          - the guesses to each card in the card deck
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

        // EFFECTS: displays the guesses to each card in the card deck
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

    // SaveAction represents the action of saving the card deck to the file with name JSON_STORE
    private class SaveAction extends AbstractAction {
        SaveAction() {
            super("Save");
        }

        // EFFECTS: Clears the frame and saves the cards to file
        @Override
        public void actionPerformed(ActionEvent e) {
            clearFrame();
            save();
        }

        // MODIFIES: this
        // EFFECTS: save cards to file with name JSON_STORE
        private void save() {
            JPanel finalPanel = new JPanel();
            finalPanel.setLayout(new BoxLayout(finalPanel, BoxLayout.Y_AXIS));
            String label;
            String path = "/Users/sydneyquach/IdeaProjects/TestGUI/src/images/thumbs-up-emoji-1905x2048-yh44rgtn.png";
            try {
                jsonWriter.open();
                jsonWriter.write(cards);
                jsonWriter.close();
                label = "Saved to " + JSON_STORE;
                ImageIcon thumbsUp = new ImageIcon(path);
                Image thumbsUp2 = thumbsUp.getImage().getScaledInstance(120, 120,
                        java.awt.Image.SCALE_SMOOTH);
                add(new JLabel(new ImageIcon(thumbsUp2)));

            } catch (FileNotFoundException e) {
                label = "Unable to write to file: " + JSON_STORE;
            }
            JLabel returnLabel = new JLabel(label);
            finalPanel.add(returnLabel);
            finalPanel.setBackground(BGCOLOR);
            add(finalPanel);
            components.add(finalPanel);
        }
    }

}

