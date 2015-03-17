import java.util.ArrayList;
import java.util.Collections;

/**********************************************************************************************
 * Program: HangmanFrameAIClever
 * @author: Adam Cankaya, Nathalie Blume, Matt LaFollette
 * Date: due July 21, 2013
 * Description: Part of a hangman game simulator
 * Input: HangmanLogic: gameVariables
 * Work: A frame that interfaces with user of a GUI-based "hangman" game
 * Output: None. 
 * Reference: NA
 * 
 **********************************************************************************************/

@SuppressWarnings("serial")
public class HangmanFrameChildAIClever extends HangmanFrameParent {


	//constructor 
	public HangmanFrameChildAIClever(HangmanLogic gameVariables, ArrayList<String> listOfLetters) {

		super(gameVariables, listOfLetters);

		//most frequent letters in English
		String[] frequentLetters = {"e","t","a","o","i","n","s","r","h","d","l","u","c","m","f","y","w","g","p","b","v","k","x","q","j","z"};
		
		ArrayList<String> newListOfLetters = new ArrayList<String>();
		
		//loops to check if all frequent letters are part of list passed into method and adds them
		for(int i = 0; i < listOfLetters.size(); i++)
		{
			if(listOfLetters.contains(frequentLetters[i]))
				newListOfLetters.add(frequentLetters[i]);
		}
		
		//reassigns listOfLetters to new frequency order
		this.listOfLetters = newListOfLetters;
	}
	
	/* Method playGame()
	 * Tests whether game is over. 
	 * Calls createButton which does all the work. 
	 */
	@Override
	public void playGame()
	{			
		while(!gameVariables.isGameOver())
		{						
			int cleverCount = 5; // number of frequency based guesses before AI switches to random
			
			for (int i = 0; i < listOfLetters.size(); i++) 
			{
				AIinput = listOfLetters.get(i); //[Specific to AI]
				
				// checks for common word combinations 
				if(gameVariables.getKnownKeyPhrase().contains("E"))
				{
					if(listOfLetters.contains('R'))
						AIinput = "R";
				}
				else if(gameVariables.getKnownKeyPhrase().contains("Q"))
				{
					if(listOfLetters.contains('U'))
						AIinput = "U";
				}
				else if(gameVariables.getKnownKeyPhrase().contains("T"))
				{
					if(listOfLetters.contains('U'))
						AIinput = "H";
				}
				else if(gameVariables.getKnownKeyPhrase().contains("O") || gameVariables.getKnownKeyPhrase().contains("I"))
				{
					if(listOfLetters.contains('N'))
						AIinput = "N";
				}
				else if(gameVariables.getKnownKeyPhrase().contains("Q"))
				{
					if(listOfLetters.contains('U'))
						AIinput = "U";
				}
				else if(gameVariables.getKnownKeyPhrase().contains("C"))
				{
					if(listOfLetters.contains('O'))
						AIinput = "O";
				}
				else if(gameVariables.getKnownKeyPhrase().contains("Y"))
				{
					if(listOfLetters.contains('T'))
						AIinput = "T";
				}
				else if(gameVariables.getKnownKeyPhrase().contains("T"))
				{
					if(listOfLetters.contains('R'))
						AIinput = "R";
				}
						
				// checks if one letter is left and if so switches to random
				if(gameVariables.getKnownKeyPhrase().length() <= 1)
					Collections.shuffle(listOfLetters);
				
				// checks if the number of frequency based guesses is complete and if so switches to random
				if(cleverCount == 0)
					Collections.shuffle(listOfLetters);
				else
					cleverCount --;

				if (!gameVariables.isGameOver()) 
				{	

					input = null; //resets local var input so that no infinite loop

					while(input == null) 
					{
						createButton(AIinput); // this method does all of the work
					}
					
		       		try { //wait between turns if it's an AI, rather than wait for button click //[Specific to AI]
		    			Thread.sleep(2500);
		    		} catch (InterruptedException e1) {
		    			// TODO Auto-generated catch block
		    			e1.printStackTrace();
		    		}
				}				
			}
		}

		//Prepare to exit
		letterField.setEditable(false);
		wordLabel.setText("The correct word is : " + gameVariables.getKeyPhrase());
		if(gameVariables.getNumGuessesLeft() <= 0)
            feedbackLabel.setText("I am sorry, that was your last guess, and you have lost the game."); 
		else
            feedbackLabel.setText("Congratulations! You have won the game!"); 

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(0);		
	}
	
}