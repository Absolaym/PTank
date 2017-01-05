package PTank.GameStates;

import java.util.ArrayList;

import PTank.Window;
import PTank.Ressources.Button;
import PTank.Ressources.Ressources;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;


public class Menu extends BasicGameState
{
	// --------------------------------------------
	// ------------- Attributes -------------------
	// --------------------------------------------
	private ArrayList<Button> buttons;

	// --------------------------------------------
	// -------------- Methods ---------------------
	// --------------------------------------------
	@Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
    {
    	buttons = new ArrayList<Button>();
		
    	buttons.add(Ressources.getButton("Play"));
    	buttons.add(Ressources.getButton("Level Editor"));
    	buttons.add(Ressources.getButton("Exit"));
    }

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int dt) throws SlickException
	{
		// update buttons
		for (int i = 0; i < buttons.size(); i++)
		{
			buttons.get(i).update(gc, sbg, dt);
		}

		// si play clické, go in game
		if (Ressources.getButton("Play").isMouseClicked())
		{
			sbg.enterState(GameStates.IN_GAME, new FadeOutTransition(), new FadeInTransition());
		}
		// si level editor clické, go MENU_LEVEL_EDITOR
		if (Ressources.getButton("Level Editor").isMouseClicked())
		{
			sbg.enterState(GameStates.MENU_LEVEL_EDITOR, new FadeOutTransition(), new FadeInTransition());
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		// render title
    	g.drawString("PTank", (Window.HEIGHT / 2) - 40,  100);

		// render buttons
		for (int i = 0; i < buttons.size(); i++)
		{
			buttons.get(i).render(gc, sbg, g);
		}
	}

	@Override
	public int getID() { return GameStates.MENU; }
}
