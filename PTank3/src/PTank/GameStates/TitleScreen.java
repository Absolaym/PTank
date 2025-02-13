package PTank.GameStates;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import PTank.Entities.Tanks.Player;
import PTank.Map.Map;
import PTank.Ressources.Ressources;

public class TitleScreen extends BasicGameState
{
	// --------------------------------------------
	// ------------- Attributes -------------------
	// --------------------------------------------
	//
	
	private Map map;
	private Player title;
	
	// --------------------------------------------
	// -------------- Methods ---------------------
	// --------------------------------------------
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException
	{
		Ressources.loadAll();
		map = new Map("maps/WELCOME.txt");
		map.init();
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int dt) throws SlickException
	{
		map.update(gc, dt);
		if (gc.getInput().isKeyPressed(Input.KEY_ENTER))
		{
			sbg.enterState(GameStates.MENU, new FadeOutTransition(), new FadeInTransition());
		}
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{
		map.render(gc, g);
		g.drawString("PRESS ENTER TO START", 500, 700);
	}

	@Override
	public int getID() 
	{
		return GameStates.TITLE_SCREEN;
	}

}
