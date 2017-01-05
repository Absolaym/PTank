package PTank.GameStates;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import PTank.Ressources.Ressources;

public class TitleScreen extends BasicGameState
{
	// --------------------------------------------
	// ------------- Attributes -------------------
	// --------------------------------------------
	//
	// --------------------------------------------
	// -------------- Methods ---------------------
	// --------------------------------------------
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException
	{
		Ressources.loadAll();
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int dt) throws SlickException
	{
		if (gc.getInput().isKeyPressed(Input.KEY_ENTER))
		{
			sbg.enterState(GameStates.MENU, new FadeOutTransition(), new FadeInTransition());
		}
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{
		g.drawString("TITLE SCREEN", 500, 500);
		g.drawString("PRESS ENTER TO START", 500, 700);
	}

	@Override
	public int getID() 
	{
		return GameStates.TITLE_SCREEN;
	}

}
