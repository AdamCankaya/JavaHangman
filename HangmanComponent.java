import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;

/**********************************************************************************************
 * Program: HangmanDriver.java
 * @author: Adam Cankaya, Nathalie Blume, Matt LaFollette
 * Date: due July 21, 2013
 * Description: Part of a hangman game simulator
 * Input: NA
 * Work: Controls hangman graphic
 * Output: None 
 * Reference: NA
 * 
 **********************************************************************************************/

@SuppressWarnings("serial")
public class HangmanComponent extends JComponent {

	int progress = 10;
	
	public void paintComponent(Graphics g) {
		
		//System.out.println(progress);
		
		Graphics2D g2 = (Graphics2D) g;
		int x = getWidth()/2;
		int y = getHeight()/2;
		Hangman myHangman = new Hangman(x,y,progress);//EDIT
		myHangman.draw(g2);
	}
	
	public void badGuessManager ()
	{
		progress--;
	}
	//updates the variable progress so the paint function knows how many pieces of the hangman to draw
}