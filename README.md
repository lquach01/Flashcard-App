
# Language Learning Flashcards

### What will the application do?

The application will allow users to add a **"flashcard"** to their **"deck of flashcards."**

A flashcard, which will be a non-trivial class, will store the English word and the word in the other language. 
Additionally, it will store what kind of word it is/what part of speech it belongs to (ex. a verb, adjective, noun, 
preposition, etc.). For example, if the person is trying to learn French, they could add a flashcard with the word *"dog"* as the English 
word, *"chien"* as the French translation, and that it is a noun.

My application will also store a list of flashcards, which is a non-trivial class that collects an arbitrary number of 
flashcards.

The application will quiz the user, either starting with the English word or the translated word. The application 
can also filter through the card to quiz the user only on 1 part of speech.
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
- See a list of all the English words in the deck of flashcards
- See a list of all the words in the other language in the deck of flashcards
- Test themselves, starting with the word in English OR the word in the other language
- Filter through the cards to only be tested based on the part of speech