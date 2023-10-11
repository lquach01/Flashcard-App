package ui;

import model.CardDeck;
import model.FlashCard;

import java.util.Scanner;

public class FlashCardApp {
    CardDeck cards;
    Scanner scanner = new Scanner(System.in);

    public FlashCardApp() {
        cards = new CardDeck();
        System.out.println("Welcome to the Language FlashCards :)");
        menu();

    }

    private void menu() {
        System.out.println("Would you like to:");
        System.out.println("Add a flashcard (Enter 1)");
        System.out.println("See all added flashcards (Enter 2)");
        System.out.println("Quiz myself! (Enter 3)");
        System.out.println("See statistics (Enter 4)");
        System.out.println("Quit (Enter 5)");
        String operation = scanner.nextLine();

        while (true) {
            if (operation.equals("1")) {
                addAFlashCard();
            } else if (operation.equals("2")) {
                seeAllEnglishWords();
            } else if (operation.equals("3")) {
                quizCenter();
            } else if (operation.equals("4")) {
                seeStats();
            } else if (operation.equals("5")) {
                System.exit(0);
            } else {
                System.out.println("That's not an option");
                menu();
            }
        }
    }

    private void addAFlashCard() {
        System.out.println("What is the English word?");
        String englishWord = scanner.nextLine();
        System.out.println("What is the translation?");
        String translation = scanner.nextLine();
        System.out.println("What is the part of speech?");
        System.out.print("Must be one of: 'noun', 'pronoun', 'verb', 'adjective', 'adverb', ");
        System.out.println("'article', 'conjunction', or 'preposition'");
        String partOfSpeech = scanner.nextLine();

        FlashCard flashCard = new FlashCard(englishWord, translation, partOfSpeech);
        cards.addCard(flashCard);

        menu();
    }

    private void seeAllEnglishWords() {
        if (cards.getAllCards().size() == 0) {
            System.out.println("There are no cards added to the deck");
            menu();
        } else {
            cards.resetUntestedCards();
            System.out.println("All words in deck of card: ");
            for (String englishWord : cards.getEnglishWords()) {
                System.out.println(englishWord);
            }
            menu();
        }
    }

    private void quizCenter() {
        System.out.println("Would you like to:");
        System.out.println("Quiz all words (Enter 1)");
        System.out.println("Filter by Part Of Speech (Enter 2)");
        System.out.println("Go back (Enter 3)");
        String operation = scanner.nextLine();

        if (operation.equals("1")) {
            cards.resetUntestedCards();
            quiz();
        } else if (operation.equals("2")) {
            System.out.println("What is the part of speech you want to filter by?");
            System.out.print("Must be one of: 'noun', 'pronoun', 'verb', 'adjective', 'adverb', ");
            System.out.println("'article', 'conjunction', or preposition");
            String partOfSpeech = scanner.nextLine();
            if (cards.filterByPartOfSpeech(partOfSpeech)) {
                quiz();
            } else {
                System.out.println("Sorry, there are no cards that match that part of speech");
                System.out.println("Add card with that part of speech");
                menu();
            }
        } else {
            menu();
        }
    }


    private void quiz() {
        if (cards.getCardsToTest().size() == 0) {
            System.out.println("Please add cards before quizzing");
            System.out.println();
            menu();
        }

        System.out.println("Welcome to the quiz");
        System.out.println("Would you like to start with the English words (1) or the translations (2)?");
        System.out.println("Enter 1 or 2");
        String res = scanner.nextLine();


        if (res.equals("1")) {
            askQuestion("English");
        } else {
            askQuestion("Translation");
        }

    }

    private void askQuestion(String language) {
        for (int i = 0; i < cards.getCardsToTest().size(); i++) {
            if (language.equals("English")) {
                System.out.println(cards.getEnglishWords().get(i));
            } else {
                System.out.println(cards.getTranslations().get(i));
            }
            String guess = scanner.nextLine();
            checkIsRight(guess, language, i);
        }
        menu();
    }

    private void checkIsRight(String guess, String language, int index) {
        if (cards.quiz(guess, language, index)) {
            System.out.println("YOU GOT IT");
        } else {
            System.out.println("WRONG");
        }

    }


    private void seeStats() {
        double numCorrect = cards.getNumCorrect();
        double num = cards.getNumTested();


        System.out.println("num correct: " + numCorrect);
        System.out.println("num tried: " + num);
        if (num > 0) {
            System.out.println("% of questions correct: " + (numCorrect / num) * 100 + "%");
        }
        System.out.println("All previous guesses per word:");
        for (FlashCard flashCard: cards.getAllCards()) {
            System.out.print(flashCard.getEnglishWord() + ": ");
            for (String guess: flashCard.getPastGuesses()) {
                System.out.print(guess + ", ");
            }
            System.out.println();
        }

        menu();
    }



}

