package PTank.Ressources;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.*;
import org.newdawn.slick.state.StateBasedGame;


import java.awt.Font;

public class Button 
{
	// --------------------------------------------
	// ------------- Attributes -------------------
	// --------------------------------------------
	private final static boolean ANTI_ALIASING = false;
	private final static int ROUND = 10;
	
	private String text;
	private Shape shape;
	private TrueTypeFont font;
	private Color textColor;
	private Color buttonColor;
	private Color buttonColorMouseOvered;
	
	private boolean mouseOvered = false;
	private boolean mouseClicked = false;

	// --------------------------------------------
	// -------------- Methods ---------------------
	// --------------------------------------------
	// constructor 1 : text, x, y, width, height (+default : Times new roman, black, white, yellow)
	public Button(String text, float x, float y, float width, float height)
	{
		this.text = text;
		this.shape = new Rectangle(x, y, width, height);
		this.font = new TrueTypeFont(new Font("Times New Roman", Font.BOLD, 24), ANTI_ALIASING);
		this.textColor = Color.black;
		this.buttonColor = Color.white;
		this.buttonColorMouseOvered = Color.yellow;

	}
	// constructor 2 : text, x, y, width, height, (java)font, textColor, buttonColor
	public Button(String text, float x, float y, float width, float height,
			      Font font, Color textColor, Color buttonColor, Color buttonColorMouseOvered)
	{
		this(text, x, y, width, height);
		this.text = text;
		this.shape = new Rectangle(x, y, width, height);
		this.font = new TrueTypeFont(font, ANTI_ALIASING);
		this.textColor = textColor;
		this.buttonColor = buttonColor;
		this.buttonColorMouseOvered = buttonColorMouseOvered;
	}

	
	public void update(GameContainer gc, StateBasedGame sbg, int dt) throws SlickException
	{
		// We get mouse position (mouseX, mouseY)
		float mouseX =  gc.getInput().getMouseX();
		float mouseY =  gc.getInput().getMouseY();
		
		// update mouseOvered and mouseClicked
    	if (this.shape.contains(mouseX, mouseY))
    	{
    		mouseOvered = true;
    	}
    	else
    	{
    		mouseOvered = false;
    	}
    	if (mouseOvered && gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
    	{
    		mouseClicked = true;
    	}
    	else
    	{
    		mouseClicked = false;
    	}
	}

	public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException
	{
		// draw shape
		if (this.isMouseOvered()) {	g.setColor(buttonColorMouseOvered); }
		else                      { g.setColor(buttonColor);	        }
		g.fillRoundRect(shape.getX(), shape.getY(), shape.getWidth(),shape.getHeight(), ROUND);
	
		// draw text, with font, at the center or the shape
		g.setColor(textColor);
		g.setFont(font);
		float textCenteredX = shape.getCenterX() - (font.getWidth(text) / 2);
		float textCenteredY = shape.getCenterY() - (font.getHeight() / 2);
		g.drawString(text, textCenteredX, textCenteredY);
	}
	
	public boolean isMouseOvered() { return mouseOvered; }	
	public boolean isMouseClicked() { return mouseClicked; }
	
}
