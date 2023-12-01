
# Language Learning Flashcards

### What will the application do?

The application will allow users to add a **"flashcard"** to their **"deck of flashcards."**

A flashcard, which will be a non-trivial class, will store the English word and the word in the other language. 
Additionally, it will store what kind of word it is/what part of speech it belongs to (ex. a verb, adjective, noun, 
preposition, etc.). For example, if the person is trying to learn French, they could add a flashcard with the word 
*"dog"* as the English word, *"chien"* as the French translation, and that it is a noun. It will also store the previous
guesses that the user has made for that card (whether the guess is correct or not) with NO duplicates.

My application will also store a deck of flashcards, called CardDeck, which is a non-trivial class that collects an 
arbitrary number of flashcards. CardDeck contains a list of all the cards the user wants to store. Additionally, it will 
store the amount of words tested, as well as the number of questions the user has gotten right.

The application will quiz the user, either starting with the English word or the translated word. The application 
can also filter through the card to quiz the user on the specified part of speech.
To filter through the cards, the application will loop through the cards and filter out the cards that do not belong to 
the same part of speech specified.

### Who will use it?

People who are learning a new language would find it very useful. Active recall has been proven to be a very 
effective learning technique and flashcards are a great way to practice active recall.

### Why is this project of interest to you?

I am trying to learn Swedish and have a hard time trying to memorize certain words.  

### User Stories
As a user, I want to:
- Add flashcards to the list of flashcards, specifying the English word, translated word, and part of speech
- Test themselves, being prompted with the word in English OR the word in the other language
- Filter through the cards to only be tested based on the part of speech
- See the statistics of quizzing, including past guesses and the percentage of questions the user has gotten right
- See all English words in the deck of flashcards
- When I open the app, I want to be given the option to load a flashcard deck from file OR make a new one
- When I close the app, I want to be given the option to save the flashcard deck


### Instructions for Grader
- You can generate the first required action related to the user story "adding multiple Xs to a Y" by selecting "Add a 
new flashcard" under "Flashcards" in the menu-bar
- You can generate the second required action related to the user story "adding multiple Xs to a Y" by selecting "See 
all cards" under "Flashcards" in the menu-bar
- You can locate my visual component by pressing "Save" in the menu-bar. If the file saves without throwing an error 
(which it should not throw), a thumbs up image will be displayed.
- You can save the state of my application by pressing "Save" in the menu-bar
- You can reload the state of my application by pressing the "Load old cards" button when the application is run


### Phase 4: Task 2
Tue Nov 28 23:04:45 PST 2023
The card for 'dog' was added to the deck of cards.
Tue Nov 28 23:04:47 PST 2023
All cards in deck of cards were displayed.
Tue Nov 28 23:04:58 PST 2023
Cards were filtered by noun.
Tue Nov 28 23:04:59 PST 2023
Quizzed on English words.
Tue Nov 28 23:05:02 2023
Quizzed on translations.