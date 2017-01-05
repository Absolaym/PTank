package PTank.GameStates;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import PTank.Window;
import PTank.Ressources.Button;
import PTank.Ressources.Ressources;

public class MenuLevelEditor extends BasicGameState
{
	// --------------------------------------------
	// ------------- Attributes -------------------
	// --------------------------------------------
	private ArrayList<Button> buttons;

	// --------------------------------------------
	// -------------- Methods ---------------------
	// --------------------------------------------
    public void init(GameContainer container, StateBasedGame arg1) throws SlickException 
    {
    	buttons = new ArrayList<Button>();
		
    	buttons.add(Ressources.getButton("New Map"));
    	buttons.add(Ressources.getButton("Load Map"));
    	buttons.add(Ressources.getButton("Main Menu"));
    }

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int dt) throws SlickException
	{
		// update buttons
		for (int i = 0; i < buttons.size(); i++)
		{
			buttons.get(i).update(gc, sbg, dt);
		}

		// si New Map clické, go LEVEL_EDITOR
		if (Ressources.getButton("New Map").isMouseClicked())
		{
			sbg.enterState(GameStates.LEVEL_EDITOR, new FadeOutTransition(), new FadeInTransition());
		}
		// si Load Map clické, go MENU_LOAD_MAP
		if (Ressources.getButton("Load Map").isMouseClicked())
		{
			sbg.enterState(GameStates.MENU_LEVEL_EDITOR, new FadeOutTransition(), new FadeInTransition());
		}
		// si Main Manu clické, go MENU
		if (Ressources.getButton("Main Menu").isMouseClicked())
		{
			sbg.enterState(GameStates.MENU, new FadeOutTransition(), new FadeInTransition());
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
	public int getID() { return GameStates.MENU_LEVEL_EDITOR; }
}
