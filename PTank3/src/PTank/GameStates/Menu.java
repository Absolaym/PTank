package PTank.GameStates;

import java.util.ArrayList;

import PTank.Window;
import PTank.Ressources.Button;
import PTank.Ressources.Ressources;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;


public class Menu extends BasicGameState
{
	// --------------------------------------------
	// ------------- Attributes -------------------
	// --------------------------------------------
	
	private Music background ;
	private Music mPlay;
	private MouseOverArea play;
	private MouseOverArea quit;
	private MouseOverArea skin;
	private MouseOverArea edit;

	// --------------------------------------------
	// -------------- Methods ---------------------
	// --------------------------------------------
	@Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
    {
		//Musique du menu et des bouttons une fois cliqué
		background = new Music("ressources/musique/drums.wav");
	    background.loop((float) 1, (float) 0.1);
	    mPlay = new Music("ressources/musique/LETSGO.wav");
	    
    	//button
	    
	    play= new MouseOverArea(gc, new Image("ressources/image/Playbutton.png"),(Window.WIDTH/2)-150, 200,300,50);
    	skin= new MouseOverArea(gc, new Image("ressources/image/Skinbutton.png"),(Window.WIDTH/4)-150, 400-50,300,50);
    	edit= new MouseOverArea(gc, new Image("ressources/image/Editbutton.png"),3*(Window.WIDTH/4)-150, 400-50,300,50);
    	quit= new MouseOverArea(gc, new Image("ressources/image/Quitbutton.png"),(Window.WIDTH/2)-150, 500,300-25,50);
	    
    }

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int dt) throws SlickException
	{	
		if(play.isMouseOver() && gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
			background.stop();
			mPlay.play();
			sbg.enterState(GameStates.IN_GAME, new FadeOutTransition(), new FadeInTransition());
		}
		
		if(quit.isMouseOver() && gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
			background.stop();
			System.exit(0);
		}
		
		if(skin.isMouseOver() && gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
			background.stop();
			sbg.enterState(GameStates.CUSTOMIZATION, new FadeOutTransition(), new FadeInTransition());
		}
		
		if(edit.isMouseOver() && gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
			background.stop();
			sbg.enterState(GameStates.MENU_LEVEL_EDITOR, new FadeOutTransition(), new FadeInTransition());

		}
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		g.setBackground(Color.gray);
		// render title
		play.render(gc, g);
		skin.render(gc, g);
		edit.render(gc, g);
		quit.render(gc, g);
	}

	@Override
	public int getID() { return GameStates.MENU; }
}
