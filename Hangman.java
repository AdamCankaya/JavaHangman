import java.awt.Graphics2D; 
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D; 
import java.awt.geom.Point2D; 


/**********************************************************************************************
 * Program: Hangman.java
 * @author: Adam Cankaya, Nathalie Blume, Matt LaFollette
 * Date: due July 21, 2013
 * Description: Part of a hangman game simulator between one human player and one computer player
 * Input: int x, int y, int progress -- coordinates for starting point & variable for number of elements to draw
 * Work: Draws a hanged man
 * Output: None, other than a graphic in a GUI
 * Reference: NA
 * 
 **********************************************************************************************/

public class Hangman {
	
	private int xLeft;
	private int yTop;
	private int numElements;
	
	/**
	 * Draws a hangman at top left corner
	 * @param x The x coordinate of the top left corner
	 * @param y The y coordinate of the top left corner
	 */
	public Hangman(int x, int y, int progress) {
		xLeft = x;
		yTop = y;
		numElements = progress;
	}
	
	/**
	 * Draws the hanging man.
	 * @param g2 the graphics context
	 */
	public void draw(Graphics2D g2) {
		
		//System.out.println("draw");
		
		//Define the points of departure
		Point2D.Double base1 = new Point2D.Double(xLeft-50, yTop + 100); // leftmost point of the base of the scaffold
		Point2D.Double base2 = new Point2D.Double(xLeft + 50, yTop + 100); // rightmost point of the base of the scaffold
		Point2D.Double rise1 = new Point2D.Double(xLeft + 30, yTop + 100); // bottom-most point of the rise of the scaffold
		Point2D.Double rise2 = new Point2D.Double(xLeft + 30, yTop-50); // topmost point of the rise of the scaffold
		Point2D.Double diag1 = new Point2D.Double(xLeft, yTop-50); // leftmost point of the diagonal support of the scaffold
		Point2D.Double diag2 = new Point2D.Double(xLeft + 30, yTop); // bottom-most point of the diagonal support of the scaffold
		Point2D.Double canopy1 = rise2; // rightmost point of the canopy of the scaffold; also topmost point of the rope
		Point2D.Double canopy2 = new Point2D.Double(xLeft - 20, yTop-50); // leftmost point of the canopy of the scaffold; also topmost point of the rope
		Point2D.Double rope1 = canopy2; // leftmost point of the canopy of the scaffold; also topmost point of the rope
		Point2D.Double rope2 = new Point2D.Double(xLeft-20, yTop-20);; // leftmost point of the canopy of the scaffold; also topmost point of the rope
		//Point2D.Double headtop = rope2; // topmost point of the hanged man's head
		Point2D.Double torso1 = new Point2D.Double(xLeft - 20, yTop -10); // topmost point of the hanged man's torso
		Point2D.Double torso2 = new Point2D.Double(xLeft -20, yTop + 20); // bottom-most point of the hanged man's torso; also, top of legs
		Point2D.Double arms1 = new Point2D.Double(xLeft-35, yTop); // leftmost point of the hanged man's arms
		Point2D.Double arms2 = new Point2D.Double(xLeft-5, yTop); // leftmost point of the hanged man's arms
		Point2D.Double basin = torso2; // bottom-most point of the hanged man's torso; also, top of legs
		Point2D.Double leftlegsfoot = new Point2D.Double(xLeft-15, yTop + 45); // bottom-most point of the hanged man's left leg
		Point2D.Double rightlegsfoot = new Point2D.Double(xLeft, yTop + 40); // bottom-most point of the hanged man's right leg
		
		//Define the lines & ellipses  
		Line2D.Double base = new Line2D.Double(base1, base2);
		Line2D.Double rise = new Line2D.Double(rise1, rise2);
		Line2D.Double diag = new Line2D.Double(diag1, diag2);
		Line2D.Double canopy = new Line2D.Double(canopy1, canopy2);
		Line2D.Double rope = new Line2D.Double(rope1, rope2);
		Ellipse2D.Double head = new Ellipse2D.Double(xLeft-22, yTop-20, 10, 10);
		Line2D.Double torso = new Line2D.Double(torso1, torso2);
		Line2D.Double arms = new Line2D.Double(arms1, arms2);
		Line2D.Double leftleg = new Line2D.Double(basin, leftlegsfoot);
		Line2D.Double rightleg = new Line2D.Double(basin, rightlegsfoot);
		
		// not sure here TK		
		//int numElements = 3;
		if(numElements<10)//ie, temp = 9 or 1 less than perfect, ,which means a penalty of 1, ie a element of the hanged man
			g2.draw(base);
		if(numElements<9)
			g2.draw(rise);
		if(numElements<8)
        	g2.draw(diag);
		if(numElements<7)
			g2.draw(canopy);
		if(numElements<6)
			g2.draw(rope);
		if(numElements<5)
        	g2.draw(head);
		if(numElements<4)
        	g2.draw(torso);
		if(numElements<3)
        	g2.draw(arms);
		if(numElements<2)
        	g2.draw(leftleg);
		if(numElements<1)
        	g2.draw(rightleg);
	}
	
	public void badGuessManager()
	{
		numElements--;
	}
	// added function that lets the hangman component know to redraw the hangman because the user had a bad guess

}
