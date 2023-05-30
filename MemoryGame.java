/**
 * Project 3.6.5
 *
 * The Memory Game shows a random sequence of "memory strings" in a variety of buttons.
 * After wathcing the memory strings appear in the buttons one at a time, the
 * player recreates the sequence from memory.
 */

import java.util.*;

public class MemoryGame
{
  public static void main(String[] args) {


    // Create the "memory strings" - an array of single character strings to 
    // show in the buttons, one element at a time. This is the sequence
    // the player will have to remember.
    ArrayList<String> options = new ArrayList<String>();
    options.add("A");
    options.add("B");
    options.add("C");
    options.add("D");
    options.add("P");

    // Create the game and gameboard. Configure a randomized board with 3 buttons.
    // (Later, you can change options to configure more or less buttons
    // and turn randomization on or off.)
    MemoryGameGUI gameGUI = new MemoryGameGUI();
    gameGUI.createBoard(3, false);
    //gameGUI.createBoard(5, true);
    //gameGUI.createBoard(1, false);

    boolean running = true;
    int score = 0;
    int rounds = 0;
    double difficulty = 2;
    // Play the game until user wants to quit.
    while (running)
    {
      // Create a new array that will contain the randomly ordered memory strings.
      String sequence[] = new String[(int) Math.floor(difficulty)];
      for (int i = 0; i < sequence.length; i++)
      {
        sequence[i] = options.get((int) (Math.random() * options.size()));
      }

      // Create a list of randomly ordered integers with no repeats, the length
      // of memory strings. Use it to create a random sequence of the memory strings.
      // - OR -
      // Overload the next method in RandomPermutation to create a random sequence 
      // of the memory strings, passed as a parameter.

      boolean incorrect = true;
      while (incorrect)
      {
        // Play one sequence, delaying half a second for the strings to show
        // in the buttons. Save the player's guess.
        // (Later, you can speed up or slow down the game.)
        // Determine if player's guess matches all elements of the random sequence.
        //Time is affected by difficulty
        String playerGuess = gameGUI.playSequence(sequence, 0.8 / Math.pow(difficulty, 0.5));

        // iterate to determine if guess matches sequence
        incorrect = false;
        if (playerGuess != null && sequence.length == playerGuess.length())
        {
          //Clean answer of lowercase and punctuation
          playerGuess = playerGuess.replaceAll("\\p{Punct}", "").toUpperCase();

          for (var i = 0; i < sequence.length; i++)
          {
            if (!sequence[i].equals(playerGuess.substring(i, i + 1)))
            {
              incorrect = true;
            }
          }
        } 
        else
        {
          incorrect = true;
        }
        if (incorrect)
        {
          gameGUI.tryAgain();
        }
        rounds++;
      } 
      // If match, increase score, and signal a match, otherwise, try again.
      score++;
      difficulty += 0.5;
      gameGUI.matched();
      gameGUI.showScore(score, rounds, difficulty);

    // Ask if user wants to play another round of the game 
    // track the number of games played.
      if (!gameGUI.playAgain())
      {
        running = false;
      }
    }
    // When done playing, show score and end the game.
    gameGUI.quit();
  }
}