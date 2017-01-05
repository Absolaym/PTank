package PTank.GameStates;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import PTank.Entities.Tanks.Player;
import PTank.Map.Map;
import PTank.Ressources.Ressources;
import PTank.Scene.Scene;



public class InGame extends BasicGameState
{
	// --------------------------------------------
	// ------------- Attributes -------------------
	// --------------------------------------------
	private int lives;
	private int level;
	private Map map;
	
	private Player me;
	
	
	// --------------------------------------------
	// -------------- Methods ---------------------
	// --------------------------------------------
	@Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
    {
		map = new Map("maps/map1.txt");
		map.init();
    }

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int dt) throws SlickException
	{
		map.update(gc, dt);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		map.render(gc, g);
	}

	@Override
	public int getID() { return GameStates.IN_GAME; }
}
