import java.util.ArrayList;

/**********************************************************************************************
 * Program: Child
 * 
 **********************************************************************************************/

@SuppressWarnings("serial")
public class HangmanFrameChildAIFrequent extends HangmanFrameParent {

	// file names of sounds
	String clickSound = "click.wav";	// mouse click sound
	String wrongSound = "wrong.wav";	// wrong letter sound
	String rightSound = "right.wav";	// right letter sound
	String winSound = "win.wav";		// game over winner sound
	
	//constructor 
	public HangmanFrameChildAIFrequent(HangmanLogic gameVariables, ArrayList<String> listOfLetters) {

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
		
		System.out.println("The added code wa executed & the oreder is: e t a o i n s r h d");
	}	
}