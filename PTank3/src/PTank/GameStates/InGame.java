package PTank.GameStates;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import PTank.Entities.Tanks.Ennemy;
import PTank.Entities.Tanks.Player;
import PTank.Map.Map;
import PTank.Ressources.Data;
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
	private boolean playerLoaded;
	private float x, y;
	
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
		if(!playerLoaded) {
			System.out.println(" " + (int) map.getPlayer().getCenterX() + " " + (int) map.getPlayer().getCenterY());
			int x = (int) map.getPlayer().getCenterX();
			int y = (int) map.getPlayer().getCenterY();
			
			map.getPlayer().setSkin(Data.getSkin(x, y, 32, 32));;
			playerLoaded = true;
		}
		
		map.update(gc, dt);
		
		
		// GAMEOVER
		if (map.getPlayer().isAlive() == false)
		{
			Sound dead = new Sound("ressources/musique/DEAD.WAV");
	    	dead.play();
	    	while(dead.playing()){
	    		
	    	}
			map.init();
			sbg.enterState(GameStates.MENU, new FadeOutTransition(), new FadeInTransition());
		}
		
		// WIN
		boolean isWon = true;
		Ennemy[] ennemies = map.getEnnemies();
		int nbEnnemies = map.getNbEnnemies();
		for (int i = 0; i < nbEnnemies; i++)
		{
			if (ennemies[i].isAlive())
			{
				isWon = false;
			}
		}
		if (isWon)
		{
			
			map.init();
			sbg.enterState(GameStates.MENU, new FadeOutTransition(), new FadeInTransition());
		}

	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		map.render(gc, g);
	}
	
	

	@Override
	public int getID() { return GameStates.IN_GAME; }
}
