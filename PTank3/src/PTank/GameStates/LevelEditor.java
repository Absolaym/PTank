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



public class LevelEditor extends BasicGameState
{
	// --------------------------------------------
	// ------------- Attributes -------------------
	// --------------------------------------------
	private final static String NEW_MAP_PATH = new String("maps/default.txt");
	public static String mapPath;
	public static boolean isNewMap = true;

	private Map map;

	private String filePath;

	// --------------------------------------------
	// -------------- Methods ---------------------
	// --------------------------------------------	
	@Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
    {
		if (isNewMap)
		{
			map = new Map("maps/defaults.txt");
		}
		else
		{
			map = new Map(this.filePath);
		}
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
	public int getID() { return GameStates.LEVEL_EDITOR; }
}
