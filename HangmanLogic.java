/**********************************************************************************************
 * Program: HangmanLogic.java
 * @author: Adam Cankaya, Nathalie Blume, Matt LaFollette
 * Date: due July 21, 2013
 * Description: Part of a hangman game simulator
 * Input: keyPhrase & numberOfGuesses.
 * Work: HangmanLogic defines objects that contain a hangman game's variables.
 * Output: HangmanLogic object containing 
 *  String: keyPhrase , ArrayList<Character>: guessedCharacters , int: numberOfGuesses , *  int: numberOfGuesssesLeft 
 * Reference: NA
 */
import java.util.ArrayList;

public class HangmanLogic 
{
	private String keyPhrase;
	private int numberOfGuesses;
	private int numberOfGuessesLeft;
	private ArrayList<Character> guessedCharacters;
	
	/**
	 * Constructor creates this initial state
	 * @param newPhrase  the phrase to be used for the key phrase of the hangman game
	 * @param numGuesses  the number of guesses allowed in the hangman game
	 */
	public HangmanLogic(String keyPhrase, int numberOfGuesses)
	{
		this.keyPhrase = keyPhrase;		
		this.numberOfGuesses = numberOfGuesses;
		this.numberOfGuessesLeft = numberOfGuesses;
		guessedCharacters = new ArrayList<Character>();
	}	
	
	/**
	 * Accessor for the keyPhrase variable
	 * @return  returns the keyPhrase field of this object
	 */
	public String getKeyPhrase()
	{
		return keyPhrase;
	}
	
	/**
	 * Method joins into a string (1) known keyPhrase letters and (2) dashes where letter is unknown
	 * @return  String: encodedKeyPhrase
	 */
	public String getKnownKeyPhrase()
	{
		char[] encodedKeyPhrase = new char[keyPhrase.length()];
		String lowerCaseKeyPhrase = keyPhrase.toLowerCase();
		
		for(int i = 0; i < keyPhrase.length(); i++)
		{
			char cur = lowerCaseKeyPhrase.charAt(i);
			if(guessedCharacters.contains(cur))
				encodedKeyPhrase[i] = cur;
			else
				encodedKeyPhrase[i] = '-';
		}
		
		return (new String(encodedKeyPhrase) );
	}
	
	/**
	 * Accessor method for the numberOfGuesses field
	 * @return  returns the numberOfGuesses field of this object
	 */
	public int getNumberOfGuesses()
	{
		return numberOfGuesses;
	}
	
	/**
	 * Accessor method for the numberOfGuessesLeft field
	 * @return  returns the numberOfGuessesLeft field of this object
	 */
	public int getNumGuessesLeft()
	{
		return numberOfGuessesLeft;
	}
	
	/**
	 * This method returns whether a specified character exists in the keyPhrase field
	 * @param guess =  a character being checked for in the keyPhrase field
	 * @return boolean = whether or not a specified character exists in the keyPhrase field
	 * @throws InvalidInputException  if a non-valid character is passed as input to this method
	 * @throws AlreadyGuessedException  if a valid character which has already been guessed is passed as input to this method
	 */
	public boolean guessCharacter(char guess) throws InvalidInputException, AlreadyGuessedException
	{
		if(isValidCharacter(guess))
		{
			//force into one case
			guess = Character.toLowerCase(guess);
			
			if(guessedCharacters.contains(guess) )
			{
				throw new AlreadyGuessedException("" + guess);
			}
			else
			{
				guessedCharacters.add(guess);
				if(keyPhrase.contains("" + guess))
					return true;
				else
				{
					numberOfGuessesLeft--;
					return false;
				}
			}		
		}
		else
		{
			throw new InvalidInputException("" + guess);
		}
	}	
	
	/**
	 * Returns whether the guessing user is out of guesses in this game of hangman
	 * @return  Returns whether the guessing user is out of guesses in this game of hangman
	 */
	public boolean isGameOver()
	{
		boolean guessed = (getKnownKeyPhrase().equals(keyPhrase.toLowerCase())); // true if all letters were guessed
		return( numberOfGuessesLeft == 0 || guessed);		// true if either all letters were guessed or if guesses have run out
	}
	
	/**
	 * Returns whether the character parameter is considered valid in this game
	 * @param guess  the character being checked for validity
	 * @return  returns whether the character parameter is considered valid in this game
	 */
	private boolean isValidCharacter(char guess)
	{
		return Character.isLetter(guess);
	}
}
