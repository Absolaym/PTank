package PTank;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class MenuState extends BasicGameState
{
	// attrbutes
	private ArrayList<Rectangle> buttons;
	private final static int BUTTON_HEIGHT = 300;
	private final static int BUTTON_WIDTH = 100;
	
	// methods
    public void init(GameContainer container, StateBasedGame arg1) throws SlickException 
    {
    	buttons = new ArrayList<Rectangle>();
		
    	Rectangle jouerRectangle = new Rectangle(0,
    										     300,
    										     BUTTON_HEIGHT,
    										     BUTTON_WIDTH);

    	buttons.add(jouerRectangle);
    	
    	Rectangle quitterRectangle = new Rectangle(0,
												   500,
												   BUTTON_HEIGHT,
												   BUTTON_WIDTH);
    	buttons.add(quitterRectangle);
    	

    	
    	for (Rectangle rectangle : buttons)
    	{
    		rectangle.setCenterX(container.getScreenHeight() / 2);
    	}

    }

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int dt) throws SlickException
	{
		float mouseX =  container.getInput().getMouseX();
		float mouseY =  container.getInput().getMouseY();
		
		if (container.getInput().isKeyPressed(Input.KEY_SPACE))
		{
			sbg.enterState(2, new FadeOutTransition(), new FadeInTransition());
		}
		
    	if (    buttons.get(0).contains(mouseX, mouseY)
    	     && container.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) )
    	{
			sbg.enterState(2, new FadeOutTransition(), new FadeInTransition());
    	}
    		
	}

	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException
	{
		
		float screenWidth = container.getScreenWidth();
		float screenHeight = container.getScreenHeight();
		
    	g.drawString("PTank", (screenHeight / 2) - 40,  100);

    	// afficher les boutons en blanc, et en jaune s'ils sont target par la souris
    	for (Rectangle button : buttons)
    	{
        	if (button.contains (container.getInput().getMouseX(),
                              container.getInput().getMouseY()) )
        	{
        		g.setColor(Color.yellow);
        		
        	}
    		g.fill(button);
    		g.setColor(Color.white);
    	}
    	
    	g.setColor(Color.black);
    	g.drawString("Jouer", buttons.get(0).getCenterX() - 50, buttons.get(0).getCenterY() - 10);
    	g.drawString("Quitter", buttons.get(1).getCenterX() - 50, buttons.get(1).getCenterY() - 10);
    	g.setColor(Color.white);
    	
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 1;
	}
}
