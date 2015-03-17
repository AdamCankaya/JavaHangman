import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**********************************************************************************************
 * Program: Parent
 * 
 **********************************************************************************************/

@SuppressWarnings("serial")
public class HangmanFrameParent extends JFrame {

	protected static final int FRAME_WIDTH = 1000;
	protected static final int FRAME_HEIGHT = 400; 
	
	protected static String input;
	protected static String AIinput; //[Specific to AI]
	protected static char guess;
	
	protected HangmanLogic gameVariables;
	protected ArrayList<String> listOfLetters;
	protected JTextField letterField;
	protected JButton submitButton;
	protected JLabel wordLabel;
	protected JLabel guessesRemainingLabel;
	protected JLabel feedbackLabel;
	protected HangmanComponent component;
	protected JPanel gamePanel; 
	protected JPanel guessPanel; //field for entering a letter and submitting this as a guess; bottom
	protected JPanel feedbackPanel; //special feedback messages 

	// file names of sounds
	String clickSound = "click.wav";	// mouse click sound
	String wrongSound = "wrong.wav";	// wrong letter sound
	String rightSound = "right.wav";	// right letter sound
	String winSound = "win.wav";		// game over winner sound
	
	//constructor 
	public HangmanFrameParent(HangmanLogic gameVariables, ArrayList<String> listOfLetters) {

		//set variables
		this.gameVariables = gameVariables; //object of type HangmanLogic that was created by the driver gets passed here.
		Collections.shuffle(listOfLetters);
		this.listOfLetters = listOfLetters;
		
		//use helper methods to create GUI components & attach them to frame
		createLabelField();
		createTextField();
		createButton(" "); //[Specific to AI]
		createHangingMan();
		createPanel();

		//set frame size & title
		setSize(FRAME_WIDTH, FRAME_HEIGHT);	
		setTitle("Hangman Game");
	}
	
	/* Method playGame()
	 * Tests whether game is over. 
	 * Calls createButton which does all the work. 
	 */
	public void playGame()
	{			
		while(!gameVariables.isGameOver())
		{						
			for (int i = 0; i < listOfLetters.size(); i++) 
			{
				AIinput = listOfLetters.get(i); //[Specific to AI]

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
		wordLabel.setForeground (Color.blue);
		wordLabel.setText("The correct word is : " + gameVariables.getKeyPhrase());
		if(gameVariables.getNumGuessesLeft() <= 0)
		{
			feedbackLabel.setForeground (Color.red);
            feedbackLabel.setText("I am sorry, that was your last guess, and you have lost the game."); 
		}
		else
		{
			feedbackLabel.setForeground (Color.green);
            feedbackLabel.setText("Congratulations! You have won the game!"); 
            
            // opens an input stream to sound files
			try	{
				InputStream in = new FileInputStream(winSound);
				AudioStream as = new AudioStream(in);
				AudioPlayer.player.start(as);
			} catch (FileNotFoundException e) {
				e.printStackTrace(); 
			} catch (IOException e) {
				e.printStackTrace(); 
			} 
            
		}
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(0);		//TK THIS WASN"T IN ADAM" CODE
				
	}
	
	
	/* Method createLabelField()
	 * Generates JLabels for all the text printed to the GUI 
	 * Calls createButton which does all the work. 
	 */
	public void createLabelField() {
		feedbackLabel = new JLabel("Welcome to hangman! The word you are trying to guess is shown below, with dashes " +
				"representing unknown charaters. The word is " + gameVariables.getKeyPhrase().length() + " characters long");	
		wordLabel = new JLabel("The word so far: " + gameVariables.getKnownKeyPhrase() + "    ");
		wordLabel.setForeground(Color.blue);
		guessesRemainingLabel = new JLabel("Guesses remaining: " + gameVariables.getNumGuessesLeft() + "    ");
	}

	/* Method createTextField()
	 * Generates field where a letter will be entered. 
	 */
	public void createTextField() {
		final int FIELD_WIDTH = 1;
		letterField = new JTextField(FIELD_WIDTH);
		letterField.setText("");
	}

	/* Method createButton()
	 */
	@SuppressWarnings("deprecation")
	protected void createButton(String AIinputvar) { //[Specific to AI]

		submitButton = new JButton("Submit Letter"); //The "submit" button is not used in the AI version. It's there for show.
		
		feedbackLabel.setText("Keep going!"); //reset feedback in case it displayed an error on last turn

        submitButton.setEnabled(false);
                
		letterField.setText(AIinputvar); //AI puts a guess into the letter field //[Specific to AI]
		input = letterField.getText();  //get input	
		guess = input.charAt(0); //guess has now been confirmed at only one character	
		
		try //now for the meaningful part of checking the input character
		{
			boolean goodGuess = gameVariables.guessCharacter(guess);
			if (goodGuess == false)
			{
				component.badGuessManager(); //updates the component every time there is a bad guess
				component.repaint();//repaints the component part of the frame
			}
		}				
		catch (InvalidInputException e)
		{
            feedbackLabel.setText("The guess " + e.getMessage() + " is invalid, you are only allowed to guess letters in my version of this game, try again");
		}
		catch (AlreadyGuessedException e)
		{
            feedbackLabel.setText("You have already guessed " + e.getMessage() + ", try again");
		}
		guessesRemainingLabel.setText("Guesses remaining: " + gameVariables.getNumGuessesLeft() + "    "); //updates the remaining guesses on the GUI 
        wordLabel.setForeground (Color.blue);
		wordLabel.setText("The word so far: " + gameVariables.getKnownKeyPhrase() + "    ");              
   }		
	
	/* Method createHangingMan()
	 * Creates object of type HangmanComponent 
	 */
	public void createHangingMan() {
		component = new HangmanComponent();	
	}

	/* Method createPanel()
	 * Creates The panel that holds the user-interface components
	 */
	public void createPanel() { 
		gamePanel = new JPanel();
		guessPanel = new JPanel();
		feedbackPanel = new JPanel();

		gamePanel.setLayout(new BorderLayout());

		guessPanel.add(wordLabel);
		guessPanel.add(guessesRemainingLabel);
		guessPanel.add(letterField);
		guessPanel.add(submitButton);
		feedbackPanel.add(feedbackLabel);

		gamePanel.add(feedbackPanel, BorderLayout.NORTH);
		gamePanel.add(component, BorderLayout.CENTER);
		gamePanel.add(guessPanel, BorderLayout.SOUTH);
		add(gamePanel);	
	}

}