import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import sun.audio.*;
import java.io.*;
import java.io.IOException;

/**********************************************************************************************
 * Program: HangmanDriver.java
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
public class HangmanFrameChildHuman extends HangmanFrameParent {
	
	//constructor 
	public HangmanFrameChildHuman(HangmanLogic gameVariables, ArrayList<String> listOfLetters) {

		super(gameVariables, listOfLetters);	
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
			input = null; //resets local var input so that no infinite loop
			while(input == null) 
			{
				createButton(" "); // this method does all of the work  TK does inserting " " harm things?
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
		System.exit(0);		
	}
	
	/* Method createButton()
	 * Does... 
	 * CHANGE?
	 */
	@Override
	protected void createButton(String AIinputvar) {

		submitButton = new JButton("Submit Letter"); //The "submit" button is clicked after user enters a letter to test against word
        submitButton.setBackground(Color.white);  //emphasize button by making it a different color

		class ClickListener implements ActionListener  //Click listener is an inner class; collects "submit" button click
		{
			public void actionPerformed(ActionEvent event) 
			{
                feedbackLabel.setText("Keep going!"); //reset feedback in case it displayed an error on last turn

				input = letterField.getText();  //get input

				while(input.length() > 1) //while input is longer than one character, ask for new input with only a single character as input
				{
	                feedbackLabel.setText("That is more than one character, please input only a single character.");
					input = letterField.getText(); //get input	
				} 

				guess = input.charAt(0); //guess has now been confirmed at only one character	
				

				// opens an input stream to sound files
				try	{
					InputStream in = new FileInputStream(clickSound);
					AudioStream as = new AudioStream(in);
					AudioPlayer.player.start(as);
				} catch (FileNotFoundException e) {
					e.printStackTrace(); 
				} catch (IOException e) {
					e.printStackTrace(); 
				} 
				
				try //now for the meaningful part of checking the input character
				{
					boolean goodGuess = gameVariables.guessCharacter(guess);
					//System.out.println(goodGuess);
					//gameVariables.guessCharacter(guess);
					if (goodGuess == false)
					{		
						component.badGuessManager(); //updates the component every time there is a bad guess
						component.repaint();//repaints the component part of the frame
						// opens an input stream to sound files
						try	{
							InputStream in = new FileInputStream(wrongSound);
							AudioStream as = new AudioStream(in);
							AudioPlayer.player.start(as);
						} catch (FileNotFoundException e) {
							e.printStackTrace(); 
						} catch (IOException e) {
							e.printStackTrace(); 
						} 
					}
					// opens an input stream to sound files
					else
					{
						try	{
							InputStream in = new FileInputStream(rightSound);
							AudioStream as = new AudioStream(in);
							AudioPlayer.player.start(as);
						} catch (FileNotFoundException e) {
							e.printStackTrace(); 
						} catch (IOException e) {
							e.printStackTrace(); 
						} 
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
                
                //frame.add(new JLabel("<html>Text color: <font color='red'>red</font></html>"));
                //wordLabel.setText("The word so far: <html><font color='red'>" + gameVariables.getKnownKeyPhrase() + "    </font></html>");
            }		
		} 
		
	ActionListener listener = new ClickListener();
	submitButton.addActionListener(listener);	
	}		
}
