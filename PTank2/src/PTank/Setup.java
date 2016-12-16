package PTank;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Setup extends StateBasedGame
{
	public Setup(String title)
	{
		super(title);
	}
	
	public static void main (String args[]) throws SlickException 
	{
		AppGameContainer app = new AppGameContainer(new Setup("Setup test"));
		
		app.setDisplayMode(1200, 800, false);
		app.setAlwaysRender(true);
		
		app.start();
	}
	
	public void initStatesList(GameContainer container) throws SlickException
	{
		this.addState(new MenuState());
		this.addState(new InGameState(1));
	}
}
