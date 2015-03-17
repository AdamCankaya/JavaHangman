
//import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
//import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JFrame;

/**********************************************************************************************
 * Program: HangmanDriver.java
 * @author: Adam Cankaya, Nathalie Blume, Matt LaFollette
 * Date: due July 21, 2013
 * Description: A hangman game simulator
 * Input: The user enters whether the player is human or AI, and if AI, which strategy the AI uses.
 * Work: The HangmanDriver creates a HangmanLogic object that contains the game's variable, then 
 *  creates (and passes the HangmanLogic object to) a HangmanFrame object that runs the game in a 
 *  GUI environment.
 * Output: None except confirmations printed to console. 
 * Reference: NA
 **********************************************************************************************/

public class HangmanDriver
{	
	private static Scanner in = new Scanner(System.in);

	public static void main(String[] args) throws IOException 
	{		
		// variable declaration
		char player; //Will contain letter ID'ing player type: either 'r','s','c', or 'h', representing random, systematic, or clever respectively (for AI),  or if the guesser will be a human
		String input, keyPhrase; // input = user entry, keyPhrase = word to be guessed
		int initialGuesses = 10; // initialGuesses = number of guesses player is allowed
		HangmanLogic driverGame; // driverGame = variables that change with each trial in the game

		/* In the next several blocks of code, get lists of letters and words in play as well as ID player type from either: 
		 *  - the command line arguments, 
		 *  - the console together with pre-coded options
		 */
		
		File fileOfLetters; //Will contain text imported from a text file
		File fileOfWords; //Will contain text imported from a text file
		ArrayList<String> stringListOfLetters = new ArrayList<String>(); //Generic ArrayList to Store only String objects
		ArrayList<String> stringListOfWords = new ArrayList<String>(); //Generic ArrayList to Store only String objects

		if (args.length == 3) //if there are 3 command line arguments in the configuration field, get those args 
		{ 
			String lettersFile = args[0]; // variable contains path to a text file with the letters of alphabet in it
			fileOfLetters = new File(lettersFile); //letters file
			String wordsFile = args[1]; // variable contains path to a text file with the words in it
			fileOfWords = new File(wordsFile); //contents of word file
			player = args[2].charAt(0);	// variable IDs player type		
		}
		else //if insufficient number of args, then get the information elsewhere
		{
			System.out.println("You have entered fewer than three arguments. The first arg is the letters file," +
					"the second arg is the words file and the third arg is the player type. But here, let's try a different" +
					"way to enter in the args:");
			
			fileOfLetters = new File("letters.txt"); 			
			fileOfWords = new File("wordsInstructor.txt");
//			fileOfLetters = new File("C:\\Users\\Programmer\\Desktop\\OSU-School\\OSU\\2013_Q3S_CS162\\workspace\\A2c\\letters.txt"); 			
//			fileOfWords = new File("C:\\Users\\Programmer\\Desktop\\OSU-School\\OSU\\2013_Q3S_CS162\\workspace\\A2c\\letters.txt");
			
			System.out.print("Is the player human (h) or an artifical intelligence, \n" +
					"and if an AI, does it guess randomly (r), systematically (s) or cleverly (c)? \n>> ");
			input = in.nextLine();
			player = input.toLowerCase().charAt(0); // cast input to lower case and extract inly 1st character
			while(!(player == 'h' || player == 'r' || player == 's' || player == 'c'))
			{
				System.out.print("Please only enter h, r, s or c. Try again: \n>> ");  
				input = in.nextLine();
				player = input.toLowerCase().charAt(0); // cast input to lower case and extract inly 1st character
			} 
			System.out.println("Player is: " + player);
		}

		try { // create stringList that contains the contents of letters file
			Scanner scanner = new Scanner(fileOfLetters);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				stringListOfLetters.add(line); 
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
			
		try { // create stringList that contains the contents of words file
			Scanner scanner = new Scanner(fileOfWords);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				stringListOfWords.add(line); 
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
			
		 /* Pick a word at random from word list. Note that in this game, 
		 * the human is never asked to pick a word for an AI to guess. 
		 */		 
		int min = 0; //define one end...
		int max = stringListOfWords.size();//...and the other of the range of indices in the array
		int randIndex = min + (int)(Math.random() * ((max - min) + 1)); //a random index number
		keyPhrase = stringListOfWords.get(randIndex);
  
		/* Generate the variables that concern the keyPhrase
		 */
		driverGame = new HangmanLogic(keyPhrase, initialGuesses);

		/*Using the object & method corresponding to player type: 
		 * (1) Generate GUI environment and (2) call method that will play the game from within the frame class
		 */
		if (player == 'h') {
			JFrame driverFrame = new HangmanFrameChildHuman(driverGame, stringListOfLetters); 
			driverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			driverFrame.setVisible(true);
			((HangmanFrameChildHuman) driverFrame).playGame(); 
		}
		else if (player == 'r') { 
			JFrame driverFrame = new HangmanFrameParent(driverGame, stringListOfLetters);//[AI]
			driverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			driverFrame.setVisible(true);
			((HangmanFrameParent) driverFrame).playGame();
		}
		else if (player == 's') // AI picks letters based on frequency
		{
			JFrame driverFrame = new HangmanFrameChildAIFrequent(driverGame, stringListOfLetters);//[AI]
			driverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			driverFrame.setVisible(true);
			((HangmanFrameChildAIFrequent) driverFrame).playGame();
		}
		else if (player == 'c') // AI picks letters based on a combination of strategies that improves success
		{
			JFrame driverFrame = new HangmanFrameChildAIClever(driverGame, stringListOfLetters);//[AI]
			driverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			driverFrame.setVisible(true);
			((HangmanFrameChildAIClever) driverFrame).playGame();
		}
		else if (player == 't') // test case
		{
			JFrame driverFrame = new HangmanFrameChildAIClever(driverGame, stringListOfLetters);//[AI]
			driverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			driverFrame.setVisible(true);
			((HangmanFrameChildAIClever) driverFrame).playGame();
		}
		else
			System.out.println("Not a valid option.");
	}
}


